////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.util.Locale;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Locale}.
 *
 * @author Pascal Schumacher
 */
public class LocaleRandomizer extends AbstractRandomizer<Locale> {

  /**
   * Create a new {@link LocaleRandomizer}.
   */
  public LocaleRandomizer() {
  }

  /**
   * Create a new {@link LocaleRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public LocaleRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Locale getRandomValue() {
    final Locale[] availableLocales = Locale.getAvailableLocales();
    return availableLocales[random.nextInt(availableLocales.length)];
  }
}
