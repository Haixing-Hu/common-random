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
 * 生成一个随机的{@link Number}。
 *
 * @author 胡海星
 */
public class NumberRandomizer extends AbstractRandomizer<Number> {

  /**
   * 创建一个新的{@link NumberRandomizer}。
   */
  public NumberRandomizer() {
  }

  /**
   * 创建一个新的{@link NumberRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public NumberRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的{@link Integer}作为{@link Number}。
   *
   * @return 一个随机的{@link Integer}
   */
  @Override
  public Integer getRandomValue() {
    return random.nextInt();
  }
}
