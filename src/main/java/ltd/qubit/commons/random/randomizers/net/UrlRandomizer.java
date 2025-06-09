////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.net;

import java.net.MalformedURLException;
import java.net.URL;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的 {@link URL}。
 *
 * @author 胡海星
 */
public class UrlRandomizer extends AbstractRandomizer<URL> {

  private final String[] urls = getPredefinedValuesOf("urls");

  /**
   * 创建一个新的 {@link UrlRandomizer}。
   */
  public UrlRandomizer() {
  }

  /**
   * 创建一个新的 {@link UrlRandomizer}。
   *
   * @param seed
   *     初始种子。
   */
  public UrlRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的 {@link URL}。
   *
   * @return
   *     一个随机的 {@link URL}。
   */
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
