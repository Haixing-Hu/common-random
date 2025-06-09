////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.util.UUID;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的 {@link UUID}。
 *
 * @author 胡海星
 */
public class UuidRandomizer extends AbstractRandomizer<UUID> {

  /**
   * 创建一个新的 {@link UuidRandomizer}。
   */
  public UuidRandomizer() {
  }

  /**
   * 创建一个新的 {@link UuidRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public UuidRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public UUID getRandomValue() {
    return new UUID(random.nextLong(), random.nextLong());
  }
}
