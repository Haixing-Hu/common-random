////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Year;
import java.time.ZonedDateTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

/**
 * A {@link Randomizer} that generates random {@link Year}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class YearRandomizer implements Randomizer<Year> {

  private final IntegerRangeRandomizer yearRandomizer;

  /**
   * Create a new {@link YearRandomizer}.
   */
  public YearRandomizer() {
    final CloseRange<ZonedDateTime> range = Parameters.DEFAULT_DATES_RANGE;
    yearRandomizer = new IntegerRangeRandomizer(range.getMin().getYear(),
            range.getMax().getYear());
  }

  /**
   * Create a new {@link YearRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public YearRandomizer(final long seed) {
    final CloseRange<ZonedDateTime> range = Parameters.DEFAULT_DATES_RANGE;
    yearRandomizer = new IntegerRangeRandomizer(range.getMin().getYear(),
            range.getMax().getYear(), seed);
  }

  @Override
  public Year getRandomValue() {
    final int randomYear = yearRandomizer.getRandomValue();
    return Year.of(randomYear);
  }
}
