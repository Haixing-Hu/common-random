////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates a random minute value between {@link
 * MinuteRandomizer#MIN_MINUTE} and {@link MinuteRandomizer#MAX_MINUTE}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MinuteRandomizer implements Randomizer<Integer> {

  public static final int MIN_MINUTE = 0;
  public static final int MAX_MINUTE = 59;

  private final IntegerRangeRandomizer minuteRandomizer;

  public MinuteRandomizer() {
    minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE);
  }

  public MinuteRandomizer(final long seed) {
    minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE, seed);
  }

  @Override
  public Integer getRandomValue() {
    return minuteRandomizer.getRandomValue();
  }
}
