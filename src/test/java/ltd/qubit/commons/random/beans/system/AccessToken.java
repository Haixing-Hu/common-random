////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示存取令牌。
 *
 * @author 胡海星
 */
public class AccessToken implements Serializable, Assignable<AccessToken> {

  private static final long serialVersionUID = -1081791616137854533L;

  /**
   * 访问令牌。
   */
  private String token;

  /**
   * 访问令牌创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 访问令牌过期时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant expiredTime;

  public AccessToken() {
    // empty
  }

  public AccessToken(final AccessToken other) {
    assign(other);
  }

  @Override
  public void assign(final AccessToken other) {
    Argument.requireNonNull("other", other);
    token = other.token;
    createTime = other.createTime;
    expiredTime = other.expiredTime;
  }

  @Override
  public AccessToken clone() {
    return new AccessToken(this);
  }

  public final String getToken() {
    return token;
  }

  public final void setToken(final String token) {
    this.token = token;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  public final Instant getExpiredTime() {
    return expiredTime;
  }

  public final void setExpiredTime(final Instant expiredTime) {
    this.expiredTime = expiredTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final AccessToken other = (AccessToken) o;
    return Equality.equals(token, other.token) && Equality.equals(createTime,
        other.createTime) && Equality.equals(expiredTime, other.expiredTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, expiredTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("token", token)
        .append("createTime", createTime)
        .append("expiredTime", expiredTime)
        .toString();
  }
}
