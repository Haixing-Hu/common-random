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
 * 生成随机全名的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class FullNameRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link FullNameRandomizer}。
   */
  public FullNameRandomizer() {
  }

  /**
   * 创建一个新的{@link FullNameRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public FullNameRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link FullNameRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public FullNameRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的全名。
   *
   * @return 一个随机的全名
   */
  @Override
  public String getRandomValue() {
    return faker.name().fullName();
  }
}
