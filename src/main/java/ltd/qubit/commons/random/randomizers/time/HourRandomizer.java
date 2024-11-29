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
 * A {@link Randomizer} that generates a random hour value between {@link
 * HourRandomizer#MIN_HOUR} and {@link HourRandomizer#MAX_HOUR}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class HourRandomizer implements Randomizer<Integer> {

  public static final int MIN_HOUR = 0;
  public static final int MAX_HOUR = 23;

  private final IntegerRangeRandomizer hourRandomizer;

  public HourRandomizer() {
    hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR);
  }

  public HourRandomizer(final long seed) {
    hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR, seed);
  }

  @Override
  public Integer getRandomValue() {
    return hourRandomizer.getRandomValue();
  }
}
