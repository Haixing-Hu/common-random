////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.LinkedList;
import java.util.Queue;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个 {@link Randomizer}，它使用委托的 {@link Randomizer} 生成一个随机值的队列。
 *
 * @param <T>
 *     队列中元素的类型。
 * @author 胡海星
 */
public class QueueRandomizer<T> extends AbstractCollectionRandomizer<T> {

  /**
   * 创建一个新的 {@link QueueRandomizer}，它将生成一个具有随机数量元素的队列。
   *
   * @param delegate
   *     用于生成元素的委托 {@link Randomizer}。
   */
  public QueueRandomizer(final Randomizer<T> delegate) {
    super(delegate);
  }

  /**
   * 创建一个新的 {@link QueueRandomizer}，它将生成一个具有固定数量元素的队列。
   *
   * @param delegate
   *     用于生成元素的委托 {@link Randomizer}。
   * @param nbElements
   *     要生成的元素数量。
   */
  public QueueRandomizer(final Randomizer<T> delegate, final int nbElements) {
    super(delegate, nbElements);
  }

  /**
   * 生成一个随机的 {@link Queue}。
   *
   * @return
   *     一个随机的 {@link Queue}。
   */
  @Override
  public Queue<T> getRandomValue() {
    final Queue<T> result = new LinkedList<>();
    for (int i = 0; i < size; i++) {
      result.add(getRandomElement());
    }
    return result;
  }

  @Override
  public String toString() {
    return "QueueRandomizer [delegate=" + delegate + ", nbElements=" + size + "]";
  }
}
