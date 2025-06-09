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
 * 生成介于{@link MinuteRandomizer#MIN_MINUTE}和{@link MinuteRandomizer#MAX_MINUTE}
 * 之间的随机分钟值的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class MinuteRandomizer implements Randomizer<Integer> {

  public static final int MIN_MINUTE = 0;
  public static final int MAX_MINUTE = 59;

  private final IntegerRangeRandomizer minuteRandomizer;

  /**
   * 创建一个新的{@link MinuteRandomizer}。
   */
  public MinuteRandomizer() {
    minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE);
  }

  /**
   * 创建一个新的{@link MinuteRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public MinuteRandomizer(final long seed) {
    minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE, seed);
  }

  /**
   * 生成一个随机的分钟值。
   *
   * @return 一个随机的分钟值（0-59）
   */
  @Override
  public Integer getRandomValue() {
    return minuteRandomizer.getRandomValue();
  }
}
