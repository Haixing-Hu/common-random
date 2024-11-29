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
 * Generate a random {@link UUID}.
 *
 * @author Pascal Schumacher
 */
public class UuidRandomizer extends AbstractRandomizer<UUID> {

  /**
   * Create a new {@link UuidRandomizer}.
   */
  public UuidRandomizer() {
  }

  /**
   * Create a new {@link UuidRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public UuidRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public UUID getRandomValue() {
    return new UUID(random.nextLong(), random.nextLong());
  }
}
