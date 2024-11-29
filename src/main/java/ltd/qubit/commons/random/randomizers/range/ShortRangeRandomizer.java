////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;

/**
 * Generate a random {@link Short} in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class ShortRangeRandomizer extends AbstractRangeRandomizer<Short> {

  /**
   * Create a new {@link ShortRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public ShortRangeRandomizer(final Short min, final Short max) {
    super(min, max);
  }

  /**
   * Create a new {@link ShortRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public ShortRangeRandomizer(final Short min, final Short max,
          final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    range.check();
  }

  @Override
  public Short getRandomValue() {
    return random.nextShort(range);
  }

  @Override
  protected Short getDefaultMaxValue() {
    return Short.MAX_VALUE;
  }

  @Override
  protected Short getDefaultMinValue() {
    return Short.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }
}
