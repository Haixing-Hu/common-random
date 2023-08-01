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
 * A {@link Randomizer} that generates random full names.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FullNameRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link FullNameRandomizer}.
   */
  public FullNameRandomizer() {
  }

  /**
   * Create a new {@link FullNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public FullNameRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link FullNameRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public FullNameRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.name().fullName();
  }
}
