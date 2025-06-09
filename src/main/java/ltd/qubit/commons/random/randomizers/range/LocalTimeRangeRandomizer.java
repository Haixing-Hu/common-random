////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link LocalTime}。
 *
 * @author 胡海星
 */
public class LocalTimeRangeRandomizer extends
    AbstractRangeRandomizer<LocalTime> {

  /**
   * 创建一个新的{@link LocalTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public LocalTimeRangeRandomizer(final LocalTime min, final LocalTime max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link LocalTimeRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public LocalTimeRangeRandomizer(final LocalTime min, final LocalTime max,
          final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link LocalTimeRangeRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public LocalTimeRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  @Override
  protected void checkValues() {
    final LocalTime min = requireNonNull(range.getMin(), "range.min cannot be null");
    final LocalTime max = requireNonNull(range.getMax(), "range.max cannot be null");
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
