////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Timestamp;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link Timestamp}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class SqlTimestampRandomizer implements Randomizer<Timestamp> {

  private final DateRandomizer delegate;

  /**
   * 创建一个新的{@link SqlTimestampRandomizer}。
   */
  public SqlTimestampRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * 创建一个新的{@link SqlTimestampRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public SqlTimestampRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  /**
   * 生成一个随机的SQL时间戳。
   *
   * @return 一个随机的{@link Timestamp}
   */
  @Override
  public Timestamp getRandomValue() {
    return new Timestamp(delegate.getRandomValue().getTime());
  }
}
