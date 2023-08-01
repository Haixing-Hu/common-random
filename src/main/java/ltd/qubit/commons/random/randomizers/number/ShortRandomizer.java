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
 * Generate a random {@link Short}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ShortRandomizer extends AbstractRandomizer<Short> {

  /**
   * Create a new {@link ShortRandomizer}.
   */
  public ShortRandomizer() {
  }

  /**
   * Create a new {@link ShortRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public ShortRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Short getRandomValue() {
    return (short) random.nextInt();
  }
}
