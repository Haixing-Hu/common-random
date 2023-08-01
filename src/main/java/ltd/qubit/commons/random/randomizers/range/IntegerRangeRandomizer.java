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
import ltd.qubit.commons.util.range.CloseRange;

/**
 * Generate a random {@link Integer} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class IntegerRangeRandomizer extends AbstractRangeRandomizer<Integer> {

  /**
   * Create a new {@link IntegerRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public IntegerRangeRandomizer(final Integer min, final Integer max) {
    super(min, max);
  }

  /**
   * Create a new {@link IntegerRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public IntegerRangeRandomizer(final Integer min, final Integer max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * Create a new {@link IntegerRangeRandomizer}.
   *
   * @param range
   *         the range.
   */
  public IntegerRangeRandomizer(final CloseRange<Integer> range) {
    super(range);
  }

  /**
   * Create a new {@link IntegerRangeRandomizer}.
   *
   * @param range
   *         the range.
   * @param seed
   *         initial seed
   */
  public IntegerRangeRandomizer(final CloseRange<Integer> range, final long seed) {
    super(range, seed);
  }

  @Override
  protected void checkValues() {
    final Integer min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Integer max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  public Integer getRandomValue() {
    return random.nextInt(range);
  }

  @Override
  protected Integer getDefaultMaxValue() {
    return Integer.MAX_VALUE;
  }

  @Override
  protected Integer getDefaultMinValue() {
    return Integer.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
