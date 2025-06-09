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
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.random.Parameters.DEFAULT_DATES_RANGE;

/**
 * 在给定范围内生成一个随机的{@link OffsetTime}。
 *
 * @author 胡海星
 */
public class OffsetTimeRangeRandomizer extends
    AbstractRangeRandomizer<OffsetTime> {

  /**
   * 创建一个新的{@link OffsetTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public OffsetTimeRangeRandomizer(final OffsetTime min, final OffsetTime max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link OffsetTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public OffsetTimeRangeRandomizer(final OffsetTime min, final OffsetTime max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link OffsetTimeRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public OffsetTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final OffsetTime min = requireNonNull(range.getMin(), "range.min cannot be null");
    final OffsetTime max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected OffsetTime getDefaultMinValue() {
    return DEFAULT_DATES_RANGE.getMin().toOffsetDateTime().toOffsetTime();
  }

  @Override
  protected OffsetTime getDefaultMaxValue() {
    return DEFAULT_DATES_RANGE.getMax().toOffsetDateTime().toOffsetTime();
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalTime minTime = parameters.getTimeRange().getMin();
    final LocalTime maxTime = parameters.getTimeRange().getMax();
    final OffsetTime min = minTime.atOffset(OffsetDateTime.now().getOffset());
    final OffsetTime max = maxTime.atOffset(OffsetDateTime.now().getOffset());
    setRange(min, max);
  }

  @Override
  public OffsetTime getRandomValue() {
    final OffsetTime min = range.getMin();
    final OffsetTime max = range.getMax();
    final long minSecondOfDay = min.getLong(ChronoField.SECOND_OF_DAY);
    final long maxSecondOfDay = max.getLong(ChronoField.SECOND_OF_DAY);
    final long value = random.nextLong(new CloseRange<>(minSecondOfDay, maxSecondOfDay));
    return OffsetTime.of(LocalTime.ofSecondOfDay(value), ZoneOffset.UTC);
  }

}
