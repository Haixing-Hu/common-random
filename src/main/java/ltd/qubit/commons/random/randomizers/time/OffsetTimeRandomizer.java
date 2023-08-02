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
import java.time.OffsetTime;
import java.time.ZoneOffset;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random {@link OffsetTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class OffsetTimeRandomizer implements Randomizer<OffsetTime> {

  private LocalTimeRandomizer localTimeRandomizer;
  private ZoneOffsetRandomizer zoneOffsetRandomizer;

  /**
   * Create a new {@link OffsetTimeRandomizer}.
   */
  public OffsetTimeRandomizer() {
    localTimeRandomizer = new LocalTimeRandomizer();
    zoneOffsetRandomizer = new ZoneOffsetRandomizer();
  }

  /**
   * Create a new {@link OffsetTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public OffsetTimeRandomizer(final long seed) {
    localTimeRandomizer = new LocalTimeRandomizer(seed);
    zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
  }

  @Override
  public OffsetTime getRandomValue() {
    final LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
    final ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
    return OffsetTime.of(randomLocalTime, randomZoneOffset);
  }

  public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
    this.localTimeRandomizer = localTimeRandomizer;
  }

  public void setZoneOffsetRandomizer(final ZoneOffsetRandomizer zoneOffsetRandomizer) {
    this.zoneOffsetRandomizer = zoneOffsetRandomizer;
  }
}
