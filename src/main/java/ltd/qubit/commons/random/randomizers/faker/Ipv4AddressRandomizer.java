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
 * A {@link Randomizer} that generates random IPv4 addresses.
 *
 * @author Michael Düsterhus
 */
public class Ipv4AddressRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link Ipv4AddressRandomizer}.
   */
  public Ipv4AddressRandomizer() {
  }

  /**
   * Create a new {@link Ipv4AddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public Ipv4AddressRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link Ipv4AddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public Ipv4AddressRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.internet().ipV4Address();
  }

}
