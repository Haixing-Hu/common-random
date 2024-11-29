////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicLong;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link AtomicLong}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class AtomicLongRandomizer implements Randomizer<AtomicLong> {

  private final LongRandomizer delegate;

  /**
   * Create a new {@link AtomicLongRandomizer}.
   */
  public AtomicLongRandomizer() {
    delegate = new LongRandomizer();
  }

  /**
   * Create a new {@link AtomicLongRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public AtomicLongRandomizer(final long seed) {
    delegate = new LongRandomizer(seed);
  }

  @Override
  public AtomicLong getRandomValue() {
    return new AtomicLong(delegate.getRandomValue());
  }
}
