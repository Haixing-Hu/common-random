////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;

import static java.lang.String.valueOf;

/**
 * 一个{@link Randomizer}，它将由委托的{@link Randomizer}生成的值字符串化。
 *
 * @author 胡海星
 */
public class StringDelegatingRandomizer implements ContextAwareRandomizer<String> {

  private final Randomizer<?> delegate;

  /**
   * 创建一个新的{@link StringDelegatingRandomizer}。
   *
   * @param delegate
   *         委托的{@link Randomizer}
   */
  public StringDelegatingRandomizer(final Randomizer<?> delegate) {
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContext(final Context context) {
    if (delegate instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer) delegate).setContext(context);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRandomValue() {
    return valueOf(delegate.getRandomValue());
  }
}
