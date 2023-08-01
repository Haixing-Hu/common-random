////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates constant values. Yeah.. That's not random.
 *
 * @author Mahmoud Ben Hassine
 */
public class ConstantRandomizer<T> implements Randomizer<T> {

  private final T value;

  /**
   * Create a new {@link ConstantRandomizer}.
   *
   * @param value
   *         the constant value
   */
  public ConstantRandomizer(final T value) {
    this.value = value;
  }

  @Override
  public T getRandomValue() {
    return value;
  }
}
