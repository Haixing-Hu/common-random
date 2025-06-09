////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.random.randomizers.number.ScalableRandomizer;

/**
 * 在给定范围内生成一个随机的{@link BigDecimal}。
 *
 * @author Rémi Alvergnat, 胡海星
 */
public class BigDecimalRangeRandomizer extends ScalableRandomizer<BigDecimal> {

  private final DoubleRangeRandomizer delegate;

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max) {
    delegate = new DoubleRangeRandomizer(min, max);
  }

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max, final long seed) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
  }

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。默认舍入模式
   * 是{@link RoundingMode#HALF_UP}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max, final Integer scale) {
    delegate = new DoubleRangeRandomizer(min, max);
    this.scale = scale;
  }

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   * @param roundingMode
   *         要返回的{@code BigDecimal}值的舍入模式。
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final Integer scale, final RoundingMode roundingMode) {
    delegate = new DoubleRangeRandomizer(min, max, scale);
    this.roundingMode = roundingMode;
  }

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。默认舍入模式
   * 是{@link RoundingMode#HALF_UP}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final long seed, final Integer scale) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
    this.scale = scale;
  }

  /**
   * 创建一个新的{@link BigDecimalRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   * @param scale
   *         要返回的{@code BigDecimal}值的小数位数。
   * @param roundingMode
   *         要返回的{@code BigDecimal}值的舍入模式。
   */
  public BigDecimalRangeRandomizer(final Double min, final Double max,
          final long seed, final Integer scale,
          final RoundingMode roundingMode) {
    delegate = new DoubleRangeRandomizer(min, max, seed);
    this.scale = scale;
    this.roundingMode = roundingMode;
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
