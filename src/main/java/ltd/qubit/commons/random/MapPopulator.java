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
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isMapType;
import static ltd.qubit.commons.random.util.RandomUtils.createRandomMap;
import static ltd.qubit.commons.random.util.RandomUtils.getRandomCollectionSize;

/**
 * 随机映射表填充器。
 *
 * @author 胡海星
 */
public class MapPopulator {

  private final EasyRandom random;
  private final ObjectFactory objectFactory;

  /**
   * 构造一个 {@link MapPopulator}。
   *
   * @param random
   *     用于填充映射表的 {@link EasyRandom} 实例。
   */
  public MapPopulator(final EasyRandom random) {
    this.random = random;
    this.objectFactory = random.getObjectFactory();
  }

  /**
   * 构造一个 {@link MapPopulator}。
   *
   * @param random
   *     用于填充映射表的 {@link EasyRandom} 实例。
   * @param objectFactory
   *     用于创建对象的对象工厂。
   */
  public MapPopulator(final EasyRandom random, final ObjectFactory objectFactory) {
    this.random = random;
    this.objectFactory = objectFactory;
  }

  /**
   * 填充指定的映射表字段。
   *
   * @param field
   *     要填充的字段。
   * @param context
   *     当前上下文。
   * @param sizeRange
   *     映射表大小的范围。
   * @return 填充了随机键值对的映射表。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Map<?, ?> populate(final Field field, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    final Class<?> fieldType = field.getType();
    if (! isMapType(fieldType)) {
      throw new UnsupportedOperationException("Only support map type");
    }
    final Type fieldGenericType = field.getGenericType();
    final int size = getRandomCollectionSize(random, context, sizeRange);
    return createRandomMap(random, objectFactory, context, fieldType,
            fieldGenericType, size);
  }

  /**
   * 填充指定类型的映射表。
   *
   * @param fieldType
   *     要填充的映射表类型。
   * @param context
   *     当前上下文。
   * @param sizeRange
   *     映射表大小的范围。
   * @return 填充了随机键值对的映射表。
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Map<?, ?> populate(final Class<?> fieldType, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    if (! isMapType(fieldType)) {
      throw new UnsupportedOperationException("Only support map type");
    }
    final int size = getRandomCollectionSize(random, context, sizeRange);
    return createRandomMap(random, objectFactory, context, fieldType,
            fieldType, size);
  }

  /**
   * 获取一个随机化器，该随机化器可以为指定的字段生成随机映射表。
   *
   * @param field
   *     要为其创建随机化器的字段。
   * @param sizeRange
   *     映射表大小的范围。
   * @return 一个可以为指定字段生成随机映射表的随机化器。
   */
  public Randomizer<?> getRandomizer(final Field field,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(field, context, sizeRange);
      }
    };
  }

  /**
   * 获取一个随机化器，该随机化器可以为指定的类型生成随机映射表。
   *
   * @param fieldType
   *     要为其创建随机化器的字段类型。
   * @param sizeRange
   *     映射表大小的范围。
   * @return 一个可以为指定类型生成随机映射表的随机化器。
   */
  public Randomizer<?> getRandomizer(final Class<?> fieldType,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(fieldType, context, sizeRange);
      }
    };
  }
}
