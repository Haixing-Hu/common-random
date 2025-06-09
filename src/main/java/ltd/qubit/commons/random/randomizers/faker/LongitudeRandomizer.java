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
 * 生成随机经度的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class LongitudeRandomizer extends FakerBasedRandomizer<String> {

  /**
   * 创建一个新的{@link LongitudeRandomizer}。
   */
  public LongitudeRandomizer() {
  }

  /**
   * 创建一个新的{@link LongitudeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public LongitudeRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link LongitudeRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param locale
   *         要使用的区域设置
   */
  public LongitudeRandomizer(final long seed, final Locale locale) {
    super(seed, locale);
  }

  /**
   * 生成一个随机的经度。
   *
   * @return 一个随机的经度
   */
  @Override
  public String getRandomValue() {
    return faker.address().longitude();
  }
}
