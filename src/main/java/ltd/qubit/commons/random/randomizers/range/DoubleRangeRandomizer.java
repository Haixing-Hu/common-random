////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;

/**
 * Generate a random {@link Double} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DoubleRangeRandomizer extends AbstractRangeRandomizer<Double> {

  /**
   * Create a new {@link DoubleRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public DoubleRangeRandomizer(final Double min, final Double max) {
    super(min, max);
  }

  /**
   * Create a new {@link DoubleRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public DoubleRangeRandomizer(final Double min, final Double max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Double min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Double max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Double getDefaultMinValue() {
    return Double.MIN_VALUE;
  }

  @Override
  protected Double getDefaultMaxValue() {
    return Double.MAX_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Double getRandomValue() {
    return random.nextDouble(range);
  }
}
