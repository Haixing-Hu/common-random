////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link Byte}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ByteRandomizer implements Randomizer<Byte> {

  private final IntegerRandomizer delegate;

  /**
   * Create a new {@link ByteRandomizer}.
   */
  public ByteRandomizer() {
    delegate = new IntegerRandomizer();
  }

  /**
   * Create a new {@link ByteRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public ByteRandomizer(final long seed) {
    delegate = new IntegerRandomizer(seed);
  }

  @Override
  public Byte getRandomValue() {
    return delegate.getRandomValue().byteValue();
  }
}
