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
 * A {@link Randomizer} that generates random states.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class StateRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link StateRandomizer}.
   */
  public StateRandomizer() {
  }

  /**
   * Create a new {@link StateRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public StateRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link StateRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public StateRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.address().state();
  }
}
