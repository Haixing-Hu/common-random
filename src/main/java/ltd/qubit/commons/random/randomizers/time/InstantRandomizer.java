////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.InstantRangeRandomizer;

import static ltd.qubit.commons.random.Parameters.DEFAULT_DATES_RANGE;

/**
 * A {@link Randomizer} that generates random {@link Instant}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class InstantRandomizer implements Randomizer<Instant>,
    ContextAwareRandomizer<Instant> {

  private final InstantRangeRandomizer delegate;

  /**
   * Create a new {@link InstantRandomizer}.
   */
  public InstantRandomizer() {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max);
  }

  /**
   * Create a new {@link InstantRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public InstantRandomizer(final long seed) {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max, seed);
  }

  /**
   * Create a new {@link InstantRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public InstantRandomizer(final TimeUnit precision, final long seed) {
    final Instant min = DEFAULT_DATES_RANGE.getMin().toInstant();
    final Instant max = DEFAULT_DATES_RANGE.getMax().toInstant();
    delegate = new InstantRangeRandomizer(min, max, precision, seed);
  }

  /**
   * Create a new {@link InstantRandomizer}.
   */
  public InstantRandomizer(final Instant min, final Instant max) {
    delegate = new InstantRangeRandomizer(min, max);
  }

  /**
   * Create a new {@link InstantRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public InstantRandomizer(final Instant min, final Instant max, final long seed) {
    delegate = new InstantRangeRandomizer(min, max, seed);
  }

  /**
   * Create a new {@link InstantRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public InstantRandomizer(final Instant min, final Instant max,
      final TimeUnit precision, final long seed) {
    delegate = new InstantRangeRandomizer(min, max, precision, seed);
  }

  @Override
  public void setContext(final Context context) {
    delegate.setContext(context);
  }

  @Override
  public Instant getRandomValue() {
    return delegate.getRandomValue();
  }
}
