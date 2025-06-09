////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Month;
import java.time.MonthDay;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * 生成随机{@link MonthDay}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class MonthDayRandomizer implements Randomizer<MonthDay> {

  private final EnumRandomizer<Month> monthRandomizer;
  private final DayRandomizer dayRandomizer;

  /**
   * 创建一个新的{@link MonthDayRandomizer}。
   */
  public MonthDayRandomizer() {
    monthRandomizer = new EnumRandomizer<>(Month.class);
    dayRandomizer = new DayRandomizer();
  }

  /**
   * 创建一个新的{@link MonthDayRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public MonthDayRandomizer(final long seed) {
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    dayRandomizer = new DayRandomizer(seed);
  }

  /**
   * 生成一个随机的月日。
   *
   * @return 一个随机的{@link MonthDay}
   */
  @Override
  public MonthDay getRandomValue() {
    final Month randomMonth = monthRandomizer.getRandomValue();
    final int randomDay = dayRandomizer.getRandomValue();
    return MonthDay.of(randomMonth, randomDay);
  }
}
