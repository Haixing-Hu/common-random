////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.DateUtils.toChronoUnit;
import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * 在给定范围内生成一个随机的{@link Instant}。
 *
 * @author 胡海星
 */
public class InstantRangeRandomizer extends AbstractRangeRandomizer<Instant> {

  private TimeUnit precision = TimeUnit.MILLISECONDS;

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   */
  public InstantRangeRandomizer(final Instant min, final Instant max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param precision
   *     生成值的精度。
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final TimeUnit precision) {
    super(min, max);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param seed
   *     初始种子
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param precision
   *     生成值的精度。
   * @param seed
   *     初始种子
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final TimeUnit precision, final long seed) {
    super(min, max, seed);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param parameters
   *     随机化参数。
   */
  public InstantRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  /**
   * 创建一个新的{@link InstantRangeRandomizer}。
   *
   * @param parameters
   *     随机化参数。
   * @param precision
   *     生成值的精度。
   */
  public InstantRangeRandomizer(final Parameters parameters,
      final TimeUnit precision) {
    super(parameters.getSeed());
    setParameters(parameters);
    this.precision = requireNonNull("precision", precision);
  }

  @Override
  protected void checkValues() {
    final Instant min = requireNonNull("min cannot be null.", range.getMin());
    final Instant max = requireNonNull("max cannot be null", range.getMax());
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected Instant getDefaultMinValue() {
    return Instant.ofEpochMilli(Long.MIN_VALUE);
  }

  @Override
  protected Instant getDefaultMaxValue() {
    return Instant.ofEpochMilli(Long.MAX_VALUE);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final Instant min = minDate.atStartOfDay(DateUtils.UTC_ZONE_ID).toInstant();
    final Instant max = maxDate.atStartOfDay(DateUtils.UTC_ZONE_ID).toInstant();
    setRange(min, max);
  }

  @Override
  public void setContext(final Context context) {
    super.setContext(context);
    final Field field = context.getCurrentField();
    if (field != null) {
      final Precision precisionAnn = getAnnotation(field, Precision.class);
      if (precisionAnn != null) {
        precision = precisionAnn.value();
      }
    }
  }

  @Override
  public Instant getRandomValue() {
    final Instant result = random.nextInstant(range);
    return result.truncatedTo(toChronoUnit(precision));
  }
}
