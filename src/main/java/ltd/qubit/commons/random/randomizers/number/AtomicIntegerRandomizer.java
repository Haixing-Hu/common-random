////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicInteger;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link AtomicInteger}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class AtomicIntegerRandomizer implements Randomizer<AtomicInteger> {

  private final IntegerRandomizer delegate;

  /**
   * Create a new {@link AtomicIntegerRandomizer}.
   */
  public AtomicIntegerRandomizer() {
    delegate = new IntegerRandomizer();
  }

  /**
   * Create a new {@link AtomicIntegerRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public AtomicIntegerRandomizer(final long seed) {
    delegate = new IntegerRandomizer(seed);
  }

  @Override
  public AtomicInteger getRandomValue() {
    return new AtomicInteger(delegate.getRandomValue());
  }
}
