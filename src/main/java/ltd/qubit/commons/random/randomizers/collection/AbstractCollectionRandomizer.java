////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
abstract class AbstractCollectionRandomizer<T> implements ContextAwareRandomizer<Collection<T>> {

  protected final int size;

  protected final Randomizer<T> delegate;

  AbstractCollectionRandomizer(final Randomizer<T> delegate) {
    this(delegate, abs(new ByteRandomizer().getRandomValue()));
  }

  AbstractCollectionRandomizer(final Randomizer<T> delegate, final int size) {
    if (delegate == null) {
      throw new IllegalArgumentException("delegate must not be null");
    }
    checkArguments(size);
    this.size = size;
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
      ((ContextAwareRandomizer<?>) delegate).setContext(context);
    }
  }

  T getRandomElement() {
    return delegate.getRandomValue();
  }

}