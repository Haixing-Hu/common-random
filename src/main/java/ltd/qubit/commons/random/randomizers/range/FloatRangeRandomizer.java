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
 * Generate a random {@link Float} in the given range.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class FloatRangeRandomizer extends AbstractRangeRandomizer<Float> {

  /**
   * Create a new {@link FloatRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public FloatRangeRandomizer(final Float min, final Float max) {
    super(min, max);
  }

  /**
   * Create a new {@link FloatRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public FloatRangeRandomizer(final Float min, final Float max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Float min = requireNonNull("range.min", range.getMin());
    final Float max = requireNonNull("range.max", range.getMax());
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Float getDefaultMinValue() {
    return Float.MIN_VALUE;
  }

  @Override
  protected Float getDefaultMaxValue() {
    return Float.MAX_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Float getRandomValue() {
    return random.nextFloat(range);
  }
}
