////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.Calendar;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * Generate a random {@link Calendar}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CalendarRandomizer implements Randomizer<Calendar> {

  private final DateRandomizer delegate;

  /**
   * Create a new {@link CalendarRandomizer}.
   */
  public CalendarRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * Create a new {@link CalendarRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public CalendarRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  @Override
  public Calendar getRandomValue() {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(delegate.getRandomValue());
    return calendar;
  }
}
