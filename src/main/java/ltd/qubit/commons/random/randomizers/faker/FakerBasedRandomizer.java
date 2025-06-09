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

import net.datafaker.Faker;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 基于<a href="https://github.com/DiUS/java-faker">Faker</a>的抽象{@link Randomizer}。
 *
 * @param <T>
 *         元素类型
 * @author 胡海星
 */
public abstract class FakerBasedRandomizer<T> extends AbstractRandomizer<T> {

  public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  protected final Faker faker;

  /**
   * 创建一个新的{@link FakerBasedRandomizer}，使用默认区域设置。
   */
  protected FakerBasedRandomizer() {
    faker = new Faker(DEFAULT_LOCALE);
  }

  /**
   * 创建一个新的{@link FakerBasedRandomizer}，使用指定的种子和默认区域设置。
   *
   * @param seed
   *         初始种子
   */
  protected FakerBasedRandomizer(final long seed) {
    this(seed, DEFAULT_LOCALE);
  }

  /**
   * 创建一个新的{@link FakerBasedRandomizer}，使用指定的种子和区域设置。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  protected FakerBasedRandomizer(final long seed, final Locale locale) {
    super(seed);
    faker = new Faker(locale, random);
  }
}
