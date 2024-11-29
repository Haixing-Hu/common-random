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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

/**
 * Generate a random {@link OffsetDateTime} in the given range.
 *
 * @author Haixing Hu
 */
public class OffsetDateTimeRangeRandomizer extends
    AbstractRangeRandomizer<OffsetDateTime> {

  /**
   * Create a new {@link OffsetDateTimeRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public OffsetDateTimeRangeRandomizer(final OffsetDateTime min,
          final OffsetDateTime max) {
    super(min, max);
  }

  /**
   * Create a new {@link OffsetDateTimeRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public OffsetDateTimeRangeRandomizer(final OffsetDateTime min,
          final OffsetDateTime max, final long seed) {
    super(min, max, seed);
  }

  public OffsetDateTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final OffsetDateTime min = requireNonNull(range.getMin(), "range.min cannot be null");
    final OffsetDateTime max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected OffsetDateTime getDefaultMinValue() {
    return Parameters.DEFAULT_DATES_RANGE.getMin().toOffsetDateTime();
  }

  @Override
  protected OffsetDateTime getDefaultMaxValue() {
    return Parameters.DEFAULT_DATES_RANGE.getMax().toOffsetDateTime();
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final LocalTime minTime = parameters.getTimeRange().getMin();
    final LocalTime maxTime = parameters.getTimeRange().getMax();
    final ZoneOffset offset = OffsetDateTime.now().getOffset();
    final OffsetDateTime min = LocalDateTime.of(minDate, minTime).atOffset(offset);
    final OffsetDateTime max = LocalDateTime.of(maxDate, maxTime).atOffset(offset);
    setRange(min, max);
  }

  @Override
  public OffsetDateTime getRandomValue() {
    final OffsetDateTime min = range.getMin();
    final OffsetDateTime max = range.getMax();
    final long minSeconds = min.toEpochSecond();
    final long maxSeconds = max.toEpochSecond();
    final long seconds = random.nextLong(new CloseRange<>(minSeconds, maxSeconds));
    final long minNanoSeconds = min.getNano();
    final long maxNanoSeconds = max.getNano();
    final long nanoSeconds = random.nextLong(new CloseRange<>(minNanoSeconds, maxNanoSeconds));
    return OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanoSeconds), min.getOffset());
  }
}
