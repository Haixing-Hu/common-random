////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link ZonedDateTime}。
 *
 * @author 胡海星
 */
public class ZonedDateTimeRangeRandomizer extends AbstractRangeRandomizer<ZonedDateTime> {

  /**
   * 创建一个新的{@link ZonedDateTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public ZonedDateTimeRangeRandomizer(final ZonedDateTime min,
          final ZonedDateTime max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link ZonedDateTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public ZonedDateTimeRangeRandomizer(final ZonedDateTime min,
          final ZonedDateTime max, final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link ZonedDateTimeRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public ZonedDateTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final ZonedDateTime min = requireNonNull(range.getMin(), "range.min cannot be null");
    final ZonedDateTime max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected ZonedDateTime getDefaultMinValue() {
    return Parameters.DEFAULT_DATES_RANGE.getMin();
  }

  @Override
  protected ZonedDateTime getDefaultMaxValue() {
    return Parameters.DEFAULT_DATES_RANGE.getMax();
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final LocalTime minTime = parameters.getTimeRange().getMin();
    final LocalTime maxTime = parameters.getTimeRange().getMax();
    final ZoneId zone = ZonedDateTime.now().getZone();
    final ZonedDateTime min = LocalDateTime.of(minDate, minTime).atZone(zone);
    final ZonedDateTime max = LocalDateTime.of(maxDate, maxTime).atZone(zone);
    setRange(min, max);
  }

  @Override
  public ZonedDateTime getRandomValue() {
    final ZonedDateTime min = range.getMin();
    final ZonedDateTime max = range.getMax();
    final long minSeconds = min.toEpochSecond();
    final long maxSeconds = max.toEpochSecond();
    final long seconds = random.nextLong(new CloseRange<>(minSeconds, maxSeconds));
    final long minNanoSeconds = min.getNano();
    final long maxNanoSeconds = max.getNano();
    final long nanoSeconds = random.nextLong(new CloseRange<>(minNanoSeconds, maxNanoSeconds));
    return ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanoSeconds), min.getZone());
  }

}
