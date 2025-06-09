////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个生成常量值的 {@link Randomizer}。是的... 这不是随机的。
 *
 * @author 胡海星
 */
public class ConstantRandomizer<T> implements Randomizer<T> {

  private final T value;

  /**
   * 创建一个新的 {@link ConstantRandomizer}。
   *
   * @param value
   *         常量值
   */
  public ConstantRandomizer(final T value) {
    this.value = value;
  }

  @Override
  public T getRandomValue() {
    return value;
  }
}
