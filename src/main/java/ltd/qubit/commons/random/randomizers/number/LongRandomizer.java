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
 * 生成一个随机的{@link Long}。
 *
 * @author 胡海星
 */
public class LongRandomizer extends AbstractRandomizer<Long> {

  /**
   * 创建一个新的{@link LongRandomizer}。
   */
  public LongRandomizer() {
  }

  /**
   * 创建一个新的{@link LongRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public LongRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的{@link Long}。
   *
   * @return 一个随机的{@link Long}
   */
  @Override
  public Long getRandomValue() {
    return random.nextLong();
  }
}
