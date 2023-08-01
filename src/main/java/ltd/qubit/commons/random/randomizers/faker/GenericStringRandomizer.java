////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.faker;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A generic {@link Randomizer} that generates random values from a list of
 * words.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class GenericStringRandomizer extends FakerBasedRandomizer<String> {

  private final String[] words;

  /**
   * Create a new {@link GenericStringRandomizer}.
   *
   * @param words
   *         the list of words from which this randomizer will generate random
   *         values.
   */
  public GenericStringRandomizer(final String[] words) {
    super();
    this.words = words;
  }

  /**
   * Create a new {@link GenericStringRandomizer}.
   *
   * @param words
   *         the list of words from which this randomizer will generate random
   *         values.
   * @param seed
   *         the initial seed
   */
  public GenericStringRandomizer(final String[] words, final long seed) {
    super(seed);
    this.words = words;
  }

  @Override
  public String getRandomValue() {
    return faker.options().option(words);
  }

}
