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
 * 生成一个随机的 {@link Locale}。
 *
 * @author 胡海星
 */
public class LocaleRandomizer extends AbstractRandomizer<Locale> {

  /**
   * 创建一个新的 {@link LocaleRandomizer}。
   */
  public LocaleRandomizer() {
  }

  /**
   * 创建一个新的 {@link LocaleRandomizer}。
   *
   * @param seed
   *         初始种子
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
