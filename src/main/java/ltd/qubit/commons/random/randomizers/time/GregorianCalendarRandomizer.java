////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.GregorianCalendar;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.LongRandomizer;

/**
 * Generate a random {@link GregorianCalendarRandomizer}.
 */
public class GregorianCalendarRandomizer implements Randomizer<GregorianCalendar> {

  private final LongRandomizer delegate;

  /**
   * Create a new {@link GregorianCalendarRandomizer}.
   */
  public GregorianCalendarRandomizer() {
    delegate = new LongRandomizer();
  }

  /**
   * Create a new {@link GregorianCalendarRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public GregorianCalendarRandomizer(final long seed) {
    delegate = new LongRandomizer(seed);
  }

  @Override
  public GregorianCalendar getRandomValue() {
    final GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTimeInMillis(Math.abs(delegate.getRandomValue()));
    return gregorianCalendar;
  }
}
