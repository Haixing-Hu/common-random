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
 * 生成随机{@link Calendar}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class CalendarRandomizer implements Randomizer<Calendar> {

  private final DateRandomizer delegate;

  /**
   * 创建一个新的{@link CalendarRandomizer}。
   */
  public CalendarRandomizer() {
    delegate = new DateRandomizer();
  }

  /**
   * 创建一个新的{@link CalendarRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public CalendarRandomizer(final long seed) {
    delegate = new DateRandomizer(seed);
  }

  /**
   * 生成一个随机的日历。
   *
   * @return 一个随机的{@link Calendar}
   */
  @Override
  public Calendar getRandomValue() {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(delegate.getRandomValue());
    return calendar;
  }
}
