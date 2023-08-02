////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Timestamp;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link Timestamp}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SqlTimestampRandomizer implements Randomizer<Timestamp> {

  private final DateRandomizer delegate;

  /**
   * Create a new {@link SqlTimestampRandomizer}.
   */
  public SqlTimestampRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * Create a new {@link SqlTimestampRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public SqlTimestampRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  @Override
  public Timestamp getRandomValue() {
    return new Timestamp(delegate.getRandomValue().getTime());
  }
}
