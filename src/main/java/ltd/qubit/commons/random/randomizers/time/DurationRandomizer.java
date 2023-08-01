////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates random {@link Duration}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DurationRandomizer implements Randomizer<Duration> {

  private static final int MIN_AMOUNT = 0;
  private static final int MAX_AMOUNT = 100;

  private final IntegerRangeRandomizer amountRandomizer;
  private final TemporalUnit unit;

  /**
   * Create a new {@link DurationRandomizer}. Generated {@link Duration} objects
   * will use {@link ChronoUnit#HOURS}.
   */
  public DurationRandomizer() {
    this(ChronoUnit.HOURS);
  }

  /**
   * Create a new {@link DurationRandomizer}.
   *
   * @param unit
   *         the temporal unit for created durations
   */
  public DurationRandomizer(final TemporalUnit unit) {
    this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT), unit);
  }

  /**
   * Create a new {@link DurationRandomizer}. Generated {@link Duration} objects
   * will use {@link ChronoUnit#HOURS}.
   *
   * @param seed
   *         initial seed
   */
  public DurationRandomizer(final long seed) {
    this(seed, ChronoUnit.HOURS);
  }

  /**
   * Create a new {@link DurationRandomizer}.
   *
   * @param seed
   *         initial seed
   * @param unit
   *         the temporal unit for created durations
   */
  public DurationRandomizer(final long seed, final TemporalUnit unit) {
    this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT, seed), unit);
  }

  private DurationRandomizer(final IntegerRangeRandomizer amountRandomizer,
          final TemporalUnit unit) {
    this.amountRandomizer = amountRandomizer;
    this.unit = requireValid(unit);
  }

  @Override
  public Duration getRandomValue() {
    final int randomAmount = amountRandomizer.getRandomValue();
    return Duration.of(randomAmount, unit);
  }

  private static TemporalUnit requireValid(final TemporalUnit unit) {
    if (unit.isDurationEstimated() && unit != ChronoUnit.DAYS) {
      throw new IllegalArgumentException("Temporal unit " + unit
          + " can't be used to create Duration objects");
    }
    return unit;
  }
}
