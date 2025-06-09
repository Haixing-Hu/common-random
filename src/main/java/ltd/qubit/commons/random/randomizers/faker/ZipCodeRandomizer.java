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
 * 生成随机邮政编码的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class ZipCodeRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link ZipCodeRandomizer}。
   */
  public ZipCodeRandomizer() {
  }

  /**
   * 创建一个新的{@link ZipCodeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ZipCodeRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link ZipCodeRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public ZipCodeRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的邮政编码。
   *
   * @return 一个随机的邮政编码
   */
  @Override
  public String getRandomValue() {
    return faker.address().zipCode();
  }
}
