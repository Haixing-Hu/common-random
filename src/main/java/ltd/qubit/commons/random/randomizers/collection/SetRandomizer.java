////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.HashSet;
import java.util.Set;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates a set of random values using a delegate
 * {@link Randomizer}.
 *
 * @param <T>
 *         the type of elements to generate
 * @author Eric Taix, Haixing Hu
 */
public class SetRandomizer<T> extends CollectionRandomizer<T> {

  /**
   * Create a new {@link SetRandomizer} that will generate a {@link Set} with a
   * random number of elements.
   *
   * @param delegate
   *         the {@link Randomizer} to use to generate random elements
   */
  public SetRandomizer(final Randomizer<T> delegate) {
    super(delegate);
  }

  /**
   * Create a new {@link SetRandomizer} that will generate a {@link Set} with a
   * fixed number of elements.
   *
   * @param delegate
   *         The {@link Randomizer} used to generate each element
   * @param nbElements
   *         The number of elements to generate
   */
  public SetRandomizer(final Randomizer<T> delegate, final int nbElements) {
    super(delegate, nbElements);
  }

  @Override
  public Set<T> getRandomValue() {
    final Set<T> result = new HashSet<>();
    for (int i = 0; i < nbElements; i++) {
      result.add(getRandomElement());
    }
    return result;
  }

  @Override
  public String toString() {
    return "SetRandomizer [delegate=" + delegate + ", nbElements=" + nbElements + "]";
  }
}
