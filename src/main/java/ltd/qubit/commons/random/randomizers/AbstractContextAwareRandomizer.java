////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;

/**
 * 抽象的上下文感知随机化器。
 *
 * @param <T>
 *     此随机化器生成的类型。
 * @author 胡海星
 */
public abstract class AbstractContextAwareRandomizer<T> extends AbstractRandomizer<T>
    implements ContextAwareRandomizer<T> {

  protected Context context;
  protected boolean contextAware = true;

  /**
   * 构造一个 {@link AbstractContextAwareRandomizer}。
   */
  public AbstractContextAwareRandomizer() {
  }

  /**
   * 构造一个 {@link AbstractContextAwareRandomizer}。
   *
   * @param seed
   *     随机种子。
   */
  public AbstractContextAwareRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 构造一个 {@link AbstractContextAwareRandomizer}。
   *
   * @param context
   *     上下文。
   */
  public AbstractContextAwareRandomizer(final Context context) {
    this.context = context;
    this.contextAware = true;
  }

  /**
   * 判断此随机化器是否是上下文感知的。
   *
   * @return
   *     如果此随机化器是上下文感知的，则为 true；否则为 false。
   */
  public final boolean isContextAware() {
    return contextAware;
  }

  /**
   * 设置此随机化器是否是上下文感知的。
   *
   * @param contextAware
   *     如果此随机化器是上下文感知的，则为 true；否则为 false。
   * @return
   *     此随机化器。
   */
  public final AbstractContextAwareRandomizer<T> setContextAware(final boolean contextAware) {
    this.contextAware = contextAware;
    return this;
  }

  /**
   * 设置参数。
   *
   * @param parameters
   *     参数。
   */
  public void setParameters(final Parameters parameters) {
    //  empty
  }

  @Override
  public void setContext(final Context context) {
    if (context != null && contextAware) {
      this.context = context;
      setParameters(context.getParameters());
    }
  }

}
