////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Array;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isArrayType;
import static ltd.qubit.commons.random.util.RandomUtils.getRandomCollectionSize;

/**
 * 随机数组填充器。
 *
 * @author 胡海星
 */
public class ArrayPopulator {

  private final EasyRandom random;

  /**
   * 创建一个新的{@link ArrayPopulator}。
   *
   * @param random
   *     {@link EasyRandom}实例。
   */
  public ArrayPopulator(final EasyRandom random) {
    this.random = random;
  }

  /**
   * 生成并填充指定类型的随机数组。
   *
   * @param fieldType
   *     要填充的数组的类型。
   * @param context
   *     当前上下文。
   * @param sizeRange
   *     大小范围。
   * @return 填充后的数组。
   */
  public Object populate(final Class<?> fieldType, final Context context,
          @Nullable final CloseRange<Integer> sizeRange) {
    if (! isArrayType(fieldType)) {
      throw new UnsupportedOperationException("Only support array type");
    }
    final int size = getRandomCollectionSize(random, context, sizeRange);
    final Class<?> componentType = fieldType.getComponentType();
    final Object result = Array.newInstance(componentType, size);
    for (int i = 0; i < size; i++) {
      final Object element = random.nextObject(componentType, context);
      Array.set(result, i, element);
    }
    return result;
  }

  /**
   * 获取此填充器的随机化器。
   *
   * @param fieldType
   *     要填充的字段的类型。
   * @param sizeRange
   *     大小范围。
   * @return 此填充器的随机化器。
   */
  public Randomizer<?> getRandomizer(final Class<?> fieldType,
          @Nullable final CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(fieldType, context, sizeRange);
      }
    };
  }
}
