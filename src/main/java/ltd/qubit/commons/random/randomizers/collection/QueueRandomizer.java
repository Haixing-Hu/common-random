////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.LinkedList;
import java.util.Queue;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates a queue of random values using a delegate
 * {@link Randomizer}.
 *
 * @param <T>
 *         the type of elements in the queue
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class QueueRandomizer<T> extends CollectionRandomizer<T> {

  /**
   * Create a new {@link QueueRandomizer} that will generate a queue with a
   * random number of elements.
   *
   * @param delegate
   *         the delegate {@link Randomizer} used to generate elements
   */
  public QueueRandomizer(final Randomizer<T> delegate) {
    super(delegate);
  }

  /**
   * Create a new {@link QueueRandomizer} that will generate a queue with a
   * fixed number of elements.
   *
   * @param delegate
   *         The delegate {@link Randomizer} used to generate elements
   * @param nbElements
   *         The number of elements to generate
   */
  public QueueRandomizer(final Randomizer<T> delegate, final int nbElements) {
    super(delegate, nbElements);
  }

  @Override
  public Queue<T> getRandomValue() {
    final Queue<T> result = new LinkedList<>();
    for (int i = 0; i < nbElements; i++) {
      result.add(getRandomElement());
    }
    return result;
  }

  @Override
  public String toString() {
    return "QueueRandomizer [delegate=" + delegate + ", nbElements=" + nbElements + "]";
  }
}
