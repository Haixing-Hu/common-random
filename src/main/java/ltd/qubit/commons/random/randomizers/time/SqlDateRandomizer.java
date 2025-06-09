////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link Date}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class SqlDateRandomizer implements Randomizer<Date> {

  private final DateRandomizer delegate;

  /**
   * 创建一个新的{@link SqlDateRandomizer}。
   */
  public SqlDateRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * 创建一个新的{@link SqlDateRandomizer}。
   *
   * @param precision
   *     生成日期时间的精度。
   */
  public SqlDateRandomizer(final TimeUnit precision) {
    delegate = new DateRandomizer(precision);
  }

  /**
   * 创建一个新的{@link SqlDateRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public SqlDateRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  /**
   * 创建一个新的{@link SqlDateRandomizer}。
   *
   * @param precision
   *     生成日期时间的精度。
   * @param seed
   *         初始种子
   */
  public SqlDateRandomizer(final TimeUnit precision, final long seed) {
    delegate = new DateRandomizer(precision, seed);
  }

  /**
   * 生成一个随机的SQL日期。
   *
   * @return 一个随机的{@link Date}
   */
  @Override
  public Date getRandomValue() {
    return new Date(delegate.getRandomValue().getTime());
  }

}
