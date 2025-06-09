////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * 一个 {@link Randomizer}，它使用委托的 {@link Randomizer} 生成一组随机值。
 *
 * @param <T>
 *     要生成的元素的类型。
 * @author Eric Taix, 胡海星
 */
public class SetRandomizer<T> extends AbstractCollectionRandomizer<T> {
  /**
   * 创建一个新的 {@link SetRandomizer}，它将生成一个具有随机数量元素的 {@link Set}。
   *
   * @param delegate
   *     用于生成随机元素的 {@link Randomizer}。
   */
  public SetRandomizer(final Randomizer<T> delegate) {
    super(delegate);
  }

  /**
   * 创建一个新的 {@link SetRandomizer}，它将生成一个具有固定数量元素的 {@link Set}。
   *
   * @param delegate
   *     用于生成每个元素的 {@link Randomizer}。
   * @param size
   *     要生成的元素数量。
   */
  public SetRandomizer(final Randomizer<T> delegate, final int size) {
    super(delegate, size);
  }

  /**
   * 生成一个随机的 {@link Set}。
   *
   * @return
   *     一个随机的 {@link Set}。
   */
  @Override
  public Set<T> getRandomValue() {
    final Set<T> result = new HashSet<>();
    for (int i = 0; i < size; i++) {
      result.add(getRandomElement());
    }
    return result;
  }

  @Override
  public String toString() {
    return "SetRandomizer [delegate=" + delegate + ", nbElements=" + size + "]";
  }
}
