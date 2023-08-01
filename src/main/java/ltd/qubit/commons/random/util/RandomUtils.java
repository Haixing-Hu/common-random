////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.FieldPopulator;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.util.pair.Pair;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.reflect.ClassUtils.isIntrospectable;
import static ltd.qubit.commons.reflect.ClassUtils.isPopulatable;

public class RandomUtils {

  /**
   * Gets the random size of a collection.
   *
   * @param random
   *     a random number generator.
   * @param context
   *     the randomization context.
   * @param sizeRange
   *     the optional specified range of the collection size. If it is {@code
   *     null}, use the size of range from the parameters of the {@code
   *     context}.
   * @return the randomized size of a collection.
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
   * Creates a random collection.
   *
   * @param random
   *     a random bean generator.
   * @param context
   *     the randomization context.
   * @param type
   *     the class of the collection.
   * @param genericType
   *     the generic type of the collection, or it could be the same as the
   *     {@code type} argument.
   * @param size
   *     the size of the generated collection.
   * @return a random collection of the specified size; or an empty collection
   *     if its element type cannot be resolved.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Collection createRandomCollection(final EasyRandom random,
      final Context context, final Class<?> type, final Type genericType,
      final int size) {
    final Collection result = ReflectionUtils.createEmptyCollection(type, size);
    final Class<?> elementType = ReflectionUtils.getCollectionElementType(genericType);
    if (elementType != null && isPopulatable(elementType)) {
      for (int i = 0; i < size; ++i) {
        final Object item = random.nextObject(elementType, context);
        result.add(item);
      }
    }
    return result;
  }

  /**
   * Creates a random map.
   *
   * @param random
   *     a random bean generator.
   * @param objectFactory
   *     the object factory.
   * @param context
   *     the randomization context.
   * @param type
   *     the class of the map.
   * @param genericType
   *     the generic type of the map, or it could be the same as the {@code
   *     type} argument.
   * @param size
   *     the size of the generated map.
   * @return a random map of the specified size; or an empty map if its element
   *     type cannot be resolved.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map createRandomMap(final EasyRandom random,
      final ObjectFactory objectFactory, final Context context,
      final Class<?> type, final Type genericType, final int size) {
    final Map<Object, Object> result = ReflectionUtils.createEmptyMap(objectFactory, context,
        type, genericType);
    final Pair<Class<?>, Class<?>> elementTypes = ReflectionUtils.getMapElementType(
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
   * Populates the remained fields of an object.
   *
   * @param random
   *     a random bean generator.
   * @param context
   *     the randomization context.
   * @param type
   *     the class of the object.
   * @param obj
   *     the object.
   * @param <T>
   *     the type of the object.
   * @return the object whose remained fields are populated.
   */
  public static <T> T populateRemainedFields(final EasyRandom random, final Context context,
      final Class<?> type, final T obj) {
    if (!isIntrospectable(type)) {
      return obj;
    }
    final FieldPopulator populator = random.getFieldPopulator();
    // retrieve fields to be populated
    final List<Field> fields = ReflectionUtils.getPopulatableFields(type, obj);
    try {
      for (final Field field : fields) {
        if (context.shouldFieldBePopulated(obj, field)
            && ReflectionUtils.fieldHasDefaultValue(obj, field)) {
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
