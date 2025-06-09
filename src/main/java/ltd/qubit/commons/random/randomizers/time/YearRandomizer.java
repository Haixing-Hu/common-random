////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Year;
import java.time.ZonedDateTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

/**
 * 生成随机{@link Year}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class YearRandomizer implements Randomizer<Year> {

  private final IntegerRangeRandomizer yearRandomizer;

  /**
   * 创建一个新的{@link YearRandomizer}。
   */
  public YearRandomizer() {
    final CloseRange<ZonedDateTime> range = Parameters.DEFAULT_DATES_RANGE;
    yearRandomizer = new IntegerRangeRandomizer(range.getMin().getYear(),
            range.getMax().getYear());
  }

  /**
   * 创建一个新的{@link YearRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public YearRandomizer(final long seed) {
    final CloseRange<ZonedDateTime> range = Parameters.DEFAULT_DATES_RANGE;
    yearRandomizer = new IntegerRangeRandomizer(range.getMin().getYear(),
            range.getMax().getYear(), seed);
  }

  /**
   * 生成一个随机的年份。
   *
   * @return 一个随机的{@link Year}
   */
  @Override
  public Year getRandomValue() {
    final int randomYear = yearRandomizer.getRandomValue();
    return Year.of(randomYear);
  }
}
