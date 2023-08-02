////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Double}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DoubleRandomizer extends AbstractRandomizer<Double> {

  /**
   * Create a new {@link DoubleRandomizer}.
   */
  public DoubleRandomizer() {
  }

  /**
   * Create a new {@link DoubleRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public DoubleRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public Double getRandomValue() {
    return random.nextDouble();
  }
}
