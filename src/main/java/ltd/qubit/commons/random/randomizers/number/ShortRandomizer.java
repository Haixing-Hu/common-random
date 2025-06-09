////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的{@link Short}。
 *
 * @author 胡海星
 */
public class ShortRandomizer extends AbstractRandomizer<Short> {

  /**
   * 创建一个新的{@link ShortRandomizer}。
   */
  public ShortRandomizer() {
  }

  /**
   * 创建一个新的{@link ShortRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ShortRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的 {@link Short}。
   *
   * @return 一个随机的 {@link Short}
   */
  @Override
  public Short getRandomValue() {
    return (short) random.nextInt();
  }
}
