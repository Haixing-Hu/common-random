////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Boolean}.
 *
 * @author Mahmoud Ben Hassine
 */
public class BooleanRandomizer extends AbstractRandomizer<Boolean> {

  /**
   * Create a new {@link BooleanRandomizer}.
   */
  public BooleanRandomizer() {
  }

  /**
   * Create a new {@link BooleanRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public BooleanRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Boolean getRandomValue() {
    return random.nextBoolean();
  }
}
