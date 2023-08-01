////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.net;

import java.net.MalformedURLException;
import java.net.URL;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link URL}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class UrlRandomizer extends AbstractRandomizer<URL> {

  private final String[] urls = getPredefinedValuesOf("urls");

  /**
   * Create a new {@link UrlRandomizer}.
   */
  public UrlRandomizer() {
  }

  /**
   * Create a new {@link UrlRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public UrlRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public URL getRandomValue() {
    try {
      final int randomIndex = random.nextInt(urls.length);
      return new URL(urls[randomIndex]);
    } catch (final MalformedURLException e) {
      // predefined URLs are valid
      return null;
    }
  }
}
