////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示授权记录。
 *
 * @author 胡海星
 */
public class AuthorizeRecord implements Assignable<AuthorizeRecord> {

  /**
   * 连续授权失败次数。
   */
  private int failures;

  /**
   * 上次授权时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant time;

  public AuthorizeRecord() {
    failures = 0;
    time = null;
  }

  public AuthorizeRecord(final AuthorizeRecord other) {
    assign(other);
  }

  @Override
  public void assign(final AuthorizeRecord other) {
    Argument.requireNonNull("other", other);
    failures = other.failures;
    time = other.time;
  }

  @Override
  public AuthorizeRecord clone() {
    return new AuthorizeRecord(this);
  }

  public final int getFailures() {
    return failures;
  }

  public final void setFailures(final int failures) {
    this.failures = failures;
  }

  @Nullable
  public final Instant getTime() {
    return time;
  }

  public final void setTime(@Nullable final Instant time) {
    this.time = time;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AuthorizeRecord other = (AuthorizeRecord) o;
    return Equality.equals(failures, other.failures)
        && Equality.equals(time, other.time);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, failures);
    result = Hash.combine(result, multiplier, time);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("failures", failures)
        .append("time", time)
        .toString();
  }
}
