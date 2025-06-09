////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.InstantRangeRandomizer;

import static ltd.qubit.commons.random.Parameters.DEFAULT_DATES_RANGE;

/**
 * 生成随机{@link Instant}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class InstantRandomizer implements Randomizer<Instant>,
    ContextAwareRandomizer<Instant> {

  private final InstantRangeRandomizer delegate;

  /**
   * 创建一个新的{@link InstantRandomizer}。
   */
  public InstantRandomizer() {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max);
  }

  /**
   * 创建一个新的{@link InstantRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public InstantRandomizer(final long seed) {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max, seed);
  }

  /**
   * 创建一个新的{@link InstantRandomizer}。
   *
   * @param precision
   *         时间精度
   * @param seed
   *         初始种子
   */
  public InstantRandomizer(final TimeUnit precision, final long seed) {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max, precision, seed);
  }

  /**
   * 创建一个新的{@link InstantRandomizer}。
   *
   * @param min
   *         最小时间
   * @param max
   *         最大时间
   */
  public InstantRandomizer(final Instant min, final Instant max) {
    delegate = new InstantRangeRandomizer(min, max);
  }

  /**
   * 创建一个新的{@link InstantRandomizer}。
   *
   * @param min
   *         最小时间
   * @param max
   *         最大时间
   * @param seed
   *         初始种子
   */
  public InstantRandomizer(final Instant min, final Instant max, final long seed) {
    delegate = new InstantRangeRandomizer(min, max, seed);
  }

  /**
   * 创建一个新的{@link InstantRandomizer}。
   *
   * @param min
   *         最小时间
   * @param max
   *         最大时间
   * @param precision
   *         时间精度
   * @param seed
   *         初始种子
   */
  public InstantRandomizer(final Instant min, final Instant max,
      final TimeUnit precision, final long seed) {
    delegate = new InstantRangeRandomizer(min, max, precision, seed);
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
   * 生成一个随机的时间瞬间。
   *
   * @return 一个随机的{@link Instant}
   */
  @Override
  public Instant getRandomValue() {
    return delegate.getRandomValue();
  }
}
