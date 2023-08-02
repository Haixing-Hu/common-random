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
 * Generate a random {@link Long} in the given range.
 *
 * @author Rémi Alvergna, Haixing Hu
 */
public class LongRangeRandomizer extends AbstractRangeRandomizer<Long> {

  /**
   * Create a new {@link LongRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public LongRangeRandomizer(final Long min, final Long max) {
    super(min, max);
  }

  /**
   * Create a new {@link LongRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public LongRangeRandomizer(final Long min, final Long max, final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Long min = requireNonNull("range.min", range.getMin());
    final Long max = requireNonNull("range.max", range.getMax());
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  public Long getRandomValue() {
    return random.nextLong(range);
  }

  @Override
  protected Long getDefaultMaxValue() {
    return Long.MAX_VALUE;
  }

  @Override
  protected Long getDefaultMinValue() {
    return Long.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
