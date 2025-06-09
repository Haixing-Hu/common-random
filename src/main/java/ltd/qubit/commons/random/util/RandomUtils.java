////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.FieldPopulator;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.util.pair.Pair;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isIntrospectable;
import static ltd.qubit.commons.lang.ClassUtils.isPopulatable;
import static ltd.qubit.commons.random.util.ReflectionUtils.createEmptyCollection;
import static ltd.qubit.commons.random.util.ReflectionUtils.createEmptyMap;
import static ltd.qubit.commons.random.util.ReflectionUtils.fieldHasDefaultValue;
import static ltd.qubit.commons.random.util.ReflectionUtils.getCollectionElementType;
import static ltd.qubit.commons.random.util.ReflectionUtils.getMapElementType;
import static ltd.qubit.commons.random.util.ReflectionUtils.getPopulatableFields;

/**
 * 提供随机化相关的工具方法。
 *
 * @author 胡海星
 */
public class RandomUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtils.class);

  /**
   * 获取集合的随机大小。
   *
   * @param random
   *     随机数生成器。
   * @param context
   *     随机化上下文。
   * @param sizeRange
   *     集合大小的可选指定范围。如果为{@code null}，则使用
   *     {@code context}参数中的大小范围。
   * @return 集合的随机大小。
   */
  public static int getRandomCollectionSize(final RandomEx random,
      final Context context, @Nullable final CloseRange<Integer> sizeRange) {
    final Parameters parameters = context.getParameters();
    final CloseRange<Integer> range = (sizeRange != null
                                       ? sizeRange
                                       : parameters.getCollectionSizeRange());
    return random.nextInt(range);
  }

  /**
   * 创建一个随机集合。
   *
   * @param random
   *     随机bean生成器。
   * @param context
   *     随机化上下文。
   * @param collectionType
   *     集合的类。
   * @param collectionGenericType
   *     集合的泛型类型，或者可以与{@code type}参数相同。
   * @param size
   *     生成的集合的大小。
   * @param <T>
   *     集合的元素类型。
   * @return 指定大小的随机集合；如果其元素类型无法解析，则为空集合。
   */
  @SuppressWarnings({"unchecked"})
  public static <T> Collection<T> createRandomCollection(final EasyRandom random,
      final Context context, final Class<T> collectionType, final Type collectionGenericType,
      final int size) {
    final Collection<T> result = createEmptyCollection(collectionType, size);
    final Class<T> elementType = (Class<T>) getCollectionElementType(collectionGenericType);
    if (elementType != null && isPopulatable(elementType)) {
      for (int i = 0; i < size; ++i) {
        final T item = random.nextObject(elementType, context);
        LOGGER.trace("Add a random item to the collection: {}", item);
        result.add(item);
      }
    }
    return result;
  }

  /**
   * 创建一个随机映射。
   *
   * @param random
   *     随机bean生成器。
   * @param objectFactory
   *     对象工厂。
   * @param context
   *     随机化上下文。
   * @param type
   *     映射的类。
   * @param genericType
   *     映射的泛型类型，或者可以与{@code type}参数相同。
   * @param size
   *     生成的映射的大小。
   * @return 指定大小的随机映射；如果其元素类型无法解析，则为空映射。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map createRandomMap(final EasyRandom random,
      final ObjectFactory objectFactory, final Context context,
      final Class<?> type, final Type genericType, final int size) {
    final Map<Object, Object> result = createEmptyMap(objectFactory, context,
        type, genericType);
    final Pair<Class<?>, Class<?>> elementTypes = getMapElementType(
        genericType);
    if (elementTypes != null) {
      final Class<?> keyType = elementTypes.first;
      final Class<?> valueType = elementTypes.second;
      if (isPopulatable(keyType) && isPopulatable(valueType)) {
        //        final EasyRandomParameters parameters = context.getParameters();
        //        final int maxLoops = parameters.getMaxLoops();
        for (int i = 0; i < size; ++i) {
          final Object key = random.nextObject((Class<?>) keyType, context);
          //          for (int j = 0; j < maxLoops; ++j) {
          //            if (! result.containsKey(key)) break;
          //          }
          //          if (result.containsKey(key)) {
          //            throw new RuntimeException("Failed to generate a unique random key "
          //                    + "for the map after " + maxLoops + " retries.");
          //          }
          final Object value = random.nextObject((Class<?>) valueType, context);
          if (key != null) {
            result.put(key, value);
          }
        }
      }
    }
    return result;
  }

  /**
   * 填充对象的剩余字段。
   *
   * @param random
   *     随机bean生成器。
   * @param context
   *     随机化上下文。
   * @param type
   *     对象的类。
   * @param obj
   *     对象。
   * @param <T>
   *     对象的类型。
   * @return 填充了剩余字段的对象。
   */
  public static <T> T populateRemainedFields(final EasyRandom random,
      final Context context, final Class<?> type, final T obj) {
    if (!isIntrospectable(type)) {
      return obj;
    }
    final FieldPopulator populator = random.getFieldPopulator();
    // retrieve fields to be populated
    final List<Field> fields = getPopulatableFields(type, obj);
    try {
      for (final Field field : fields) {
        if (context.shouldFieldBePopulated(obj, field)
            && fieldHasDefaultValue(obj, field)) {
          populator.populate(obj, field, context);
        }
      }
    } catch (final IllegalAccessException e) {
      if (context.getParameters().isIgnoreErrors()) {
        return null;
      } else {
        throw new ObjectCreationException("Unable to create a random instance "
            + "of type " + type, e);
      }
    }
    return obj;
  }
}
