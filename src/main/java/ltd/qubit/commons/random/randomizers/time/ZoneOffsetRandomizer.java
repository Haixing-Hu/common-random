////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.ZoneOffset;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * 生成随机{@link ZoneOffset}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class ZoneOffsetRandomizer implements Randomizer<ZoneOffset> {

  /**
   * ZoneOffset秒数的上限。
   *
   * @see java.time.ZoneOffset#ofTotalSeconds
   */
  private static final int MAX_SECONDS = 64800;

  private final IntegerRangeRandomizer integerRangeRandomizer;

  /**
   * 创建一个新的{@link ZoneOffsetRandomizer}。
   */
  public ZoneOffsetRandomizer() {
    integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS);
  }

  /**
   * 创建一个新的{@link ZoneOffsetRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ZoneOffsetRandomizer(final long seed) {
    integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS, seed);
  }

  /**
   * 生成一个随机的时区偏移量。
   *
   * @return 一个随机的{@link ZoneOffset}
   */
  @Override
  public ZoneOffset getRandomValue() {
    final Integer randomValue = integerRangeRandomizer.getRandomValue();
    return ZoneOffset.ofTotalSeconds(randomValue);
  }

}
