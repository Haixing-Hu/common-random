////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * A {@link Randomizer} that generates random {@link ZonedDateTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZonedDateTimeRandomizer extends AbstractRandomizer<ZonedDateTime> {

  private LocalDateTimeRandomizer localDateTimeRandomizer;

  /**
   * Create a new {@link ZonedDateTimeRandomizer}.
   */
  public ZonedDateTimeRandomizer() {
    localDateTimeRandomizer = new LocalDateTimeRandomizer();
  }

  /**
   * Create a new {@link ZonedDateTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public ZonedDateTimeRandomizer(final long seed) {
    super(seed);
    localDateTimeRandomizer = new LocalDateTimeRandomizer(seed);
  }

  @Override
  public ZonedDateTime getRandomValue() {
    final LocalDateTime randomLocalDateTime = localDateTimeRandomizer.getRandomValue();
    final ZoneId randomZoneId = getRandomZoneId();
    return ZonedDateTime.of(randomLocalDateTime, randomZoneId);
  }

  private ZoneId getRandomZoneId() {
    final Set<String> availableZoneIds = ZoneOffset.getAvailableZoneIds();
    final List<String> ids = new ArrayList<>(availableZoneIds);
    return ZoneId.of(ids.get(random.nextInt(ids.size())));
  }

  public void setLocalDateTimeRandomizer(final LocalDateTimeRandomizer localDateTimeRandomizer) {
    this.localDateTimeRandomizer = localDateTimeRandomizer;
  }
}
