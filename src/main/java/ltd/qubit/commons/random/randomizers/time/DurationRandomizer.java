////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * 生成随机{@link Duration}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class DurationRandomizer implements Randomizer<Duration> {

  private static final int MIN_AMOUNT = 0;
  private static final int MAX_AMOUNT = 100;

  private final IntegerRangeRandomizer amountRandomizer;
  private final TemporalUnit unit;

  /**
   * 创建一个新的{@link DurationRandomizer}。生成的{@link Duration}对象
   * 将使用{@link ChronoUnit#HOURS}。
   */
  public DurationRandomizer() {
    this(ChronoUnit.HOURS);
  }

  /**
   * 创建一个新的{@link DurationRandomizer}。
   *
   * @param unit
   *         创建时长的时间单位
   */
  public DurationRandomizer(final TemporalUnit unit) {
    this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT), unit);
  }

  /**
   * 创建一个新的{@link DurationRandomizer}。生成的{@link Duration}对象
   * 将使用{@link ChronoUnit#HOURS}。
   *
   * @param seed
   *         初始种子
   */
  public DurationRandomizer(final long seed) {
    this(seed, ChronoUnit.HOURS);
  }

  /**
   * 创建一个新的{@link DurationRandomizer}。
   *
   * @param seed
   *         初始种子
   * @param unit
   *         创建时长的时间单位
   */
  public DurationRandomizer(final long seed, final TemporalUnit unit) {
    this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT, seed), unit);
  }

  private DurationRandomizer(final IntegerRangeRandomizer amountRandomizer,
          final TemporalUnit unit) {
    this.amountRandomizer = amountRandomizer;
    this.unit = requireValid(unit);
  }

  /**
   * 生成一个随机的时间长度。
   *
   * @return 一个随机的{@link Duration}
   */
  @Override
  public Duration getRandomValue() {
    final int randomAmount = amountRandomizer.getRandomValue();
    return Duration.of(randomAmount, unit);
  }

  /**
   * 验证时间单位是否有效。
   *
   * @param unit
   *         要验证的时间单位
   * @return 有效的时间单位
   * @throws IllegalArgumentException
   *         如果时间单位无效
   */
  private static TemporalUnit requireValid(final TemporalUnit unit) {
    if (unit.isDurationEstimated() && unit != ChronoUnit.DAYS) {
      throw new IllegalArgumentException("Temporal unit " + unit
          + " can't be used to create Duration objects");
    }
    return unit;
  }
}
