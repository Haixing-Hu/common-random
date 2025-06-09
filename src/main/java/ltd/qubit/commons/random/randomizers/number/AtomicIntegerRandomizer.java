////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicInteger;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成一个随机的{@link AtomicInteger}。
 *
 * @author 胡海星
 */
public class AtomicIntegerRandomizer implements Randomizer<AtomicInteger> {

  private final IntegerRandomizer delegate;

  /**
   * 创建一个新的{@link AtomicIntegerRandomizer}。
   */
  public AtomicIntegerRandomizer() {
    delegate = new IntegerRandomizer();
  }

  /**
   * 创建一个新的{@link AtomicIntegerRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public AtomicIntegerRandomizer(final long seed) {
    delegate = new IntegerRandomizer(seed);
  }

  /**
   * 生成一个随机的{@link AtomicInteger}。
   *
   * @return 一个随机的{@link AtomicInteger}
   */
  @Override
  public AtomicInteger getRandomValue() {
    return new AtomicInteger(delegate.getRandomValue());
  }
}
