////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Generate a random {@link BigDecimal}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class BigDecimalRandomizer extends ScalableRandomizer<BigDecimal> {

  private final DoubleRandomizer delegate;

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
  public BigDecimal getRandomValue() {
    BigDecimal randomValue = BigDecimal.valueOf(delegate.getRandomValue());
    if (scale != null) {
      randomValue = randomValue.setScale(this.scale, this.roundingMode);
    }
    return randomValue;
  }
}
