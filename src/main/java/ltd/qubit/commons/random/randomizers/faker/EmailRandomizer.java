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
 * A {@link Randomizer} that generates random emails.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EmailRandomizer extends FakerBasedRandomizer<String> {

  private boolean safe;

  /**
   * Create a new {@link EmailRandomizer}.
   */
  public EmailRandomizer() {
  }

  /**
   * Create a new {@link EmailRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public EmailRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link EmailRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public EmailRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * Create a new {@link EmailRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   * @param safe
   *         true to generate safe emails (invalid domains), false otherwise
   */
  public EmailRandomizer(final long seed, final Locale locale,
          final boolean safe) {
    super(seed, locale);
    this.safe = safe;
  }

  @Override
  public String getRandomValue() {
    return safe ? faker.internet().safeEmailAddress()
                : faker.internet().emailAddress();
  }

}
