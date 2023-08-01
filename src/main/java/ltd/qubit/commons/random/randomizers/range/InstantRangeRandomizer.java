////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.DateUtils.toChronoUnit;

/**
 * Generate a random {@link Instant} in the given range.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public class InstantRangeRandomizer extends AbstractRangeRandomizer<Instant> {

  private TimeUnit precision = TimeUnit.MILLISECONDS;

  /**
   * Create a new {@link InstantRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   */
  public InstantRangeRandomizer(final Instant min, final Instant max) {
    super(min, max);
  }

  /**
   * Create a new {@link InstantRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param precision
   *     the precision of the generated value.
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final TimeUnit precision) {
    super(min, max);
    this.precision = requireNonNull("precision", precision);
  }

  /**
   * Create a new {@link InstantRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param seed
   *     initial seed
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final long seed) {
    super(min, max, seed);
  }

  /**
   * Create a new {@link InstantRangeRandomizer}.
   *
   * @param min
   *     min value
   * @param max
   *     max value
   * @param precision
   *     the precision of the generated value.
   * @param seed
   *     initial seed
   */
  public InstantRangeRandomizer(final Instant min, final Instant max,
      final TimeUnit precision, final long seed) {
    super(min, max, seed);
    this.precision = requireNonNull("precision", precision);
  }

  public InstantRangeRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    setParameters(parameters);
  }

  public InstantRangeRandomizer(final Parameters parameters,
      final TimeUnit precision) {
    super(parameters.getSeed());
    setParameters(parameters);
    this.precision = requireNonNull("precision", precision);
  }

  @Override
  protected void checkValues() {
    final Instant min = requireNonNull("min cannot be null.", range.getMin());
    final Instant max = requireNonNull("max cannot be null", range.getMax());
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("max must be after min");
    }
  }

  @Override
  protected Instant getDefaultMinValue() {
    return Instant.ofEpochMilli(Long.MIN_VALUE);
  }

  @Override
  protected Instant getDefaultMaxValue() {
    return Instant.ofEpochMilli(Long.MAX_VALUE);
  }

  @Override
  public void setParameters(final Parameters parameters) {
    final CloseRange<LocalDate> localDateRange = parameters.getDateRange();
    final LocalDate minDate = localDateRange.getMin();
    final LocalDate maxDate = localDateRange.getMax();
    final Instant min = minDate.atStartOfDay(DateUtils.UTC_ZONE_ID).toInstant();
    final Instant max = maxDate.atStartOfDay(DateUtils.UTC_ZONE_ID).toInstant();
    setRange(min, max);
  }

  @Override
  public Instant getRandomValue() {
    final Instant result = random.nextInstant(range);
    return result.truncatedTo(toChronoUnit(precision));
  }

}
