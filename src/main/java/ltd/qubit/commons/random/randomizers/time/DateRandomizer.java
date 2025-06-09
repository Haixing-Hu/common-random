////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.DateRangeRandomizer;

import static ltd.qubit.commons.random.Parameters.DEFAULT_DATES_RANGE;

/**
 * 生成随机{@link Date}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class DateRandomizer implements Randomizer<Date>,
    ContextAwareRandomizer<Date> {

  private final DateRangeRandomizer delegate;

  /**
   * 创建一个新的{@link DateRandomizer}。
   */
  public DateRandomizer() {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max);
  }

  /**
   * 创建一个新的{@link DateRandomizer}。
   *
   * @param precision
   *     生成日期时间的精度
   */
  public DateRandomizer(final TimeUnit precision) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, precision);
  }

  /**
   * 创建一个新的{@link DateRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public DateRandomizer(final long seed) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, seed);
  }

  /**
   * 创建一个新的{@link DateRandomizer}。
   *
   * @param precision
   *     生成日期时间的精度
   * @param seed
   *         初始种子
   */
  public DateRandomizer(final TimeUnit precision, final long seed) {
    final Date min = Date.from(DEFAULT_DATES_RANGE.getMin().toInstant());
    final Date max = Date.from(DEFAULT_DATES_RANGE.getMax().toInstant());
    delegate = new DateRangeRandomizer(min, max, precision, seed);
  }

  /**
   * 设置上下文。
   *
   * @param context
   *         上下文对象
   */
  @Override
  public void setContext(final Context context) {
    delegate.setContext(context);
  }

  /**
   * 生成一个随机的日期。
   *
   * @return 一个随机的{@link Date}
   */
  @Override
  public Date getRandomValue() {
    return delegate.getRandomValue();
  }
}
