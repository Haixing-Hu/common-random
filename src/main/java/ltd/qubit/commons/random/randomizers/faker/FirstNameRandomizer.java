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
 * 生成随机名字的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class FirstNameRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link FirstNameRandomizer}。
   */
  public FirstNameRandomizer() {
  }

  /**
   * 创建一个新的{@link FirstNameRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public FirstNameRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link FirstNameRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public FirstNameRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的名字。
   *
   * @return 一个随机的名字
   */
  @Override
  public String getRandomValue() {
    return faker.name().firstName();
  }
}
