////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;

/**
 * Generate a random {@link BigDecimal} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class BigDecimalRangeRandomizer extends AbstractContextAwareRandomizer<BigDecimal> {

  private final DoubleRangeRandomizer delegate;
  private Integer scale;
  private RoundingMode roundingMode = RoundingMode.HALF_UP;

  /**
   * Create a new {@link BigDecimalRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max) {
    delegate = new DoubleRangeRandomizer(min, max);
  }

  /**
   * Create a new {@link BigDecimalRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max, final long seed) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
  }

  /**
   * Create a new {@link BigDecimalRangeRandomizer}. The default rounding mode
   * is {@link RoundingMode#HALF_UP}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param scale
   *         of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max, final Integer scale) {
    delegate = new DoubleRangeRandomizer(min, max);
    this.scale = scale;
  }

  /**
   * Create a new {@link BigDecimalRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param scale
   *         of the {@code BigDecimal} value to be returned.
   * @param roundingMode
   *         of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final Integer scale, final RoundingMode roundingMode) {
    delegate = new DoubleRangeRandomizer(min, max, scale);
    this.roundingMode = roundingMode;
  }

  /**
   * Create a new {@link BigDecimalRangeRandomizer}. The default rounding mode
   * is {@link RoundingMode#HALF_UP}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   * @param scale
   *         of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final long seed, final Integer scale) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
    this.scale = scale;
  }

  /**
   * Create a new {@link BigDecimalRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   * @param scale
   *         of the {@code BigDecimal} value to be returned.
   * @param roundingMode
   *         of the {@code BigDecimal} value to be returned.
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final long seed, final Integer scale,
          final RoundingMode roundingMode) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
    this.scale = scale;
    this.roundingMode = roundingMode;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    delegate.setParameters(parameters);
  }

  @Override
  public BigDecimal getRandomValue() {
    final Double delegateRandomValue = delegate.getRandomValue();
    BigDecimal randomValue = new BigDecimal(delegateRandomValue);
    if (scale != null) {
      randomValue = randomValue.setScale(this.scale, this.roundingMode);
    }
    return randomValue;
  }
}
