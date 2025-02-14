////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Character}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class CharacterRandomizer extends AbstractRandomizer<Character> {

  /**
   * Create a new {@link CharacterRandomizer}.
   */
  public CharacterRandomizer() {
    super();
  }

  /**
   * Create a new {@link CharacterRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public CharacterRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Character getRandomValue() {
    return random.nextLetterChar();
  }
}
