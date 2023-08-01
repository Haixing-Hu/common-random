////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;

/**
 * A {@link Randomizer} that generates random {@link YearMonth}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class YearMonthRandomizer implements Randomizer<YearMonth> {

  private final YearRandomizer yearRandomizer;
  private final EnumRandomizer<Month> monthRandomizer;

  /**
   * Create a new {@link YearMonthRandomizer}.
   */
  public YearMonthRandomizer() {
    yearRandomizer = new YearRandomizer();
    monthRandomizer = new EnumRandomizer<>(Month.class);
  }

  /**
   * Create a new {@link YearMonthRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public YearMonthRandomizer(final long seed) {
    yearRandomizer = new YearRandomizer(seed);
    monthRandomizer = new EnumRandomizer<>(Month.class, seed);
  }

  @Override
  public YearMonth getRandomValue() {
    final Year randomYear = yearRandomizer.getRandomValue();
    final Month randomMonth = monthRandomizer.getRandomValue();
    return YearMonth.of(randomYear.getValue(), randomMonth.getValue());
  }
}
