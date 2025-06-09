////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.api.Randomizer;

import static java.util.ResourceBundle.getBundle;

/**
 * {@link Randomizer} 实现的基类。
 *
 * @author 胡海星
 */
public abstract class AbstractRandomizer<T> implements Randomizer<T> {

  protected final RandomEx random;

  /**
   * 构造一个 {@link AbstractRandomizer}。
   */
  protected AbstractRandomizer() {
    random = new RandomEx();
  }

  /**
   * 构造一个 {@link AbstractRandomizer}。
   *
   * @param seed
   *     随机种子。
   */
  protected AbstractRandomizer(final long seed) {
    random = new RandomEx(seed);
  }

  /**
   * 获取预定义的值。
   *
   * @param key
   *     键。
   * @return
   *     预定义的值。
   */
  protected String[] getPredefinedValuesOf(final String key) {
    return getBundle("common-random-data").getString(key).split(",");
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
