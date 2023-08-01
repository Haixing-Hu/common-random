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
 * A {@link Randomizer} that generates random countries.
 *
 * @author Mahmoud Ben Hassine
 */
public class CountryRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link CountryRandomizer}.
   */
  public CountryRandomizer() {
  }

  /**
   * Create a new {@link CountryRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public CountryRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link CountryRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public CountryRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().country();
  }

}
