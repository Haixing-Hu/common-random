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

public abstract class AbstractContextAwareRandomizer<T> extends AbstractRandomizer<T>
    implements ContextAwareRandomizer<T> {

  protected Context context;
  protected boolean contextAware = true;

  public AbstractContextAwareRandomizer() {
  }

  public AbstractContextAwareRandomizer(final long seed) {
    super(seed);
  }

  public AbstractContextAwareRandomizer(final Context context) {
    this.context = context;
    this.contextAware = true;
  }

  public final boolean isContextAware() {
    return contextAware;
  }

  public final AbstractContextAwareRandomizer<T> setContextAware(final boolean contextAware) {
    this.contextAware = contextAware;
    return this;
  }

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
