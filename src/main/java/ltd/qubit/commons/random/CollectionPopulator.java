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
import java.lang.reflect.Type;
import java.util.Collection;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isCollectionType;
import static ltd.qubit.commons.random.util.RandomUtils.createRandomCollection;
import static ltd.qubit.commons.random.util.RandomUtils.getRandomCollectionSize;

/**
 * 随机集合填充器。
 *
 * @author 胡海星
 */
public class CollectionPopulator {

  private final EasyRandom random;

  /**
   * 构造一个 {@link CollectionPopulator}。
   *
   * @param random
   *     EasyRandom 实例。
   */
  public CollectionPopulator(final EasyRandom random) {
    this.random = random;
  }

  /**
   * 填充指定的集合字段。
   *
   * @param field
   *     要填充的字段。
   * @param context
   *     上下文。
   * @param sizeRange
   *     集合大小的范围。
   * @return 填充后的集合。
   */
  public Collection<?> populate(final Field field, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    final Class<?> fieldType = field.getType();
    if (! isCollectionType(fieldType)) {
      throw new UnsupportedOperationException("Only support collection type");
    }
    final Type genericType = field.getGenericType();
    final int size = getRandomCollectionSize(random, context, sizeRange);
    return createRandomCollection(random, context, fieldType, genericType, size);
  }

  /**
   * 填充指定的集合类型。
   *
   * @param fieldType
   *     要填充的集合字段的类型。
   * @param context
   *     上下文。
   * @param sizeRange
   *     集合大小的范围。
   * @return 填充后的集合。
   */
  public Collection<?> populate(final Class<?> fieldType, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    if (! isCollectionType(fieldType)) {
      throw new UnsupportedOperationException("Only support collection type");
    }
    final int size = getRandomCollectionSize(random, context, sizeRange);
    return createRandomCollection(random, context, fieldType, fieldType, size);
  }

  /**
   * 为指定的集合字段创建一个随机化器。
   *
   * @param field
   *     字段。
   * @param sizeRange
   *     集合大小的范围。
   * @return 随机化器。
   */
  public Randomizer<?> getRandomizer(final Field field,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<>() {
      @Override
      public Object getRandomValue() {
        return populate(field, context, sizeRange);
      }
    };
  }

  /**
   * 为指定的集合类型创建一个随机化器。
   *
   * @param collectionType
   *     集合类型。
   * @param sizeRange
   *     集合大小的范围。
   * @return 随机化器。
   */
  public Randomizer<?> getRandomizer(final Class<?> collectionType,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<>() {
      @Override
      public Object getRandomValue() {
        return populate(collectionType, context, sizeRange);
      }
    };
  }
}
