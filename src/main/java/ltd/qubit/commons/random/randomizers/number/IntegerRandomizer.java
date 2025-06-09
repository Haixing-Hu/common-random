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
 * 生成一个随机的{@link Integer}。
 *
 * @author 胡海星
 */
public class IntegerRandomizer extends AbstractRandomizer<Integer> {

  /**
   * 创建一个新的{@link IntegerRandomizer}。
   */
  public IntegerRandomizer() {
  }

  /**
   * 创建一个新的{@link IntegerRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public IntegerRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的 {@link Integer}。
   *
   * @return 一个随机的 {@link Integer}
   */
  @Override
  public Integer getRandomValue() {
    return random.nextInt();
  }
}
