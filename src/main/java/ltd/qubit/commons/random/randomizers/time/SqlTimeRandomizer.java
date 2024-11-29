////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Time;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link Time}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SqlTimeRandomizer implements Randomizer<Time> {

  private final DateRandomizer delegate;

  /**
   * Create a new {@link SqlTimeRandomizer}.
   */
  public SqlTimeRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * Create a new {@link SqlTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public SqlTimeRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  @Override
  public Time getRandomValue() {
    return new Time(delegate.getRandomValue().getTime());
  }
}
