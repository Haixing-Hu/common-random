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
import java.time.Month;
import java.time.Year;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * A {@link Randomizer} that generates random {@link LocalDate}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalDateRandomizer implements Randomizer<LocalDate> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;
  private final DayRandomizer dayRandomizer;

  /**
   * Create a new {@link LocalDateRandomizer}.
   */
  public LocalDateRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
    dayRandomizer = new DayRandomizer();
  }

  /**
   * Create a new {@link LocalDateRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public LocalDateRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    dayRandomizer = new DayRandomizer(seed);
  }

  @Override
  public LocalDate getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    final int randomDay = dayRandomizer.getRandomValue();
    return LocalDate.of(randomYear.getValue(), randomMonth.getValue(), randomDay);
  }
}
