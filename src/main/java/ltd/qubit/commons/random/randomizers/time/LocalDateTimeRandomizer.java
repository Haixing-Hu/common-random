////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link LocalDateTime}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class LocalDateTimeRandomizer implements Randomizer<LocalDateTime> {

  private LocalDateRandomizer localDateRandomizer;
  private LocalTimeRandomizer localTimeRandomizer;

  /**
   * 创建一个新的{@link LocalDateTimeRandomizer}。
   */
  public LocalDateTimeRandomizer() {
    localDateRandomizer = new LocalDateRandomizer();
    localTimeRandomizer = new LocalTimeRandomizer();
  }

  /**
   * 创建一个新的{@link LocalDateTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public LocalDateTimeRandomizer(final long seed) {
    localDateRandomizer = new LocalDateRandomizer(seed);
    localTimeRandomizer = new LocalTimeRandomizer(seed);
  }

  /**
   * 生成一个随机的本地日期时间。
   *
   * @return 一个随机的{@link LocalDateTime}
   */
  @Override
  public LocalDateTime getRandomValue() {
    final LocalDate localDate = localDateRandomizer.getRandomValue();
    final LocalTime localTime = localTimeRandomizer.getRandomValue();
    return LocalDateTime.of(localDate, localTime);
  }

  /**
   * 设置{@link LocalDateRandomizer}。
   *
   * @param localDateRandomizer
   *         要设置的{@link LocalDateRandomizer}
   */
  public void setLocalDateRandomizer(final LocalDateRandomizer localDateRandomizer) {
    this.localDateRandomizer = localDateRandomizer;
  }

  /**
   * 设置{@link LocalTimeRandomizer}。
   *
   * @param localTimeRandomizer
   *         要设置的{@link LocalTimeRandomizer}
   */
  public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
    this.localTimeRandomizer = localTimeRandomizer;
  }
}
