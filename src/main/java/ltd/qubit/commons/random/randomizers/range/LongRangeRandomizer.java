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
 * 在给定范围内生成一个随机的{@link Long}。
 *
 * @author 胡海星
 */
public class LongRangeRandomizer extends AbstractRangeRandomizer<Long> {

  /**
   * 创建一个新的{@link LongRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public LongRangeRandomizer(final Long min, final Long max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link LongRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public LongRangeRandomizer(final Long min, final Long max, final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Long min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Long max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  public Long getRandomValue() {
    return random.nextLong(range);
  }

  @Override
  protected Long getDefaultMaxValue() {
    return Long.MAX_VALUE;
  }

  @Override
  protected Long getDefaultMinValue() {
    return Long.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
