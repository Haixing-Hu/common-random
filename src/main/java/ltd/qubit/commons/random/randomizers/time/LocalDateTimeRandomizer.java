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
 * A {@link Randomizer} that generates random {@link LocalDateTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalDateTimeRandomizer implements Randomizer<LocalDateTime> {

  private LocalDateRandomizer localDateRandomizer;
  private LocalTimeRandomizer localTimeRandomizer;

  /**
   * Create a new {@link LocalDateTimeRandomizer}.
   */
  public LocalDateTimeRandomizer() {
    localDateRandomizer = new LocalDateRandomizer();
    localTimeRandomizer = new LocalTimeRandomizer();
  }

  /**
   * Create a new {@link LocalDateTimeRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public LocalDateTimeRandomizer(final long seed) {
    localDateRandomizer = new LocalDateRandomizer(seed);
    localTimeRandomizer = new LocalTimeRandomizer(seed);
  }

  @Override
  public LocalDateTime getRandomValue() {
    final LocalDate localDate = localDateRandomizer.getRandomValue();
    final LocalTime localTime = localTimeRandomizer.getRandomValue();
    return LocalDateTime.of(localDate, localTime);
  }

  public void setLocalDateRandomizer(final LocalDateRandomizer localDateRandomizer) {
    this.localDateRandomizer = localDateRandomizer;
  }

  public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
    this.localTimeRandomizer = localTimeRandomizer;
  }
}
