////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Time;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link Time}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class SqlTimeRandomizer implements Randomizer<Time> {

  private final DateRandomizer delegate;

  /**
   * 创建一个新的{@link SqlTimeRandomizer}。
   */
  public SqlTimeRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * 创建一个新的{@link SqlTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public SqlTimeRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  /**
   * 生成一个随机的SQL时间。
   *
   * @return 一个随机的{@link Time}
   */
  @Override
  public Time getRandomValue() {
    return new Time(delegate.getRandomValue().getTime());
  }
}
