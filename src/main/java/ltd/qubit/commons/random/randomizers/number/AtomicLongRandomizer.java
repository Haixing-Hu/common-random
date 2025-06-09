////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicLong;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成一个随机的{@link AtomicLong}。
 *
 * @author 胡海星
 */
public class AtomicLongRandomizer implements Randomizer<AtomicLong> {

  private final LongRandomizer delegate;

  /**
   * 创建一个新的{@link AtomicLongRandomizer}。
   */
  public AtomicLongRandomizer() {
    delegate = new LongRandomizer();
  }

  /**
   * 创建一个新的{@link AtomicLongRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public AtomicLongRandomizer(final long seed) {
    delegate = new LongRandomizer(seed);
  }

  /**
   * 生成一个随机的 {@link AtomicLong}。
   *
   * @return 一个随机的 {@link AtomicLong}
   */
  @Override
  public AtomicLong getRandomValue() {
    return new AtomicLong(delegate.getRandomValue());
  }
}
