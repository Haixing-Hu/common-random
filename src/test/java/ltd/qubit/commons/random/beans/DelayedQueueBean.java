////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("rawtypes")
public class DelayedQueueBean {

  private DelayQueue delayQueue;
  private DelayQueue<DummyDelayed> typedDelayQueue;

  public DelayedQueueBean() {
  }

  public DelayQueue getDelayQueue() {
    return this.delayQueue;
  }

  public DelayQueue<DummyDelayed> getTypedDelayQueue() {
    return this.typedDelayQueue;
  }

  public void setDelayQueue(final DelayQueue delayQueue) {
    this.delayQueue = delayQueue;
  }

  public void setTypedDelayQueue(final DelayQueue<DummyDelayed> typedDelayQueue) {
    this.typedDelayQueue = typedDelayQueue;
  }

  private class DummyDelayed implements Delayed {

    @Override
    public long getDelay(final TimeUnit timeUnit) {
      return 0;
    }

    @Override
    public int compareTo(final Delayed delayed) {
      return 0;
    }
  }
}
