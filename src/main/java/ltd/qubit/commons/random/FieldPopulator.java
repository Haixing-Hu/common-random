////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.randomizers.misc.SkipRandomizer;
import ltd.qubit.commons.reflect.FieldUtils;

import static ltd.qubit.commons.lang.ClassUtils.isAbstract;
import static ltd.qubit.commons.lang.ClassUtils.isArrayType;
import static ltd.qubit.commons.lang.ClassUtils.isCollectionType;
import static ltd.qubit.commons.lang.ClassUtils.isEnumType;
import static ltd.qubit.commons.lang.ClassUtils.isMapType;
import static ltd.qubit.commons.random.util.ReflectionUtils.filterSameParameterizedTypes;
import static ltd.qubit.commons.random.util.ReflectionUtils.getPublicConcreteSubTypesOf;
import static ltd.qubit.commons.random.util.ReflectionUtils.setFieldValue;
import static ltd.qubit.commons.random.util.ReflectionUtils.setProperty;

/**
 * 封装了为给定字段生成随机值逻辑的组件。它与以下内容协作：
 * <ul>
 * <li>{@link EasyRandom}：当字段是用户定义的类型时。</li>
 * <li>{@link ArrayPopulator}：当字段是数组类型时。</li>
 * <li>{@link CollectionPopulator}：当字段是集合类型时。</li>
 * <li>{@link MapPopulator}：当字段是映射类型时。</li>
 * </ul>
 *
 * @author 胡海星
 */
public class FieldPopulator {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final EasyRandom random;
  private final ArrayPopulator arrayPopulator;
  private final CollectionPopulator collectionPopulator;
  private final MapPopulator mapPopulator;
  private final RandomizerProvider randomizerProvider;

  /**
   * 构造一个 {@link FieldPopulator}。
   *
   * @param random
   *     EasyRandom 实例。
   */
  public FieldPopulator(final EasyRandom random) {
    this.random = random;
    this.arrayPopulator = random.getArrayPopulator();
    this.collectionPopulator = random.getCollectionPopulator();
    this.mapPopulator = random.getMapPopulator();
    this.randomizerProvider = random.getRandomizerProvider();
  }

  /**
   * 构造一个 {@link FieldPopulator}。
   *
   * @param random
   *     EasyRandom 实例。
   * @param randomizerProvider
   *     随机化器提供者。
   * @param arrayPopulator
   *     数组填充器。
   * @param collectionPopulator
   *     集合填充器。
   * @param mapPopulator
   *     映射填充器。
   */
  public FieldPopulator(final EasyRandom random,
          final RandomizerProvider randomizerProvider,
          final ArrayPopulator arrayPopulator,
          final CollectionPopulator collectionPopulator,
          final MapPopulator mapPopulator) {
    this.random = random;
    this.randomizerProvider = randomizerProvider;
    this.arrayPopulator = arrayPopulator;
    this.collectionPopulator = collectionPopulator;
    this.mapPopulator = mapPopulator;
  }

  /**
   * 填充指定对象的指定名称的字段。
   *
   * @param object
   *     要填充其字段的对象。
   * @param fieldName
   *     要填充的字段的名称。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   * @throws NoSuchFieldException
   *     如果字段不存在。
   */
  public void populate(final Object object, final String fieldName)
      throws IllegalAccessException, NoSuchFieldException {
    final Class<?> type = object.getClass();
    final Field field = type.getDeclaredField(fieldName);
    populate(object, field);
  }

  /**
   * 填充指定对象的指定字段。
   *
   * @param object
   *     要填充其字段的对象。
   * @param field
   *     要填充的字段。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   */
  public void populate(final Object object, final Field field)
      throws IllegalAccessException {
    final Context context = new Context(object.getClass(), random.getParameters());
    context.setRandomizedObject(object);
    context.addPopulatedBean(object.getClass(), object);
    populate(object, field, context);
  }

  /**
   * 填充指定对象的指定字段。
   *
   * @param object
   *     要填充其字段的对象。
   * @param field
   *     要填充的字段。
   * @param context
   *     随机化上下文。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   */
  public void populate(final Object object, final Field field, final Context context)
          throws IllegalAccessException {
    context.pushStackItem(new ContextStackItem(object, field));
    final Randomizer<?> randomizer = getRandomizer(field, context);
    if (randomizer instanceof SkipRandomizer) {
      logger.warn("Ignore the skip randomizer for the field {} of object {}", field.getName(),
              object.getClass().getName());
      context.popStackItem();
      return;
    }
    if (context.hasExceededRandomizationDepth()) {
      logger.warn("The field {} of object {} exceed the maximum randomization depth.",
              field.getName(), object.getClass().getName());
      context.popStackItem();
      return;
    }
    final Object value;
    if (randomizer != null) {
      if (randomizer instanceof ContextAwareRandomizer) {
        ((ContextAwareRandomizer<?>) randomizer).setContext(context);
      }
      value = randomizer.getRandomValue();
    } else {
      try {
        value = generateRandomValue(object.getClass(), field, context);
      } catch (final ObjectCreationException e) {
        final String exceptionMessage = String.format(
                "Unable to create type: %s for field: %s of class: %s",
                field.getType().getName(),
                field.getName(),
                object.getClass().getName());
        logger.error(exceptionMessage);
        // FIXME catch ObjectCreationException and throw ObjectCreationException ?
        throw new ObjectCreationException(exceptionMessage, e);
      }
    }
    if (context.getParameters().isBypassSetters()) {
      setFieldValue(object, field, value);
    } else {
      try {
        setProperty(object, field, value);
      } catch (final InvocationTargetException e) {
        final String exceptionMessage = String.format(
                "Unable to invoke setter for field %s of class %s",
                field.getName(), object.getClass().getName());
        throw new ObjectCreationException(exceptionMessage, e.getCause());
      }
    }
    context.popStackItem();
  }

  private Randomizer<?> getRandomizer(final Field field, final Context context) {
    // issue 241: if there is no custom randomizer by field, then check by type
    Randomizer<?> randomizer = randomizerProvider.getByField(field, context);
    if (randomizer == null) {
      randomizer = randomizerProvider.getByType(field.getType(), context);
    }
    return randomizer;
  }

  private Object generateRandomValue(final Class<?> ownerClass, final Field field,
      final Context context) {
    final Class<?> fieldType = field.getType();
    final Type fieldGenericType = field.getGenericType();
    final Object value;
    if (isArrayType(fieldType)) {
      value = arrayPopulator.populate(fieldType, context, null);
    } else if (isCollectionType(fieldType)) {
      value = collectionPopulator.populate(field, context, null);
    } else if (isMapType(fieldType)) {
      value = mapPopulator.populate(field, context, null);
    } else {
      if (context.getParameters().isScanClasspathForConcreteTypes()
              && isAbstract(fieldType)
              && !isEnumType(fieldType)) { /*enums can be abstract, but can not inherit*/
        List<Class<?>> subTypes = getPublicConcreteSubTypesOf(fieldType);
        subTypes = filterSameParameterizedTypes(subTypes, fieldGenericType);
        final Class<?> randomConcreteSubType = random.choose(subTypes);
        if (randomConcreteSubType == null) {
          throw new ObjectCreationException(
              "Unable to find a matching concrete " + "subtype of type: " + fieldType);
        } else {
          value = random.nextObject(randomConcreteSubType, context);
        }
      } else {
        final Class<?> fieldActualType = FieldUtils.getActualType(ownerClass, field);
        value = random.nextObject(fieldActualType, context);
      }
    }
    return value;
  }

  /**
   * 填充对象的字段。
   *
   * @param object
   *     要填充其字段的对象。
   * @param fields
   *     要填充的字段列表。
   * @param context
   *     随机化上下文。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   */
  public void populateFields(final Object object, final List<Field> fields,
          final Context context) throws IllegalAccessException {
    logger.debug("Populate fields: class = {}, fields = {}, context = {}",
        object.getClass(), fields, context);
    fields.sort(new UniqueAnnotatedFieldComparator()); // sort the filed by its @Unique annotation
    for (final Field field : fields) {
      if (context.shouldFieldBePopulated(object, field)) {
        this.populate(object, field, context);
      }
    }
  }
}
