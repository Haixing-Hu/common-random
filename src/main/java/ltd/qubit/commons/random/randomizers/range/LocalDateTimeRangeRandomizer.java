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
import java.time.LocalDateTime;
import java.time.LocalTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link LocalDateTime}。
 *
 * @author 胡海星
 */
public class LocalDateTimeRangeRandomizer extends
    AbstractRangeRandomizer<LocalDateTime> {

  /**
   * 创建一个新的{@link LocalDateTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public LocalDateTimeRangeRandomizer(final LocalDateTime min,
          final LocalDateTime max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link LocalDateTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public LocalDateTimeRangeRandomizer(final LocalDateTime min,
          final LocalDateTime max, final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link LocalDateTimeRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public LocalDateTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final LocalDateTime min = requireNonNull(range.getMin(), "range.min cannot be null");
    final LocalDateTime max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected LocalDateTime getDefaultMinValue() {
    return LocalDateTime.MIN;
  }

  @Override
  protected LocalDateTime getDefaultMaxValue() {
    return LocalDateTime.MAX;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final LocalTime minTime = parameters.getTimeRange().getMin();
    final LocalTime maxTime = parameters.getTimeRange().getMax();
    final LocalDateTime min = LocalDateTime.of(minDate, minTime);
    final LocalDateTime max = LocalDateTime.of(maxDate, maxTime);
    setRange(min, max);
  }

  @Override
  public LocalDateTime getRandomValue() {
    return random.nextLocalDateTime(range);
  }

}
