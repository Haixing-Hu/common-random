////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Month;
import java.time.Period;
import java.time.Year;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * A {@link Randomizer} that generates random {@link Period}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class PeriodRandomizer implements Randomizer<Period> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;
  private final DayRandomizer dayRandomizer;

  /**
   * Create a new {@link PeriodRandomizer}.
   */
  public PeriodRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
    dayRandomizer = new DayRandomizer();
  }

  /**
   * Create a new {@link PeriodRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public PeriodRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    dayRandomizer = new DayRandomizer(seed);
  }

  @Override
  public Period getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    final int randomDay = dayRandomizer.getRandomValue();
    return Period.of(randomYear.getValue(), randomMonth.getValue(), randomDay);
  }
}
