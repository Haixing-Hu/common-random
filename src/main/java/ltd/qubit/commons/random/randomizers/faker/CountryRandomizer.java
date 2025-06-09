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
 * 一个 {@link Randomizer}，可以生成随机的国家。
 *
 * @author 胡海星
 */
public class CountryRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的 {@link CountryRandomizer}。
   */
  public CountryRandomizer() {
  }

  /**
   * 创建一个新的 {@link CountryRandomizer}。
   *
   * @param seed
   *     初始种子。
   */
  public CountryRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的 {@link CountryRandomizer}。
   *
   * @param seed
   *     初始种子。
   * @param locale
   *     要使用的区域设置。
   */
  public CountryRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的国家。
   *
   * @return
   *     一个随机的国家。
   */
  @Override
  public String getRandomValue() {
    return faker.address().country();
  }

}
