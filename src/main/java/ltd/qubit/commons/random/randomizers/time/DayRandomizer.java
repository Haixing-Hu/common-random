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
 * 生成介于{@link DayRandomizer#MIN_DAY}和{@link DayRandomizer#MAX_DAY}
 * 之间的随机日期值的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class DayRandomizer implements Randomizer<Integer> {

  public static final int MIN_DAY = 1;

  // 31 may break some LocalDateTime instances when the dayOfMonth is invalid
  public static final int MAX_DAY = 28;

  private final IntegerRangeRandomizer dayRandomizer;

  /**
   * 创建一个新的{@link DayRandomizer}。
   */
  public DayRandomizer() {
    dayRandomizer = new IntegerRangeRandomizer(MIN_DAY, MAX_DAY);
  }

  /**
   * 创建一个新的{@link DayRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public DayRandomizer(final long seed) {
    dayRandomizer = new IntegerRangeRandomizer(MIN_DAY, MAX_DAY, seed);
  }

  /**
   * 生成一个随机的日期值。
   *
   * @return 一个随机的日期值（1-28）
   */
  @Override
  public Integer getRandomValue() {
    return dayRandomizer.getRandomValue();
  }
}
