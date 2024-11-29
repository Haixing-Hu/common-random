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
 * A {@link Randomizer} that generates random IPv6 addresses.
 *
 * @author Michael Düsterhus
 */
public class Ipv6AddressRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link Ipv6AddressRandomizer}.
   */
  public Ipv6AddressRandomizer() {
  }

  /**
   * Create a new {@link Ipv6AddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public Ipv6AddressRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link Ipv6AddressRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public Ipv6AddressRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.internet().ipV6Address();
  }

}
