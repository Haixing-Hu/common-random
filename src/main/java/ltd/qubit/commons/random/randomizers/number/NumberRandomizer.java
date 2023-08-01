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
 * Generate a random {@link Number}.
 *
 * @author Andrew Neal (https://github.com/aeneal)
 */
public class NumberRandomizer extends AbstractRandomizer<Number> {

  /**
   * Create a new {@link NumberRandomizer}.
   */
  public NumberRandomizer() {
  }

  /**
   * Create a new {@link NumberRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public NumberRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Integer getRandomValue() {
    return random.nextInt();
  }
}
