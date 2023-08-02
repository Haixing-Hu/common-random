////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.person;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Deletable;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Info;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示用户的角色信息。
 *
 * @author 孙建
 */
public class UserRole implements Serializable, Identifiable, Deletable {

  private static final long serialVersionUID = 3352425101388793004L;

  /**
   * 唯一标识，系统自动生成。
   */
  private Long id;

  /**
   * 用户信息。
   */
  private UserInfo user;

  /**
   * 所属App。
   */
  private Info app;

  /**
   * 角色。
   */
  private Info role;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public static UserRole create(final UserInfo user, final Info app,
      final Info role) {
    final UserRole result = new UserRole();
    result.setUser(user);
    result.setApp(app);
    result.setRole(role);
    return result;
  }

  public UserRole() {
    // empty
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final UserInfo getUser() {
    return user;
  }

  public final void setUser(final UserInfo user) {
    this.user = user;
  }

  public final Info getApp() {
    return app;
  }

  public final void setApp(final Info app) {
    this.app = app;
  }

  public final Info getRole() {
    return role;
  }

  public final void setRole(final Info role) {
    this.role = role;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  public final void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UserRole other = (UserRole) o;
    return Equality.equals(id, other.id)
        && Equality.equals(user, other.user)
        && Equality.equals(app, other.app)
        && Equality.equals(role, other.role)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, user);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, role);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("user", user)
        .append("app", app)
        .append("role", role)
        .append("createTime", createTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
