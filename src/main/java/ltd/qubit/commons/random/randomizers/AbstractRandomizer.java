////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.api.Randomizer;

import static java.util.ResourceBundle.getBundle;

/**
 * Base class for {@link Randomizer} implementations.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class AbstractRandomizer<T> implements Randomizer<T> {

  protected final RandomEx random;

  protected AbstractRandomizer() {
    random = new RandomEx();
  }

  protected AbstractRandomizer(final long seed) {
    random = new RandomEx(seed);
  }

  protected String[] getPredefinedValuesOf(final String key) {
    return getBundle("common-random-data").getString(key).split(",");
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
