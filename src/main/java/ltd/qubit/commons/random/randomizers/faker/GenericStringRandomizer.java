////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.faker;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 从单词列表中生成随机值的通用{@link Randomizer}。
 *
 * @author 胡海星
 */
public class GenericStringRandomizer extends FakerBasedRandomizer<String> {

  private final String[] words;

  /**
   * 创建一个新的{@link GenericStringRandomizer}。
   *
   * @param words
   *         此随机化器将从中生成随机值的单词列表
   */
  public GenericStringRandomizer(final String[] words) {
    super();
    this.words = words;
  }

  /**
   * 创建一个新的{@link GenericStringRandomizer}。
   *
   * @param words
   *         此随机化器将从中生成随机值的单词列表
   * @param seed
   *         初始种子
   */
  public GenericStringRandomizer(final String[] words, final long seed) {
    super(seed);
    this.words = words;
  }

  /**
   * 从预定义的单词列表中生成一个随机字符串。
   *
   * @return 一个随机的字符串
   */
  @Override
  public String getRandomValue() {
    return faker.options().option(words);
  }

}
