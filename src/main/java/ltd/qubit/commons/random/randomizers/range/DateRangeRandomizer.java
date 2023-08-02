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
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.sql.Date.valueOf;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Generate a random {@link Date} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class DateRangeRandomizer extends AbstractRangeRandomizer<Date> {

  private TimeUnit precision = Precision.DEFAULT_VALUE;

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   */
  public DateRangeRandomizer(final Date min, final Date max) {
    super(min, max);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param precision
   *     the precision of the generated date time.
   */
  public DateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision) {
    super(min, max);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param seed
   *     initial seed
   */
  public DateRangeRandomizer(final Date min, final Date max, final long seed) {
    super(min, max, seed);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
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
  public DateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision, final long seed) {
    super(min, max, seed);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param range
   *     the range.
   */
  public DateRangeRandomizer(final CloseRange<Date> range) {
    super(range);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param range
   *     the range.
   * @param precision
   *     the precision of the generated date time.
   */
  public DateRangeRandomizer(final CloseRange<Date> range,
      final TimeUnit precision) {
    super(range);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param range
   *     the range.
   * @param seed
   *     initial seed
   */
  public DateRangeRandomizer(final CloseRange<Date> range, final long seed) {
    super(range, seed);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param range
   *     the range.
   * @param precision
   *     the precision of the generated date time.
   * @param seed
   *     initial seed
   */
  public DateRangeRandomizer(final CloseRange<Date> range,
      final TimeUnit precision, final long seed) {
    super(range, seed);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link DateRangeRandomizer}.
   *
   * @param parameters
   *     the random generator parameters.
   */
  public DateRangeRandomizer(final Parameters parameters) {
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
  public DateRangeRandomizer(final Parameters parameters,
      final TimeUnit precision) {
    super(parameters.getSeed());
    setParameters(parameters);
    this.precision = requireNonNull("precision", precision);
  }

  @Override
  protected void checkValues() {
    final Date min = requireNonNull("range.min.", range.getMin());
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
    return DateUtils.truncateInPlace(result, precision);
  }

}
