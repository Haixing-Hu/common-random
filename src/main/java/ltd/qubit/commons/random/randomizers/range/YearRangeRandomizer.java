////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoField;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link Year}。
 *
 * @author 胡海星
 */
public class YearRangeRandomizer extends AbstractRangeRandomizer<Year> {

  /**
   * 创建一个新的{@link YearRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public YearRangeRandomizer(final Year min, final Year max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link YearRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public YearRangeRandomizer(final Year min, final Year max, final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link YearRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public YearRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final Year min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Year max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected Year getDefaultMinValue() {
    return Year.of(Parameters.DEFAULT_DATES_RANGE.getMin().getYear());
  }

  @Override
  protected Year getDefaultMaxValue() {
    return Year.of(Parameters.DEFAULT_DATES_RANGE.getMax().getYear());
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final Year min = Year.of(minDate.getYear());
    final Year max = Year.of(maxDate.getYear());
    setRange(min, max);
  }

  @Override
  public Year getRandomValue() {
    final long minYear = range.getMin().getLong(ChronoField.YEAR);
    final long maxYear = range.getMax().getLong(ChronoField.YEAR);
    final long randomYear = random.nextLong(new CloseRange<>(minYear, maxYear));
    return Year.of(Math.toIntExact(randomYear));
  }
}
