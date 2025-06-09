////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * 生成介于{@link HourRandomizer#MIN_HOUR}和{@link HourRandomizer#MAX_HOUR}
 * 之间的随机小时值的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class HourRandomizer implements Randomizer<Integer> {

  public static final int MIN_HOUR = 0;
  public static final int MAX_HOUR = 23;

  private final IntegerRangeRandomizer hourRandomizer;

  /**
   * 创建一个新的{@link HourRandomizer}。
   */
  public HourRandomizer() {
    hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR);
  }

  /**
   * 创建一个新的{@link HourRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public HourRandomizer(final long seed) {
    hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR, seed);
  }

  /**
   * 生成一个随机的小时值。
   *
   * @return 一个随机的小时值（0-23）
   */
  @Override
  public Integer getRandomValue() {
    return hourRandomizer.getRandomValue();
  }
}
