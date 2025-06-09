////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.math.BigInteger;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;

/**
 * 在给定范围内生成一个随机的{@link BigInteger}。
 *
 * @author Rémi Alvergnat, 胡海星
 */
public class BigIntegerRangeRandomizer extends AbstractContextAwareRandomizer<BigInteger> {

  private final IntegerRangeRandomizer delegate;

  /**
   * 创建一个新的{@link BigIntegerRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public BigIntegerRangeRandomizer(final Integer min, final Integer max) {
    delegate = new IntegerRangeRandomizer(min, max);
  }

  /**
   * 创建一个新的{@link BigIntegerRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public BigIntegerRangeRandomizer(final Integer min, final Integer max,
          final long seed) {
    delegate = new IntegerRangeRandomizer(min, max, seed);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    delegate.setParameters(parameters);
  }

  @Override
  public BigInteger getRandomValue() {
    return new BigInteger(String.valueOf(delegate.getRandomValue()));
  }
}
