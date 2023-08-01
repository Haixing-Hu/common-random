////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.net;

import java.net.URI;
import java.net.URISyntaxException;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link URI}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class UriRandomizer extends AbstractRandomizer<URI> {

  private final String[] uris = getPredefinedValuesOf("uris");

  /**
   * Create a new {@link UriRandomizer}.
   */
  public UriRandomizer() {
  }

  /**
   * Create a new {@link UriRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public UriRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public URI getRandomValue() {
    try {
      final int randomIndex = random.nextInt(uris.length);
      return new URI(uris[randomIndex]);
    } catch (final URISyntaxException e) {
      // predefined URIs are valid
      return null;
    }
  }
}
