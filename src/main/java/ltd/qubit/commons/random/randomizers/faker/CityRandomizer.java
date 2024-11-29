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
 * A {@link Randomizer} that generates random cities.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CityRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link CityRandomizer}.
   */
  public CityRandomizer() {
  }

  /**
   * Create a new {@link CityRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public CityRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link CityRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public CityRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().city();
  }
}
