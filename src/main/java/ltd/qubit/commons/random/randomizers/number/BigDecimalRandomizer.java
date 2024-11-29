////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * Generate a random {@link BigDecimal}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class BigDecimalRandomizer implements Randomizer<BigDecimal>,
    ContextAwareRandomizer<BigDecimal> {

  private final DoubleRandomizer delegate;
  private Integer scale;
  private RoundingMode roundingMode = RoundingMode.HALF_UP;

  /**
   * Create a new {@link BigDecimalRandomizer}.
   */
  public BigDecimalRandomizer() {
    delegate = new DoubleRandomizer();
  }

  /**
   * Create a new {@link BigDecimalRandomizer}.
   *
   * @param seed
   *         initial seed of the random number generator.
   */
  public BigDecimalRandomizer(final long seed) {
    delegate = new DoubleRandomizer(seed);
  }

  /**
   * Create a new {@link BigDecimalRandomizer}. The default rounding mode is
   * {@link RoundingMode#HALF_UP}.
   *
   * @param scale
   *         scale of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRandomizer(final Integer scale) {
    delegate = new DoubleRandomizer();
    this.scale = scale;
  }

  public BigDecimalRandomizer(final long seed, final Integer scale) {
    delegate = new DoubleRandomizer(seed);
    this.scale = scale;
  }

  /**
   * Create a new {@link BigDecimalRandomizer}.
   *
   * @param scale
   *         scale of the {@code BigDecimal} value to be returned.
   * @param roundingMode
   *         rounding mode of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRandomizer(final Integer scale, final RoundingMode roundingMode) {
    this(scale);
    this.roundingMode = roundingMode;
  }

  /**
   * Create a new {@link BigDecimalRandomizer}.
   *
   * @param seed
   *         initial seed of the random number generator.
   * @param scale
   *         scale of the {@code BigDecimal} value to be returned.
   * @param roundingMode
   *         rounding mode of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRandomizer(final long seed, final Integer scale,
          final RoundingMode roundingMode) {
    this(seed);
    this.scale = scale;
    this.roundingMode = roundingMode;
  }

  @Override
  public void setContext(final Context context) {
    final Field field = context.getCurrentField();
    if (field != null) {
      final Scale scaleAnn = getAnnotation(field, Scale.class);
      if (scaleAnn != null) {
        this.scale = scaleAnn.value();
      }
      final Round roundAnn = getAnnotation(field, Round.class);
      if (roundAnn != null) {
        this.roundingMode = roundAnn.value();
      }
      final Money moneyAnn = getAnnotation(field, Money.class);
      if (moneyAnn != null) {
        this.scale = moneyAnn.scale();
        this.roundingMode = moneyAnn.roundingModel();
      }
    }
  }

  @Override
  public BigDecimal getRandomValue() {
    BigDecimal randomValue = BigDecimal.valueOf(delegate.getRandomValue());
    if (scale != null) {
      randomValue = randomValue.setScale(this.scale, this.roundingMode);
    }
    return randomValue;
  }
}
