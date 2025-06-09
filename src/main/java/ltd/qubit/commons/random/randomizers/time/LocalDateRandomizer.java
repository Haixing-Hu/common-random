////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * 生成随机{@link LocalDate}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class LocalDateRandomizer implements Randomizer<LocalDate> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;
  private final DayRandomizer dayRandomizer;

  /**
   * 创建一个新的{@link LocalDateRandomizer}。
   */
  public LocalDateRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
    dayRandomizer = new DayRandomizer();
  }

  /**
   * 创建一个新的{@link LocalDateRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public LocalDateRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    dayRandomizer = new DayRandomizer(seed);
  }

  /**
   * 生成一个随机的本地日期。
   *
   * @return 一个随机的{@link LocalDate}
   */
  @Override
  public LocalDate getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    final int randomDay = dayRandomizer.getRandomValue();
    return LocalDate.of(randomYear.getValue(), randomMonth.getValue(), randomDay);
  }
}
