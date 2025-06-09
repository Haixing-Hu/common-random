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
 * 生成一个随机的{@link Double}。
 *
 * @author 胡海星
 */
public class DoubleRandomizer extends AbstractRandomizer<Double> {

  /**
   * 创建一个新的{@link DoubleRandomizer}。
   */
  public DoubleRandomizer() {
  }

  /**
   * 创建一个新的{@link DoubleRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public DoubleRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的{@link Double}。
   *
   * @return 一个随机的{@link Double}
   */
  @Override
  public Double getRandomValue() {
    return random.nextDouble();
  }
}
