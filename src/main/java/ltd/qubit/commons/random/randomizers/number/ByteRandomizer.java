////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成一个随机的{@link Byte}。
 *
 * @author 胡海星
 */
public class ByteRandomizer implements Randomizer<Byte> {

  private final IntegerRandomizer delegate;

  /**
   * 创建一个新的{@link ByteRandomizer}。
   */
  public ByteRandomizer() {
    delegate = new IntegerRandomizer();
  }

  /**
   * 创建一个新的{@link ByteRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ByteRandomizer(final long seed) {
    delegate = new IntegerRandomizer(seed);
  }

  /**
   * 生成一个随机的{@link Byte}。
   *
   * @return 一个随机的{@link Byte}
   */
  @Override
  public Byte getRandomValue() {
    return delegate.getRandomValue().byteValue();
  }
}
