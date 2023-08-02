////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import java.nio.charset.Charset;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.random.Parameters.DEFAULT_STRING_LENGTH_RANGE;

/**
 * Generate a random {@link String}.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class StringRandomizer extends AbstractContextAwareRandomizer<String> {

  private CloseRange<Integer> lengthRange = DEFAULT_STRING_LENGTH_RANGE;

  /**
   * Create a new {@link StringRandomizer}.
   */
  public StringRandomizer() {
    super();
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param maxLength
   *         of the String to generate
   */
  public StringRandomizer(final int maxLength) {
    super();
    lengthRange = new CloseRange<>(DEFAULT_STRING_LENGTH_RANGE.getMin(), maxLength);
    this.contextAware = false;
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public StringRandomizer(final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param charset
   *         to use
   * @param seed
   *         initial seed
   */
  public StringRandomizer(final Charset charset, final long seed) {
    super(seed);
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param maxLength
   *         of the String to generate
   * @param seed
   *         initial seed
   */
  public StringRandomizer(final int maxLength, final long seed) {
    super(seed);
    lengthRange = new CloseRange<>(DEFAULT_STRING_LENGTH_RANGE.getMin(), maxLength);
    this.contextAware = false;
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param maxLength
   *         of the String to generate
   * @param minLength
   *         of the String to generate
   * @param seed
   *         initial seed
   */
  public StringRandomizer(final int minLength, final int maxLength, final long seed) {
    super(seed);
    if (minLength > maxLength) {
      throw new IllegalArgumentException("minLength should be less than "
          + "or equal to maxLength");
    }
    lengthRange = new CloseRange<>(minLength, maxLength);
    this.contextAware = false;
  }

  /**
   * Create a new {@link StringRandomizer}.
   *
   * @param lengthRange
   *         the range of the length of the string to be generated.
   * @param seed
   *         initial seed
   */
  public StringRandomizer(final CloseRange<Integer> lengthRange, final long seed) {
    super(seed);
    if (lengthRange == null) {
      throw new NullPointerException("length range cannot be null");
    }
    if (lengthRange.getMin() > lengthRange.getMax()) {
      throw new IllegalArgumentException("minLength should be less than "
              + "or equal to maxLength");
    }
    this.lengthRange = lengthRange;
    this.contextAware = false;
  }

  public StringRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    this.lengthRange = parameters.getStringLengthRange();
  }

  @Override
  public void setParameters(final Parameters parameters) {
    this.lengthRange = parameters.getStringLengthRange();
  }

  @Override
  public String getRandomValue() {
    return random.nextLetterString(lengthRange);
  }
}
