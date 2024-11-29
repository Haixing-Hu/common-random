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

import ltd.qubit.commons.random.api.Randomizer;

import static ltd.qubit.commons.lang.ClassUtils.isPopulatable;
import static ltd.qubit.commons.random.util.ReflectionUtils.createEmptyCollection;

/**
 * A randomizer for generating {@link Collection} types.
 *
 * @param <T>
 *     the type of elements in the collection
 */
public class CollectionRandomizer<T> extends AbstractCollectionRandomizer<T> {

  private final Class<? extends Collection<T>> collectionType;
  private final Class<T> elementType;

  /**
   * Constructs a new randomizer.
   *
   * @param delegate
   *     the randomizer used to generate the elements in the collection.
   * @param collectionType
   *     the type of the collection to create.
   * @param elementType
   *     the type of the elements in the collection to create.
   */
  public CollectionRandomizer(final Randomizer<T> delegate,
      final Class<? extends Collection<T>> collectionType,
      final Class<T> elementType) {
    super(delegate);
    this.collectionType = collectionType;
    this.elementType = elementType;
  }
  /**
   * Constructs a new randomizer.
   *
   * @param delegate
   *     the randomizer used to generate the elements in the collection.
   * @param size
   *     the number of elements to generate.
   * @param collectionType
   *     the type of the collection to create.
   * @param elementType
   *     the type of the elements in the collection to create.
   */
  CollectionRandomizer(final Randomizer<T> delegate, final int size,
      final Class<? extends Collection<T>> collectionType,
      final Class<T> elementType) {
    super(delegate, size);
    this.collectionType = collectionType;
    this.elementType = elementType;
  }

  /**
   * Generates a random collection.
   *
   * @return
   *     a random collection.
   */
  @Override
  public Collection<T> getRandomValue() {
    final Collection<T> result = createEmptyCollection(collectionType, size);
    if (elementType != null && isPopulatable(elementType)) {
      for (int i = 0; i < size; ++i) {
        final T item = getRandomElement();
        result.add(item);
      }
    }
    return result;
  }
}
