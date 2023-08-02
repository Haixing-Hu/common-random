////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.DateRangeRandomizer;

import static ltd.qubit.commons.random.Parameters.DEFAULT_DATES_RANGE;

/**
 * Generate a random {@link Date}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DateRandomizer implements Randomizer<Date> {

  private final DateRangeRandomizer delegate;

  /**
   * Create a new {@link DateRandomizer}.
   */
  public DateRandomizer() {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max);
  }

  /**
   * Create a new {@link DateRandomizer}.
   *
   * @param precision
   *     the precision of the generated date time.
   */
  public DateRandomizer(final TimeUnit precision) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, precision);
  }

  /**
   * Create a new {@link DateRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public DateRandomizer(final long seed) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, seed);
  }

  /**
   * Create a new {@link DateRandomizer}.
   *
   * @param precision
   *     the precision of the generated date time.
   * @param seed
   *         initial seed
   */
  public DateRandomizer(final TimeUnit precision, final long seed) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, precision, seed);
  }

  @Override
  public Date getRandomValue() {
    return delegate.getRandomValue();
  }
}
