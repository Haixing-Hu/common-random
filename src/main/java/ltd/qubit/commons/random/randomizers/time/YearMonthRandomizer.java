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
import java.time.Year;
import java.time.YearMonth;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * 生成随机{@link YearMonth}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class YearMonthRandomizer implements Randomizer<YearMonth> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;

  /**
   * 创建一个新的{@link YearMonthRandomizer}。
   */
  public YearMonthRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
  }

  /**
   * 创建一个新的{@link YearMonthRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public YearMonthRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
  }

  /**
   * 生成一个随机的年月。
   *
   * @return 一个随机的{@link YearMonth}
   */
  @Override
  public YearMonth getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    return YearMonth.of(randomYear.getValue(), randomMonth.getValue());
  }
}
