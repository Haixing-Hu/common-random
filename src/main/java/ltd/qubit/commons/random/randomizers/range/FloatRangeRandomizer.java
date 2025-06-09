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
 * 在给定范围内生成一个随机的{@link Float}。
 *
 * @author 胡海星
 */
public class FloatRangeRandomizer extends AbstractRangeRandomizer<Float> {

  /**
   * 创建一个新的{@link FloatRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public FloatRangeRandomizer(final Float min, final Float max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link FloatRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public FloatRangeRandomizer(final Float min, final Float max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Float min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Float max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Float getDefaultMinValue() {
    return Float.MIN_VALUE;
  }

  @Override
  protected Float getDefaultMaxValue() {
    return Float.MAX_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Float getRandomValue() {
    return random.nextFloat(range);
  }
}
