////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link Double}。
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DoubleRangeRandomizer extends AbstractRangeRandomizer<Double> {

  /**
   * 创建一个新的{@link DoubleRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public DoubleRangeRandomizer(final Double min, final Double max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link DoubleRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public DoubleRangeRandomizer(final Double min, final Double max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Double min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Double max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Double getDefaultMinValue() {
    return Double.MIN_VALUE;
  }

  @Override
  protected Double getDefaultMaxValue() {
    return Double.MAX_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Double getRandomValue() {
    return random.nextDouble(range);
  }
}
