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
 * A {@link Randomizer} that generates random paragraphs.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ParagraphRandomizer extends FakerBasedRandomizer<String> {

  /**
   * Create a new {@link ParagraphRandomizer}.
   */
  public ParagraphRandomizer() {
  }

  /**
   * Create a new {@link ParagraphRandomizer}.
   *
   * @param seed
   *         the initial seed
   */
  public ParagraphRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link ParagraphRandomizer}.
   *
   * @param seed
   *         the initial seed
   * @param locale
   *         the locale to use
   */
  public ParagraphRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  @Override
  public String getRandomValue() {
    return faker.lorem().paragraph();
  }
}
