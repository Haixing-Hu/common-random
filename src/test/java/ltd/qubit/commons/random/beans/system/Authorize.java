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

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型封装了应用授权信息。
 *
 * @author 胡海星
 */
public class Authorize implements Serializable, WithApp, Assignable<Authorize> {

  private static final long serialVersionUID = 215678588664562119L;

  /**
   * 应用的基本信息。
   */
  private StatefulInfo app;

  /**
   * 访问令牌。
   */
  @Size(max = 128)
  private String token;

  /**
   * 访问令牌创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant createTime;

  /**
   * 访问令牌过期时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant expiredTime;

  public Authorize() {
    // empty
  }

  public Authorize(final Authorize other) {
    assign(other);
  }

  @Override
  public void assign(final Authorize other) {
    Argument.requireNonNull("other", other);
    app = Assignment.clone(other.app);
    token = other.token;
    createTime = other.createTime;
    expiredTime = other.expiredTime;
  }

  @Override
  public Authorize clone() {
    return new Authorize(this);
  }

  public final StatefulInfo getApp() {
    return app;
  }

  public final void setApp(final StatefulInfo app) {
    this.app = app;
  }

  public final String getToken() {
    return token;
  }

  public final void setToken(final String token) {
    this.token = token;
  }

  @Nullable
  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(@Nullable final Instant createTime) {
    this.createTime = createTime;
  }

  public final Instant getExpiredTime() {
    return expiredTime;
  }

  public final void setExpiredTime(final Instant expiredTime) {
    this.expiredTime = expiredTime;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Authorize other = (Authorize) o;
    return Equality.equals(app, other.app)
        && Equality.equals(token, other.token)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(expiredTime, other.expiredTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, expiredTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("app", app)
        .append("token", token)
        .append("createTime", createTime)
        .append("expiredTime", expiredTime)
        .toString();
  }
}
