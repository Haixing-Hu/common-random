////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.faker;

import java.util.Locale;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random last names.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LastNameRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link LastNameRandomizer}.
   */
  public LastNameRandomizer() {
  }

  /**
   * Create a new {@link LastNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public LastNameRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link LastNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public LastNameRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.name().lastName();
  }
}
