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

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 生成随机{@link LocalTime}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class LocalTimeRandomizer implements Randomizer<LocalTime> {

  private final HourRandomizer hourRandomizer;
  private final MinuteRandomizer minuteRandomizer;

  /**
   * 创建一个新的{@link LocalTimeRandomizer}。
   */
  public LocalTimeRandomizer() {
    hourRandomizer = new HourRandomizer();
    minuteRandomizer = new MinuteRandomizer();
  }

  /**
   * 创建一个新的{@link LocalTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public LocalTimeRandomizer(final long seed) {
    hourRandomizer = new HourRandomizer(seed);
    minuteRandomizer = new MinuteRandomizer(seed);
  }

  /**
   * 生成一个随机的本地时间。
   *
   * @return 一个随机的{@link LocalTime}
   */
  @Override
  public LocalTime getRandomValue() {
    final int randomHour = hourRandomizer.getRandomValue();
    final int randomMinute = minuteRandomizer.getRandomValue();
    final int randomSecond = minuteRandomizer.getRandomValue(); // seconds are also between 0 and 59
    return LocalTime.of(randomHour, randomMinute, randomSecond);
  }
}
