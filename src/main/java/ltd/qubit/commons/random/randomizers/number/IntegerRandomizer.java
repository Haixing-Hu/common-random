////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Integer}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class IntegerRandomizer extends AbstractRandomizer<Integer> {

  /**
   * Create a new {@link IntegerRandomizer}.
   */
  public IntegerRandomizer() {
  }

  /**
   * Create a new {@link IntegerRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public IntegerRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Integer getRandomValue() {
    return random.nextInt();
  }
}
