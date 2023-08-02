////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.randomizers.misc.SkipRandomizer;
import ltd.qubit.commons.random.util.ReflectionUtils;
import ltd.qubit.commons.reflect.FieldUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.reflect.ClassUtils.isAbstract;
import static ltd.qubit.commons.reflect.ClassUtils.isArrayType;
import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;
import static ltd.qubit.commons.reflect.ClassUtils.isEnumType;
import static ltd.qubit.commons.reflect.ClassUtils.isMapType;

/**
 * Component that encapsulate the logic of generating a random value for a given
 * field. It collaborates with a:
 * <ul>
 * <li>{@link EasyRandom} whenever the field is a user defined type.</li>
 * <li>{@link ArrayPopulator} whenever the field is an array type.</li>
 * <li>{@link CollectionPopulator} whenever the field is a collection
 * type.</li>
 * <li>{@link CollectionPopulator}whenever the field is a map type.</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldPopulator {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final EasyRandom random;
  private final ArrayPopulator arrayPopulator;
  private final CollectionPopulator collectionPopulator;
  private final MapPopulator mapPopulator;
  private final RandomizerProvider randomizerProvider;

  public FieldPopulator(final EasyRandom random) {
    this.random = random;
    this.arrayPopulator = random.getArrayPopulator();
    this.collectionPopulator = random.getCollectionPopulator();
    this.mapPopulator = random.getMapPopulator();
    this.randomizerProvider = random.getRandomizerProvider();
  }

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

  public void populate(final Object object, final String fieldName)
      throws IllegalAccessException, NoSuchFieldException {
    final Class<?> type = object.getClass();
    final Field field = type.getDeclaredField(fieldName);
    populate(object, field);
  }

  public void populate(final Object object, final Field field)
      throws IllegalAccessException {
    final Context context = new Context(object.getClass(), random.getParameters());
    context.setRandomizedObject(object);
    context.addPopulatedBean(object.getClass(), object);
    populate(object, field, context);
  }

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
      ReflectionUtils.setFieldValue(object, field, value);
    } else {
      try {
        ReflectionUtils.setProperty(object, field, value);
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
        List<Class<?>> subTypes = ReflectionUtils.getPublicConcreteSubTypesOf(fieldType);
        subTypes = ReflectionUtils.filterSameParameterizedTypes(subTypes, fieldGenericType);
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
