////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link Date}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SqlDateRandomizer implements Randomizer<Date> {

  private final DateRandomizer delegate;

  /**
   * Create a new {@link SqlDateRandomizer}.
   */
  public SqlDateRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * Create a new {@link SqlDateRandomizer}.
   *
   * @param precision
   *     the precision of the generated date time.
   */
  public SqlDateRandomizer(final TimeUnit precision) {
    delegate = new DateRandomizer(precision);
  }

  /**
   * Create a new {@link SqlDateRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public SqlDateRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  /**
   * Create a new {@link SqlDateRandomizer}.
   *
   * @param precision
   *     the precision of the generated date time.
   * @param seed
   *         initial seed
   */
  public SqlDateRandomizer(final TimeUnit precision, final long seed) {
    delegate = new DateRandomizer(precision, seed);
  }

  @Override
  public Date getRandomValue() {
    return new Date(delegate.getRandomValue().getTime());
  }

}
