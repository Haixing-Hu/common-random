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
 * 生成一个随机的{@link BigDecimal}。
 *
 * @author 胡海星
 */
public class BigDecimalRandomizer extends ScalableRandomizer<BigDecimal> {

  private final DoubleRandomizer delegate;

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。
   */
  public BigDecimalRandomizer() {
    delegate = new DoubleRandomizer();
  }

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。
   *
   * @param seed
   *         随机数生成器的初始种子。
   */
  public BigDecimalRandomizer(final long seed) {
    delegate = new DoubleRandomizer(seed);
  }

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。默认的舍入模式是
   * {@link RoundingMode#HALF_UP}。
   *
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   */
  public BigDecimalRandomizer(final Integer scale) {
    delegate = new DoubleRandomizer();
    this.scale = scale;
  }

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。
   *
   * @param seed
   *         随机数生成器的初始种子。
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   */
  public BigDecimalRandomizer(final long seed, final Integer scale) {
    delegate = new DoubleRandomizer(seed);
    this.scale = scale;
  }

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。
   *
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   * @param roundingMode
   *         要返回的{@code BigDecimal}值的舍入模式。
   */
  public BigDecimalRandomizer(final Integer scale, final RoundingMode roundingMode) {
    this(scale);
    this.roundingMode = roundingMode;
  }

  /**
   * 创建一个新的{@link BigDecimalRandomizer}。
   *
   * @param seed
   *         随机数生成器的初始种子。
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   * @param roundingMode
   *         要返回的{@code BigDecimal}值的舍入模式。
   */
  public BigDecimalRandomizer(final long seed, final Integer scale,
          final RoundingMode roundingMode) {
    this(seed);
    this.scale = scale;
    this.roundingMode = roundingMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal getRandomValue() {
    BigDecimal randomValue = BigDecimal.valueOf(delegate.getRandomValue());
    if (scale != null) {
      randomValue = randomValue.setScale(this.scale, this.roundingMode);
    }
    return randomValue;
  }
}
