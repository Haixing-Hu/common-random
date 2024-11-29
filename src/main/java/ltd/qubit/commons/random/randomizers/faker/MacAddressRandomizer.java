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
 * A {@link Randomizer} that generates random mac addresses.
 *
 * @author Michael Düsterhus
 */
public class MacAddressRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link MacAddressRandomizer}.
   */
  public MacAddressRandomizer() {
  }

  /**
   * Create a new {@link MacAddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public MacAddressRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link MacAddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public MacAddressRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.internet().macAddress();
  }

}
