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
 * A {@link Randomizer} that generates random zip codes.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZipCodeRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link ZipCodeRandomizer}.
   */
  public ZipCodeRandomizer() {
  }

  /**
   * Create a new {@link ZipCodeRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public ZipCodeRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link ZipCodeRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public ZipCodeRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().zipCode();
  }
}
