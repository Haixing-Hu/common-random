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
 * A {@link Randomizer} that generates random {@link ZoneOffset}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZoneOffsetRandomizer implements Randomizer<ZoneOffset> {

  /**
   * Upper bound for ZoneOffset seconds.
   *
   * @see java.time.ZoneOffset#ofTotalSeconds
   */
  private static final int MAX_SECONDS = 64800;

  private final IntegerRangeRandomizer integerRangeRandomizer;

  /**
   * Create a new {@link ZoneOffsetRandomizer}.
   */
  public ZoneOffsetRandomizer() {
    integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS);
  }

  /**
   * Create a new {@link ZoneOffsetRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public ZoneOffsetRandomizer(final long seed) {
    integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS, seed);
  }

  @Override
  public ZoneOffset getRandomValue() {
    final Integer randomValue = integerRangeRandomizer.getRandomValue();
    return ZoneOffset.ofTotalSeconds(randomValue);
  }

}
