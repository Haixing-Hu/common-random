////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.net;

import java.net.URI;
import java.net.URISyntaxException;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的 {@link URI}。
 *
 * @author 胡海星
 */
public class UriRandomizer extends AbstractRandomizer<URI> {

  private final String[] uris = getPredefinedValuesOf("uris");

  /**
   * 创建一个新的 {@link UriRandomizer}。
   */
  public UriRandomizer() {
  }

  /**
   * 创建一个新的 {@link UriRandomizer}。
   *
   * @param seed
   *     初始种子。
   */
  public UriRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的 {@link URI}。
   *
   * @return
   *     一个随机的 {@link URI}。
   */
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
