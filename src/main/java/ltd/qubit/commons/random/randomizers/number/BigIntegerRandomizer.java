////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigInteger;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link BigInteger}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class BigIntegerRandomizer extends AbstractRandomizer<BigInteger> {

  private static final int NUM_BITS = 128;

  /**
   * Create a new {@link BigIntegerRandomizer}.
   */
  public BigIntegerRandomizer() {
    super();
  }

  /**
   * Create a new {@link BigIntegerRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public BigIntegerRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public BigInteger getRandomValue() {
    return new BigInteger(NUM_BITS, random);
  }
}
