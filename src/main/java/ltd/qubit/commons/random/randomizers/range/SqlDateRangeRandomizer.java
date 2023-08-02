////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.sql.Date.valueOf;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.DateUtils.truncateInPlace;

/**
 * Generate a random {@link java.sql.Date} in a given range.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class SqlDateRangeRandomizer extends AbstractRangeRandomizer<Date> {

  private TimeUnit precision = Precision.DEFAULT_VALUE;

  /**
   * Create a new {@link SqlDateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   */
  public SqlDateRangeRandomizer(final Date min, final Date max) {
    super(min, max);
  }

  /**
   * Create a new {@link SqlDateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param precision
   *     the precision of the generated date time.
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision) {
    super(min, max);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link SqlDateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param seed
   *     initial seed
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final long seed) {
    super(min, max, seed);
  }

  /**
   * Create a new {@link SqlDateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param precision
   *     the precision of the generated date time.
   * @param seed
   *     initial seed
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision, final long seed) {
    super(min, max, seed);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param parameters
   *     the random generator parameters.
   */
  public SqlDateRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param parameters
   *     the random generator parameters.
   * @param precision
   *     the precision of the generated date time.
   */
  public SqlDateRangeRandomizer(final Parameters parameters,
      final TimeUnit precision) {
    super(parameters.getSeed());
    setParameters(parameters);
    this.precision = requireNonNull("precision", precision);
  }

  @Override
  protected void checkValues() {
    final Date min = requireNonNull("range.min", range.getMin());
    final Date max = requireNonNull("range.max", range.getMax());
    if (min.after(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected Date getDefaultMinValue() {
    return new Date(Long.MIN_VALUE);
  }

  @Override
  protected Date getDefaultMaxValue() {
    return new Date(Long.MAX_VALUE);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final Date minDate = valueOf(localDateRange.getMin());
    final Date maxDate = valueOf(localDateRange.getMax());
    setRange(minDate, maxDate);
  }

  @Override
  public Date getRandomValue() {
    final long minDateTime = range.getMin().getTime();
    final long maxDateTime = range.getMax().getTime();
    final long millis = random.nextLong(new CloseRange<>(minDateTime, maxDateTime));
    final Date result = new Date(millis);
    return truncateInPlace(result, precision);
  }

}
