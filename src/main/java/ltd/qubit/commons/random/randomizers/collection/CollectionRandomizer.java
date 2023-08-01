////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.Collection;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.ByteRandomizer;

import static java.lang.Math.abs;

/**
 * A base class for collection randomizers.
 *
 * @param <T>
 *         the type of elements in the collection
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
abstract class CollectionRandomizer<T> implements
        ContextAwareRandomizer<Collection<T>> {

  final int nbElements;

  final Randomizer<T> delegate;

  CollectionRandomizer(final Randomizer<T> delegate) {
    this(delegate, abs(new ByteRandomizer().getRandomValue()));
  }

  CollectionRandomizer(final Randomizer<T> delegate, final int nbElements) {
    if (delegate == null) {
      throw new IllegalArgumentException("delegate must not be null");
    }
    checkArguments(nbElements);
    this.nbElements = nbElements;
    this.delegate = delegate;
  }

  private void checkArguments(final int nbElements) {
    if (nbElements < 0) {
      throw new IllegalArgumentException(
          "The number of elements to generate must be >= 0");
    }
  }

  @Override
  public void setContext(final Context context) {
    if (delegate instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer) delegate).setContext(context);
    }
  }

  T getRandomElement() {
    return delegate.getRandomValue();
  }

}
