////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.TimeZone;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成随机{@link TimeZone}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class TimeZoneRandomizer extends AbstractRandomizer<TimeZone> {

  /**
   * 创建一个新的{@link TimeZoneRandomizer}。
   */
  public TimeZoneRandomizer() {
  }

  /**
   * 创建一个新的{@link TimeZoneRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public TimeZoneRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的时区。
   *
   * @return 一个随机的{@link TimeZone}
   */
  @Override
  public TimeZone getRandomValue() {
    final String[] timeZoneIds = TimeZone.getAvailableIDs();
    return TimeZone.getTimeZone(timeZoneIds[random.nextInt(timeZoneIds.length)]);
  }
}
