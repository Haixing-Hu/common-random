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
 * A {@link Randomizer} that generates random first names.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FirstNameRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link FirstNameRandomizer}.
   */
  public FirstNameRandomizer() {
  }

  /**
   * Create a new {@link FirstNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public FirstNameRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link FirstNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public FirstNameRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.name().firstName();
  }
}
