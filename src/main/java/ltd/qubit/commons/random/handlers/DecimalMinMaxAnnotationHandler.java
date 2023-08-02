////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.BigDecimalRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.BigIntegerRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.ByteRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.LongRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.ShortRangeRandomizer;
import ltd.qubit.commons.random.randomizers.text.StringDelegatingRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * The annotation handler for the {@link DecimalMax} and {@link DecimalMin}
 * annotations.
 *
 * @author Haixing Hu
 */
public class DecimalMinMaxAnnotationHandler implements AnnotationHandler {

  private final Random random;

  public DecimalMinMaxAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    final Class<?> fieldType = field.getType();
    final DecimalMax decimalMaxAnnotation = getAnnotation(field, DecimalMax.class);
    final DecimalMin decimalMinAnnotation = getAnnotation(field, DecimalMin.class);
    BigDecimal maxValue = null;
    BigDecimal minValue = null;
    if (decimalMaxAnnotation != null) {
      maxValue = new BigDecimal(decimalMaxAnnotation.value());
    }
    if (decimalMinAnnotation != null) {
      minValue = new BigDecimal(decimalMinAnnotation.value());
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
      return new LongRangeRandomizer(
              minValue == null ? null : minValue.longValue(),
              maxValue == null ? null : maxValue.longValue(),
              random.nextLong()
      );
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
    } else if (fieldType.equals(String.class)) {
      final BigDecimalRangeRandomizer delegate = new BigDecimalRangeRandomizer(
              minValue == null ? null : minValue.doubleValue(),
              maxValue == null ? null : maxValue.doubleValue(),
              random.nextLong()
      );
      return new StringDelegatingRandomizer(delegate);
    } else {
      return null;
    }
  }
}
