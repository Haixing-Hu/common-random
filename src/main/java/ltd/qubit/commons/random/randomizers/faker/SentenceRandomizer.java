////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.faker;

import java.util.Locale;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random sentences.
 *
 * @author Mahmoud Ben Hassine
 */
public class SentenceRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link SentenceRandomizer}.
   */
  public SentenceRandomizer() {
  }

  /**
   * Create a new {@link SentenceRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public SentenceRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link SentenceRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public SentenceRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.lorem().sentence();
  }
}
