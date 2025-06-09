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
 * 生成随机电子邮件的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class EmailRandomizer extends FakerBasedRandomizer<String> {

  private boolean safe;

  /**
   * 创建一个新的{@link EmailRandomizer}。
   */
  public EmailRandomizer() {
  }

  /**
   * 创建一个新的{@link EmailRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public EmailRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link EmailRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public EmailRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 创建一个新的{@link EmailRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   * @param safe
   *         true表示生成安全的电子邮件（无效域名），false表示其他情况
   */
  public EmailRandomizer(final long seed, final Locale locale,
          final boolean safe) {
    super(seed, locale);
    this.safe = safe;
  }

  /**
   * 生成一个随机的电子邮件地址。
   *
   * @return 一个随机的电子邮件地址
   */
  @Override
  public String getRandomValue() {
    return safe ? faker.internet().safeEmailAddress()
                : faker.internet().emailAddress();
  }

}
