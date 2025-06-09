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

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link LocalDate}。
 *
 * @author 胡海星
 */
public class LocalDateRangeRandomizer extends
    AbstractRangeRandomizer<LocalDate> {

  /**
   * 创建一个新的{@link LocalDateRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link LocalDateRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link LocalDateRangeRandomizer}。
   *
   * @param range
   *         范围
   */
  public LocalDateRangeRandomizer(final CloseRange<LocalDate> range) {
    super(range);
  }

  /**
   * 创建一个新的{@link LocalDateRangeRandomizer}。
   *
   * @param range
   *         范围
   * @param seed
   *         初始种子
   */
  public LocalDateRangeRandomizer(final CloseRange<LocalDate> range, final long seed) {
    super(range, seed);
  }

  /**
   * 创建一个新的{@link LocalDateRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public LocalDateRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final LocalDate min = requireNonNull(range.getMin(), "range.min cannot be null");
    final LocalDate max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected LocalDate getDefaultMinValue() {
    return LocalDate.MIN;
  }

  @Override
  protected LocalDate getDefaultMaxValue() {
    return LocalDate.MAX;
  }

  @Override
  public LocalDate getRandomValue() {
    return random.nextLocalDate(range);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    setRange(minDate, maxDate);
  }

}
