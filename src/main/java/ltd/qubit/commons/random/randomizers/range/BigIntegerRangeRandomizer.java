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
 * Generate a random {@link BigInteger} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class BigIntegerRangeRandomizer extends AbstractContextAwareRandomizer<BigInteger> {

  private final IntegerRangeRandomizer delegate;

  /**
   * Create a new {@link BigIntegerRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public BigIntegerRangeRandomizer(final Integer min, final Integer max) {
    delegate = new IntegerRangeRandomizer(min, max);
  }

  /**
   * Create a new {@link BigIntegerRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
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
