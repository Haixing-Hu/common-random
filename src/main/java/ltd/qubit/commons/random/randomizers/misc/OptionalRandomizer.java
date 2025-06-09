////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.util.Random;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个 {@link Randomizer}，它根据可选的百分比，从委托中返回随机值。
 *
 * @param <T>
 *         此随机化器生成的类型
 * @author 胡海星
 */
public class OptionalRandomizer<T> implements ContextAwareRandomizer<T> {

  private static final int MAX_PERCENT = 100;

  private final Random random;
  private final Randomizer<T> delegate;
  private final int optionalPercent;

  /**
   * 使用委托随机化器和可选的百分比阈值创建一个新的 {@link OptionalRandomizer}。
   *
   * @param delegate
   *         用于检索随机值的委托
   * @param optionalPercent
   *         要返回的随机化值的百分比（介于0和100之间）
   */
  public OptionalRandomizer(final Randomizer<T> delegate,
          final int optionalPercent) {
    this(System.currentTimeMillis(), delegate, optionalPercent);
  }

  /**
   * 使用委托随机化器和可选的百分比阈值创建一个新的 {@link OptionalRandomizer}。
   *
   * @param seed
   *         随机数生成器的种子。
   * @param delegate
   *         用于检索随机值的委托
   * @param optionalPercent
   *         要返回的随机化值的百分比（介于0和100之间）
   */
  public OptionalRandomizer(final long seed, final Randomizer<T> delegate,
          final int optionalPercent) {
    this.random = new Random(seed);
    this.delegate = delegate;
    if (optionalPercent > MAX_PERCENT) {
      this.optionalPercent = MAX_PERCENT;
    } else if (optionalPercent < 0) {
      this.optionalPercent = 0;
    } else {
      this.optionalPercent = optionalPercent;
    }
  }

  @Override
  public void setContext(final Context context) {
    if (delegate instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer) delegate).setContext(context);
    }
  }

  @Override
  public T getRandomValue() {
    final int randomPercent = random.nextInt(MAX_PERCENT);
    if (randomPercent <= optionalPercent) {
      return delegate.getRandomValue();
    }
    return null;
  }

}
