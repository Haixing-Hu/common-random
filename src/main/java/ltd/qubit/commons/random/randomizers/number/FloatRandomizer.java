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
 * Generate a random {@link Float}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FloatRandomizer extends AbstractRandomizer<Float> {

  /**
   * Create a new {@link FloatRandomizer}.
   */
  public FloatRandomizer() {
  }

  /**
   * Create a new {@link FloatRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public FloatRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Float getRandomValue() {
    return random.nextFloat();
  }
}
