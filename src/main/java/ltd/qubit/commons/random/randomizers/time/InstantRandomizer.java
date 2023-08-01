////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Instant;
import java.util.Date;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random {@link Instant}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class InstantRandomizer implements Randomizer<Instant> {

  private final DateRandomizer dateRandomizer;

  /**
   * Create a new {@link InstantRandomizer}.
   */
  public InstantRandomizer() {
    dateRandomizer = new DateRandomizer();
  }

  /**
   * Create a new {@link InstantRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public InstantRandomizer(final long seed) {
    dateRandomizer = new DateRandomizer(seed);
  }

  @Override
  public Instant getRandomValue() {
    final Date randomDate = dateRandomizer.getRandomValue();
    return Instant.ofEpochMilli(randomDate.getTime());
  }
}
