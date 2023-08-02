////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalTime;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random {@link LocalTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalTimeRandomizer implements Randomizer<LocalTime> {

  private final HourRandomizer hourRandomizer;
  private final MinuteRandomizer minuteRandomizer;

  /**
   * Create a new {@link LocalTimeRandomizer}.
   */
  public LocalTimeRandomizer() {
    hourRandomizer = new HourRandomizer();
    minuteRandomizer = new MinuteRandomizer();
  }

  /**
   * Create a new {@link LocalTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public LocalTimeRandomizer(final long seed) {
    hourRandomizer = new HourRandomizer(seed);
    minuteRandomizer = new MinuteRandomizer(seed);
  }

  @Override
  public LocalTime getRandomValue() {
    final int randomHour = hourRandomizer.getRandomValue();
    final int randomMinute = minuteRandomizer.getRandomValue();
    final int randomSecond = minuteRandomizer.getRandomValue(); // seconds are also between 0 and 59
    return LocalTime.of(randomHour, randomMinute, randomSecond);
  }
}
