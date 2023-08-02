////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.TimeZone;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link TimeZone}.
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
public class TimeZoneRandomizer extends AbstractRandomizer<TimeZone> {

  /**
   * Create a new {@link TimeZoneRandomizer}.
   */
  public TimeZoneRandomizer() {
  }

  /**
   * Create a new {@link TimeZoneRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public TimeZoneRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public TimeZone getRandomValue() {
    final String[] timeZoneIds = TimeZone.getAvailableIDs();
    return TimeZone.getTimeZone(timeZoneIds[random.nextInt(timeZoneIds.length)]);
  }
}
