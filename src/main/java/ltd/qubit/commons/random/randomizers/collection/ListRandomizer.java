////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.ArrayList;
import java.util.List;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个自定义的 {@link Randomizer}，它使用委托的 {@link Randomizer} 生成一个随机值的列表。
 *
 * @param <T>
 *     此 {@link Randomizer} 生成的元素的类型。
 * @author 胡海星
 */
public class ListRandomizer<T> extends AbstractCollectionRandomizer<T> {
  /**
   * 创建一个新的 {@link ListRandomizer}，它将生成一个具有随机数量元素的列表。
   * 每个元素都由参数 {@link Randomizer} 生成。
   *
   * @param delegate
   *     用于生成元素的 {@link Randomizer}。
   */
  public ListRandomizer(final Randomizer<T> delegate) {
    super(delegate);
  }

  /**
   * 创建一个新的 {@link ListRandomizer}，它将生成一个具有固定数量元素的列表。
   * 每个元素都由参数 {@link Randomizer} 生成。
   *
   * @param delegate
   *     用于生成每个元素的 {@link Randomizer}。
   * @param size
   *     要生成的元素数量。
   */
  public ListRandomizer(final Randomizer<T> delegate, final int size) {
    super(delegate, size);
  }

  /**
   * 生成一个随机的 {@link List}。
   *
   * @return
   *     一个随机的 {@link List}。
   */
  @Override
  public List<T> getRandomValue() {
    final List<T> result = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      result.add(getRandomElement());
    }
    return result;
  }

  @Override
  public String toString() {
    return "ListRandomizer [delegate=" + delegate + ", nbElements=" + size + "]";
  }
}
