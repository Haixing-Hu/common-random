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

import net.datafaker.Faker;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Abstract {@link Randomizer} based on <a href="https://github.com/DiUS/java-faker">Faker</a>.
 *
 * @param <T>
 *         the element type
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class FakerBasedRandomizer<T> extends AbstractRandomizer<T> {

  public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  protected final Faker faker;

  protected FakerBasedRandomizer() {
    faker = new Faker(DEFAULT_LOCALE);
  }

  protected FakerBasedRandomizer(final long seed) {
    this(seed, DEFAULT_LOCALE);
  }

  protected FakerBasedRandomizer(final long seed, final Locale locale) {
    super(seed);
    faker = new Faker(locale, random);
  }
}
