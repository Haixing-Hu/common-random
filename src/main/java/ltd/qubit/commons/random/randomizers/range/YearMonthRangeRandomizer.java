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
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link YearMonth}。
 *
 * @author 胡海星
 */
public class YearMonthRangeRandomizer extends
    AbstractRangeRandomizer<YearMonth> {

  /**
   * 创建一个新的{@link YearMonthRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public YearMonthRangeRandomizer(final YearMonth min, final YearMonth max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link YearMonthRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public YearMonthRangeRandomizer(final YearMonth min, final YearMonth max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link YearMonthRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public YearMonthRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final YearMonth min = requireNonNull(range.getMin(), "range.min cannot be null");
    final YearMonth max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected YearMonth getDefaultMinValue() {
    final ZonedDateTime defaultDateMin = Parameters.DEFAULT_DATES_RANGE.getMin();
    return YearMonth.of(defaultDateMin.getYear(), defaultDateMin.getMonth());
  }

  @Override
  protected YearMonth getDefaultMaxValue() {
    final ZonedDateTime defaultDateMax = Parameters.DEFAULT_DATES_RANGE.getMax();
    return YearMonth.of(defaultDateMax.getYear(), defaultDateMax.getMonth());
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final YearMonth min = YearMonth.of(minDate.getYear(), minDate.getMonth());
    final YearMonth max = YearMonth.of(maxDate.getYear(), maxDate.getMonth());
    setRange(min, max);
  }

  @Override
  public YearMonth getRandomValue() {
    final long minYear = range.getMin().getLong(ChronoField.YEAR);
    final long maxYear = range.getMax().getLong(ChronoField.YEAR);
    final long randomYear = random.nextLong(new CloseRange<>(minYear, maxYear));
    final long minMonth = range.getMin().getLong(ChronoField.MONTH_OF_YEAR);
    final long maxMonth = range.getMax().getLong(ChronoField.MONTH_OF_YEAR);
    final long randomMonth = random.nextLong(new CloseRange<>(minMonth, maxMonth));
    return YearMonth.of(Math.toIntExact(randomYear), Math.toIntExact(randomMonth));
  }
}
