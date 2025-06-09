////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigInteger;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的{@link BigInteger}。
 *
 * @author 胡海星
 */
public class BigIntegerRandomizer extends AbstractRandomizer<BigInteger> {

  private static final int NUM_BITS = 128;

  /**
   * 创建一个新的{@link BigIntegerRandomizer}。
   */
  public BigIntegerRandomizer() {
    super();
  }

  /**
   * 创建一个新的{@link BigIntegerRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public BigIntegerRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的{@link BigInteger}。
   *
   * @return 一个随机的{@link BigInteger}
   */
  @Override
  public BigInteger getRandomValue() {
    return new BigInteger(NUM_BITS, random);
  }
}
