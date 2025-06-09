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
 * 生成一个随机的{@link Float}。
 *
 * @author 胡海星
 */
public class FloatRandomizer extends AbstractRandomizer<Float> {

  /**
   * 创建一个新的{@link FloatRandomizer}。
   */
  public FloatRandomizer() {
  }

  /**
   * 创建一个新的{@link FloatRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public FloatRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的{@link Float}。
   *
   * @return 一个随机的{@link Float}
   */
  @Override
  public Float getRandomValue() {
    return random.nextFloat();
  }
}
