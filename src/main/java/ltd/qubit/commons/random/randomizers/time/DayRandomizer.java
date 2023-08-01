////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates a random day value between {@link
 * DayRandomizer#MIN_DAY} and {@link DayRandomizer#MAX_DAY}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DayRandomizer implements Randomizer<Integer> {

  public static final int MIN_DAY = 1;

  // 31 may break some LocalDateTime instances when the dayOfMonth is invalid
  public static final int MAX_DAY = 28;

  private final IntegerRangeRandomizer dayRandomizer;

  public DayRandomizer() {
    dayRandomizer = new IntegerRangeRandomizer(MIN_DAY, MAX_DAY);
  }

  public DayRandomizer(final long seed) {
    dayRandomizer = new IntegerRangeRandomizer(MIN_DAY, MAX_DAY, seed);
  }

  @Override
  public Integer getRandomValue() {
    return dayRandomizer.getRandomValue();
  }
}
