////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link BigDecimal}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class BigDecimalRandomizer implements Randomizer<BigDecimal> {

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
  public BigDecimalRandomizer(final Integer scale,
          final RoundingMode roundingMode) {
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
  public BigDecimal getRandomValue() {
    BigDecimal randomValue = new BigDecimal(delegate.getRandomValue());
    if (scale != null) {
      randomValue = randomValue.setScale(this.scale, this.roundingMode);
    }
    return randomValue;
  }
}
