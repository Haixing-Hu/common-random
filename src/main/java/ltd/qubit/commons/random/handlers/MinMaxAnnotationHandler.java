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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.BigDecimalRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.BigIntegerRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.ByteRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.LongRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.ShortRangeRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * {@link Max} 和 {@link Min} 注解的注解处理器。
 *
 * @author 胡海星
 */

public class MinMaxAnnotationHandler implements AnnotationHandler {

  private final Random random;

  /**
   * 构造一个 {@link MinMaxAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public MinMaxAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    final Class<?> fieldType = field.getType();
    final Max maxAnnotation = getAnnotation(field, Max.class);
    final Min minAnnotation = getAnnotation(field, Min.class);
    Long maxValue = null;
    Long minValue = null;
    if (maxAnnotation != null) {
      maxValue = maxAnnotation.value();
    }
    if (minAnnotation != null) {
      minValue = minAnnotation.value();
    }
    if (fieldType.equals(Byte.TYPE) || fieldType.equals(Byte.class)) {
      return new ByteRangeRandomizer(
              minValue == null ? null : minValue.byteValue(),
              maxValue == null ? null : maxValue.byteValue(),
              random.nextLong()
      );
    } else if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
      return new ShortRangeRandomizer(
              minValue == null ? null : minValue.shortValue(),
              maxValue == null ? null : maxValue.shortValue(),
              random.nextLong()
      );
    } else if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
      return new IntegerRangeRandomizer(
              minValue == null ? null : minValue.intValue(),
              maxValue == null ? null : maxValue.intValue(),
              random.nextLong()
      );
    } else if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
      return new LongRangeRandomizer(minValue, maxValue, random.nextLong());
    } else if (fieldType.equals(BigInteger.class)) {
      return new BigIntegerRangeRandomizer(
              minValue == null ? null : minValue.intValue(),
              maxValue == null ? null : maxValue.intValue(),
              random.nextLong()
      );
    } else if (fieldType.equals(BigDecimal.class)) {
      return new BigDecimalRangeRandomizer(
              minValue == null ? null : minValue.doubleValue(),
              maxValue == null ? null : maxValue.doubleValue(),
              random.nextLong()
      );
    } else {
      return null;
    }
  }
}
