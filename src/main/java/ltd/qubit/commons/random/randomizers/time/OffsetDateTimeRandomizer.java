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
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link OffsetDateTime}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class OffsetDateTimeRandomizer implements Randomizer<OffsetDateTime> {

  private LocalDateRandomizer localDateRandomizer;
  private LocalTimeRandomizer localTimeRandomizer;
  private ZoneOffsetRandomizer zoneOffsetRandomizer;

  /**
   * 创建一个新的{@link OffsetDateTimeRandomizer}。
   */
  public OffsetDateTimeRandomizer() {
    localDateRandomizer = new LocalDateRandomizer();
    localTimeRandomizer = new LocalTimeRandomizer();
    zoneOffsetRandomizer = new ZoneOffsetRandomizer();
  }

  /**
   * 创建一个新的{@link OffsetDateTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public OffsetDateTimeRandomizer(final long seed) {
    localDateRandomizer = new LocalDateRandomizer(seed);
    localTimeRandomizer = new LocalTimeRandomizer(seed);
    zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
  }

  /**
   * 生成一个随机的带偏移量的日期时间。
   *
   * @return 一个随机的{@link OffsetDateTime}
   */
  @Override
  public OffsetDateTime getRandomValue() {
    final LocalDate randomLocalDate = localDateRandomizer.getRandomValue();
    final LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
    final ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
    return OffsetDateTime.of(randomLocalDate, randomLocalTime, randomZoneOffset);
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

  /**
   * 设置{@link ZoneOffsetRandomizer}。
   *
   * @param zoneOffsetRandomizer
   *         要设置的{@link ZoneOffsetRandomizer}
   */
  public void setZoneOffsetRandomizer(final ZoneOffsetRandomizer zoneOffsetRandomizer) {
    this.zoneOffsetRandomizer = zoneOffsetRandomizer;
  }
}
