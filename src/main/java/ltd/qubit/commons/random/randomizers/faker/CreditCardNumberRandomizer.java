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
 * 生成随机信用卡号码的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class CreditCardNumberRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link CreditCardNumberRandomizer}。
   */
  public CreditCardNumberRandomizer() {
  }

  /**
   * 创建一个新的{@link CreditCardNumberRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public CreditCardNumberRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link CreditCardNumberRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public CreditCardNumberRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的信用卡号码。
   *
   * @return 一个随机的信用卡号码
   */
  @Override
  public String getRandomValue() {
    return faker.business().creditCardNumber();
  }
}
