////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Generate a random {@link Byte} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class ByteRangeRandomizer extends AbstractRangeRandomizer<Byte> {

  /**
   * Create a new {@link ByteRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public ByteRangeRandomizer(final Byte min, final Byte max) {
    super(min, max);
  }

  /**
   * Create a new {@link ByteRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public ByteRangeRandomizer(final Byte min, final Byte max, final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Byte min = requireNonNull("range.min", range.getMin());
    final Byte max = requireNonNull("range.max", range.getMax());
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Byte getDefaultMaxValue() {
    return Byte.MAX_VALUE;
  }

  @Override
  protected Byte getDefaultMinValue() {
    return Byte.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Byte getRandomValue() {
    return random.nextByte(range);
  }
}
