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
 * A {@link Randomizer} that generates random street names.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class StreetRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link StreetRandomizer}.
   */
  public StreetRandomizer() {
  }

  /**
   * Create a new {@link StreetRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public StreetRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link StreetRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public StreetRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().streetName();
  }

}
