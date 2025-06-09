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
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link Integer}。
 *
 * @author 胡海星
 */
public class IntegerRangeRandomizer extends AbstractRangeRandomizer<Integer> {

  /**
   * 创建一个新的{@link IntegerRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public IntegerRangeRandomizer(final Integer min, final Integer max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link IntegerRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public IntegerRangeRandomizer(final Integer min, final Integer max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link IntegerRangeRandomizer}。
   *
   * @param range
   *         范围。
   */
  public IntegerRangeRandomizer(final CloseRange<Integer> range) {
    super(range);
  }

  /**
   * 创建一个新的{@link IntegerRangeRandomizer}。
   *
   * @param range
   *         范围。
   * @param seed
   *         初始种子
   */
  public IntegerRangeRandomizer(final CloseRange<Integer> range, final long seed) {
    super(range, seed);
  }

  @Override
  protected void checkValues() {
    final Integer min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Integer max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  public Integer getRandomValue() {
    return random.nextInt(range);
  }

  @Override
  protected Integer getDefaultMaxValue() {
    return Integer.MAX_VALUE;
  }

  @Override
  protected Integer getDefaultMinValue() {
    return Integer.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
