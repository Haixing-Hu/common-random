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
 * 生成随机单词的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class WordRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link WordRandomizer}。
   */
  public WordRandomizer() {
  }

  /**
   * 创建一个新的{@link WordRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public WordRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link WordRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public WordRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的单词。
   *
   * @return 一个随机的单词
   */
  @Override
  public String getRandomValue() {
    return faker.lorem().word();
  }
}
