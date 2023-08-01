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

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.App;
import ltd.qubit.commons.random.beans.commons.Token;
import ltd.qubit.commons.random.beans.person.User;
import ltd.qubit.commons.random.beans.person.UserInfo;
import ltd.qubit.commons.random.beans.util.Creatable;
import ltd.qubit.commons.random.beans.util.Expirable;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Info;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.random.beans.util.WithToken;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示登录会话信息。
 *
 * @author 胡海星
 */
public class Session implements Identifiable, WithApp, WithToken, Expirable,
    Creatable, Normalizable, Assignable<Session> {

  private static final long serialVersionUID = -3182545272338585538L;

  private static final ThreadLocal<Session> CURRENT_THREAD_SESSION = new ThreadLocal<>();

  /**
   * 获取当前线程中保存的全局{@link Session}对象。
   *
   * @return 当前线程中保存的全局{@link Session}对象。
   */
  public static Session getCurrent() {
    return CURRENT_THREAD_SESSION.get();
  }

  /**
   * 设置当前线程中保存的全局{@link Session}对象。
   *
   * @param session
   *     待设置的{@link Session}对象，若为{@code null}则清除当前线程中保存的 全局{@link Session}对象。
   */
  public static void setCurrent(@Nullable final Session session) {
    CURRENT_THREAD_SESSION.set(session);
  }

  public static UserInfo getCurrentUser() {
    final Session current = CURRENT_THREAD_SESSION.get();
    return (current == null ? null : current.getUser());
  }

  public static Info getCurrentApp() {
    final Session current = CURRENT_THREAD_SESSION.get();
    return (current == null ? null : current.getApp());
  }

  public static Token getCurrentToken() {
    final Session current = CURRENT_THREAD_SESSION.get();
    return (current == null ? null : current.getToken());
  }

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 会话发起App。
   */
  @Reference(entity = App.class, property = "info")
  private StatefulInfo app;

  /**
   * 会话发起用户，即当前登录用户。
   */
  @Reference(entity = User.class, property = "info")
  @Nullable
  private UserInfo user;

  /**
   * 会话发起用户的令牌。
   */
  private Token token;

  /**
   * 会话发起用户在当前app中所拥有的角色。
   */
  @Nullable
  private String[] roles;

  /**
   * 会话发起用户在当前app中所拥有的权限。
   */
  @Nullable
  private String[] privileges;

  /**
   * 会话发起客户端的环境
   */
  @Nullable
  private Environment environment;

  /**
   * 该会话用户最后一次活动时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant lastActiveTime;

  /**
   * 会话过期信息。
   */
  @Nullable
  private Expired expired;

  /**
   * 该会话创建时间，即用户登录时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  public Session() {
    // empty
  }

  public Session(final Session other) {
    assign(other);
  }

  @Override
  public void assign(final Session other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    user = Assignment.clone(other.user);
    token = Assignment.clone(other.token);
    roles = Assignment.clone(other.roles);
    privileges = Assignment.clone(other.privileges);
    environment = Assignment.clone(other.environment);
    lastActiveTime = other.lastActiveTime;
    expired = Assignment.clone(other.expired);
    createTime = other.createTime;
  }

  @Override
  public Session clone() {
    return new Session(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final StatefulInfo getApp() {
    return app;
  }

  public final void setApp(final StatefulInfo app) {
    this.app = app;
  }

  @Nullable
  public final UserInfo getUser() {
    return user;
  }

  public final void setUser(@Nullable final UserInfo user) {
    this.user = user;
  }

  public final Token getToken() {
    return token;
  }

  public final void setToken(final Token token) {
    this.token = token;
  }

  @Nullable
  public final String[] getRoles() {
    return roles;
  }

  public final void setRoles(@Nullable final String[] roles) {
    this.roles = roles;
  }

  @Nullable
  public final String[] getPrivileges() {
    return privileges;
  }

  public final void setPrivileges(@Nullable final String[] privileges) {
    this.privileges = privileges;
  }

  @Nullable
  public final Environment getEnvironment() {
    return environment;
  }

  public final void setEnvironment(@Nullable final Environment environment) {
    this.environment = environment;
  }

  public final Instant getLastActiveTime() {
    return lastActiveTime;
  }

  public final void setLastActiveTime(final Instant lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

  @Nullable
  public final Expired getExpired() {
    return expired;
  }

  public final void setExpired(@Nullable final Expired expired) {
    this.expired = expired;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Session other = (Session) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(user, other.user)
        && Equality.equals(token, other.token)
        && Equality.equals(roles, other.roles)
        && Equality.equals(privileges, other.privileges)
        && Equality.equals(environment, other.environment)
        && Equality.equals(lastActiveTime, other.lastActiveTime)
        && Equality.equals(expired, other.expired)
        && Equality.equals(createTime, other.createTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, user);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, roles);
    result = Hash.combine(result, multiplier, privileges);
    result = Hash.combine(result, multiplier, environment);
    result = Hash.combine(result, multiplier, lastActiveTime);
    result = Hash.combine(result, multiplier, expired);
    result = Hash.combine(result, multiplier, createTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("user", user)
        .append("token", token)
        .append("roles", roles)
        .append("privileges", privileges)
        .append("environment", environment)
        .append("lastActiveTime", lastActiveTime)
        .append("expired", expired)
        .append("createTime", createTime)
        .toString();
  }
}
