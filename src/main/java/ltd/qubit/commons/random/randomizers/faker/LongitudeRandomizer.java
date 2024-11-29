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
 * A {@link Randomizer} that generates random longitudes.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LongitudeRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link LongitudeRandomizer}.
   */
  public LongitudeRandomizer() {
  }

  /**
   * Create a new {@link LongitudeRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public LongitudeRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link LongitudeRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public LongitudeRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().longitude();
  }
}
