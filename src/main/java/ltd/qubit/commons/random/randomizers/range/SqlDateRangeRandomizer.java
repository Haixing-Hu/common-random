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
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.sql.Date.valueOf;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.DateUtils.truncateInPlace;
import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * 在给定范围内生成一个随机的{@link java.sql.Date}。
 *
 * @author 胡海星
 */
public class SqlDateRangeRandomizer extends AbstractRangeRandomizer<Date> {

  private TimeUnit precision = Precision.DEFAULT_VALUE;

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   */
  public SqlDateRangeRandomizer(final Date min, final Date max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param precision
   *     生成日期的精度。
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision) {
    super(min, max);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param seed
   *     初始种子
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final long seed) {
    super(min, max, seed);
  }

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param min
   *     最小值
   * @param max
   *     最大值
   * @param precision
   *     生成日期的精度。
   * @param seed
   *     初始种子
   */
  public SqlDateRangeRandomizer(final Date min, final Date max,
      final TimeUnit precision, final long seed) {
    super(min, max, seed);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param parameters
   *     随机生成器参数。
   */
  public SqlDateRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  /**
   * 创建一个新的{@link SqlDateRangeRandomizer}。
   *
   * @param parameters
   *     随机生成器参数。
   * @param precision
   *     生成日期的精度。
   */
  public SqlDateRangeRandomizer(final Parameters parameters,
      final TimeUnit precision) {
    super(parameters.getSeed());
    setParameters(parameters);
    this.precision = requireNonNull("precision", precision);
  }

  @Override
  protected void checkValues() {
    final Date min = requireNonNull("range.min", range.getMin());
    final Date max = requireNonNull("range.max", range.getMax());
    if (min.after(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected Date getDefaultMinValue() {
    return new Date(Long.MIN_VALUE);
  }

  @Override
  protected Date getDefaultMaxValue() {
    return new Date(Long.MAX_VALUE);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final java.sql.Date minDate = valueOf(localDateRange.getMin());
    final java.sql.Date maxDate = valueOf(localDateRange.getMax());
    setRange(minDate, maxDate);
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
  public Date getRandomValue() {
    final long minDateTime = range.getMin().getTime();
    final long maxDateTime = range.getMax().getTime();
    final long millis = random.nextLong(new CloseRange<>(minDateTime, maxDateTime));
    final Date result = new Date(millis);
    return truncateInPlace(result, precision);
  }

}
