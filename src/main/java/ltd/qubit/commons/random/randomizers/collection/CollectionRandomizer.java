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
 * 用于生成 {@link Collection} 类型的随机化器。
 *
 * @param <T>
 *     集合中元素的类型。
 * @author 胡海星
 */
public class CollectionRandomizer<T> extends AbstractCollectionRandomizer<T> {

  private final Class<? extends Collection<T>> collectionType;
  private final Class<T> elementType;

  /**
   * 构造一个新的随机化器。
   *
   * @param delegate
   *     用于在集合中生成元素的随机化器。
   * @param collectionType
   *     要创建的集合的类型。
   * @param elementType
   *     要创建的集合中元素的类型。
   */
  public CollectionRandomizer(final Randomizer<T> delegate,
      final Class<? extends Collection<T>> collectionType,
      final Class<T> elementType) {
    super(delegate);
    this.collectionType = collectionType;
    this.elementType = elementType;
  }

  /**
   * 构造一个新的随机化器。
   *
   * @param delegate
   *     用于在集合中生成元素的随机化器。
   * @param size
   *     要生成的元素数。
   * @param collectionType
   *     要创建的集合的类型。
   * @param elementType
   *     要创建的集合中元素的类型。
   */
  CollectionRandomizer(final Randomizer<T> delegate, final int size,
      final Class<? extends Collection<T>> collectionType,
      final Class<T> elementType) {
    super(delegate, size);
    this.collectionType = collectionType;
    this.elementType = elementType;
  }

  /**
   * 生成一个随机集合。
   *
   * @return
   *     一个随机集合。
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
