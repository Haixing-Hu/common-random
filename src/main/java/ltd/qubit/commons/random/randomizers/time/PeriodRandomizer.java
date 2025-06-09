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
import java.time.Period;
import java.time.Year;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * 生成随机{@link Period}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class PeriodRandomizer implements Randomizer<Period> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;
  private final DayRandomizer dayRandomizer;

  /**
   * 创建一个新的{@link PeriodRandomizer}。
   */
  public PeriodRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
    dayRandomizer = new DayRandomizer();
  }

  /**
   * 创建一个新的{@link PeriodRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public PeriodRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    dayRandomizer = new DayRandomizer(seed);
  }

  /**
   * 生成一个随机的时间段。
   *
   * @return 一个随机的{@link Period}
   */
  @Override
  public Period getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    final int randomDay = dayRandomizer.getRandomValue();
    return Period.of(randomYear.getValue(), randomMonth.getValue(), randomDay);
  }
}
