////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.text.StringRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isArrayType;
import static ltd.qubit.commons.lang.ClassUtils.isCollectionType;
import static ltd.qubit.commons.lang.ClassUtils.isMapType;
import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * {@link Size} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class SizeAnnotationHandler implements AnnotationHandler {

  private final long seed;
  private final Parameters parameters;
  private final EasyRandom random;

  /**
   * 构造一个 {@link SizeAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   * @param random
   *     {@link EasyRandom} 的实例。
   * @param parameters
   *     {@link Parameters} 的实例。
   */
  public SizeAnnotationHandler(final long seed, final EasyRandom random,
          final Parameters parameters) {
    this.seed = seed;
    this.random = random;
    this.parameters = parameters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    final CloseRange<Integer> sizeRange = getSizeRange(field);
    final Class<?> fieldType = field.getType();
    if (fieldType.equals(String.class)) {
      return new StringRandomizer(sizeRange, random.nextLong());
    } else if (isArrayType(fieldType)) {
      return random.getArrayPopulator().getRandomizer(fieldType, sizeRange);
    } else if (isCollectionType(fieldType)) {
      return random.getCollectionPopulator().getRandomizer(field, sizeRange);
    } else if (isMapType(fieldType)) {
      return random.getMapPopulator().getRandomizer(field, sizeRange);
    }
    return null;
  }

  /**
   * 从字段的 {@link Size} 注解中获取大小范围。
   *
   * @param field
   *     要获取大小范围的字段。
   * @return 字段的 {@link Size} 注解指定的大小范围。
   */
  private CloseRange<Integer> getSizeRange(final Field field) {
    final Size sizeAnnotation = getAnnotation(field, Size.class);
    final Integer defaultMax = parameters.getCollectionSizeRange().getMax();
    final int min = sizeAnnotation.min();
    final int max = (sizeAnnotation.max() == Integer.MAX_VALUE
                     ? defaultMax
                     : sizeAnnotation.max());
    return new CloseRange<>(min, max);
  }
}
