////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * A {@link Randomizer} that generates random {@link OffsetDateTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class OffsetDateTimeRandomizer implements Randomizer<OffsetDateTime> {

  private LocalDateRandomizer localDateRandomizer;
  private LocalTimeRandomizer localTimeRandomizer;
  private ZoneOffsetRandomizer zoneOffsetRandomizer;

  /**
   * Create a new {@link OffsetDateTimeRandomizer}.
   */
  public OffsetDateTimeRandomizer() {
    localDateRandomizer = new LocalDateRandomizer();
    localTimeRandomizer = new LocalTimeRandomizer();
    zoneOffsetRandomizer = new ZoneOffsetRandomizer();
  }

  /**
   * Create a new {@link OffsetDateTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public OffsetDateTimeRandomizer(final long seed) {
    localDateRandomizer = new LocalDateRandomizer(seed);
    localTimeRandomizer = new LocalTimeRandomizer(seed);
    zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
  }

  @Override
  public OffsetDateTime getRandomValue() {
    final LocalDate randomLocalDate = localDateRandomizer.getRandomValue();
    final LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
    final ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
    return OffsetDateTime.of(randomLocalDate, randomLocalTime, randomZoneOffset);
  }

  public void setLocalDateRandomizer(final LocalDateRandomizer localDateRandomizer) {
    this.localDateRandomizer = localDateRandomizer;
  }

  public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
    this.localTimeRandomizer = localTimeRandomizer;
  }

  public void setZoneOffsetRandomizer(final ZoneOffsetRandomizer zoneOffsetRandomizer) {
    this.zoneOffsetRandomizer = zoneOffsetRandomizer;
  }
}
