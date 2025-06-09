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
 * 生成随机{@link GregorianCalendar}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class GregorianCalendarRandomizer implements Randomizer<GregorianCalendar> {

  private final LongRandomizer delegate;

  /**
   * 创建一个新的{@link GregorianCalendarRandomizer}。
   */
  public GregorianCalendarRandomizer() {
    delegate = new LongRandomizer();
  }

  /**
   * 创建一个新的{@link GregorianCalendarRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public GregorianCalendarRandomizer(final long seed) {
    delegate = new LongRandomizer(seed);
  }

  /**
   * 生成一个随机的格里高利历。
   *
   * @return 一个随机的{@link GregorianCalendar}
   */
  @Override
  public GregorianCalendar getRandomValue() {
    final GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTimeInMillis(Math.abs(delegate.getRandomValue()));
    return gregorianCalendar;
  }
}
