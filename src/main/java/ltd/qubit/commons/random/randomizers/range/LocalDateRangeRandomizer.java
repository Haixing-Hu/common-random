////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalDate;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

/**
 * Generate a random {@link LocalDate} in the given range.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class LocalDateRangeRandomizer extends
    AbstractRangeRandomizer<LocalDate> {

  /**
   * Create a new {@link LocalDateRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max) {
    super(min, max);
  }

  /**
   * Create a new {@link LocalDateRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public LocalDateRangeRandomizer(final LocalDate min, final LocalDate max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * Create a new {@link LocalDateRangeRandomizer}.
   *
   * @param range
   *         the range
   */
  public LocalDateRangeRandomizer(final CloseRange<LocalDate> range) {
    super(range);
  }

  /**
   * Create a new {@link LocalDateRangeRandomizer}.
   *
   * @param range
   *         the range
   * @param seed
   *         initial seed
   */
  public LocalDateRangeRandomizer(final CloseRange<LocalDate> range, final long seed) {
    super(range, seed);
  }

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
