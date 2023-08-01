////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Long}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LongRandomizer extends AbstractRandomizer<Long> {

  /**
   * Create a new {@link LongRandomizer}.
   */
  public LongRandomizer() {
  }

  /**
   * Create a new {@link LongRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public LongRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Long getRandomValue() {
    return random.nextLong();
  }
}
