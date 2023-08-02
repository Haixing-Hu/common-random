////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Generate a random {@link Year} in the given range.
 *
 * @author Haixing Hu
 */
public class YearRangeRandomizer extends AbstractRangeRandomizer<Year> {

  /**
   * Create a new {@link YearRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public YearRangeRandomizer(final Year min, final Year max) {
    super(min, max);
  }

  /**
   * Create a new {@link YearRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public YearRangeRandomizer(final Year min, final Year max, final long seed) {
    super(min, max, seed);
  }

  public YearRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final Year min = requireNonNull("range.min", range.getMin());
    final Year max = requireNonNull("range.max", range.getMax());
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
