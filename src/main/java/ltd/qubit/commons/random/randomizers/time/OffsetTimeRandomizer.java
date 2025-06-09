////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link OffsetTime}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class OffsetTimeRandomizer implements Randomizer<OffsetTime> {

  private LocalTimeRandomizer localTimeRandomizer;
  private ZoneOffsetRandomizer zoneOffsetRandomizer;

  /**
   * 创建一个新的{@link OffsetTimeRandomizer}。
   */
  public OffsetTimeRandomizer() {
    localTimeRandomizer = new LocalTimeRandomizer();
    zoneOffsetRandomizer = new ZoneOffsetRandomizer();
  }

  /**
   * 创建一个新的{@link OffsetTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public OffsetTimeRandomizer(final long seed) {
    localTimeRandomizer = new LocalTimeRandomizer(seed);
    zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
  }

  /**
   * 生成一个随机的带偏移量的时间。
   *
   * @return 一个随机的{@link OffsetTime}
   */
  @Override
  public OffsetTime getRandomValue() {
    final LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
    final ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
    return OffsetTime.of(randomLocalTime, randomZoneOffset);
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
