////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的 {@link Boolean}.
 *
 * @author 胡海星
 */
public class BooleanRandomizer extends AbstractRandomizer<Boolean> {

  /**
   * 创建一个新的 {@link BooleanRandomizer}.
   */
  public BooleanRandomizer() {
  }

  /**
   * 创建一个新的 {@link BooleanRandomizer}.
   *
   * @param seed
   *         初始种子
   */
  public BooleanRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Boolean getRandomValue() {
    return random.nextBoolean();
  }
}
