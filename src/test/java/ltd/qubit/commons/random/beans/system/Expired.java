////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示对象过期信息。
 *
 * @author 胡海星
 */
public class Expired implements Assignable<Expired> {

  /**
   * 过期时间，UTC时区。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant time;

  /**
   * 过期原因。
   */
  private ExpiredReason reason;

  public Expired() {
    // empty
  }

  public Expired(final Expired other) {
    assign(other);
  }

  @Override
  public void assign(final Expired other) {
    Argument.requireNonNull("other", other);
    time = other.time;
    reason = other.reason;
  }

  @Override
  public Expired clone() {
    return new Expired(this);
  }

  public final Instant getTime() {
    return time;
  }

  public final void setTime(final Instant time) {
    this.time = time;
  }

  public final ExpiredReason getReason() {
    return reason;
  }

  public final void setReason(final ExpiredReason reason) {
    this.reason = reason;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Expired other = (Expired) o;
    return Equality.equals(time, other.time)
        && Equality.equals(reason, other.reason);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, time);
    result = Hash.combine(result, multiplier, reason);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("time", time)
        .append("reason", reason)
        .toString();
  }
}
