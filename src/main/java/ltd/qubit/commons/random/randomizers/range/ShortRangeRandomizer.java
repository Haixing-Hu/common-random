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

/**
 * 在给定范围内生成一个随机的{@link Short}。
 *
 * @author 胡海星
 */
public class ShortRangeRandomizer extends AbstractRangeRandomizer<Short> {

  /**
   * 创建一个新的{@link ShortRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public ShortRangeRandomizer(final Short min, final Short max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link ShortRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public ShortRangeRandomizer(final Short min, final Short max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    range.check();
  }

  @Override
  public Short getRandomValue() {
    return random.nextShort(range);
  }

  @Override
  protected Short getDefaultMaxValue() {
    return Short.MAX_VALUE;
  }

  @Override
  protected Short getDefaultMinValue() {
    return Short.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
