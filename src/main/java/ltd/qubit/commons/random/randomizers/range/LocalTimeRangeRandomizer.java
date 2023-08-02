////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * Generate a random {@link LocalTime} in the given range.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class LocalTimeRangeRandomizer extends
    AbstractRangeRandomizer<LocalTime> {

  /**
   * Create a new {@link LocalTimeRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   */
  public LocalTimeRangeRandomizer(final LocalTime min, final LocalTime max) {
    super(min, max);
  }

  /**
   * Create a new {@link LocalTimeRangeRandomizer}.
   *
   * @param min
   *         min value
   * @param max
   *         max value
   * @param seed
   *         initial seed
   */
  public LocalTimeRangeRandomizer(final LocalTime min, final LocalTime max,
          final long seed) {
    super(min, max, seed);
  }

  public LocalTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final LocalTime min = requireNonNull("range.min", range.getMin());
    final LocalTime max = requireNonNull("range.max", range.getMax());
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected LocalTime getDefaultMinValue() {
    return LocalTime.MIN;
  }

  @Override
  protected LocalTime getDefaultMaxValue() {
    return LocalTime.MAX;
  }

  @Override
  public LocalTime getRandomValue() {
    return random.nextLocalTime(range);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalTime> localTimeRange = parameters.getTimeRange();
    final LocalTime minTime = localTimeRange.getMin();
    final LocalTime maxTime = localTimeRange.getMax();
    setRange(minTime, maxTime);
  }

}
