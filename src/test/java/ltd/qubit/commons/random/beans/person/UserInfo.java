////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.person;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.State;
import ltd.qubit.commons.random.beans.util.Deletable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.Stateful;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.random.beans.util.WithUsername;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示用户对象{@link User}的基本信息。
 *
 * @author 胡海星
 */
public class UserInfo implements Identifiable, WithUsername, WithName, Stateful,
    Deletable, Emptyful, Normalizable, Assignable<UserInfo> {

  private static final long serialVersionUID = -3739818849317896899L;

  /**
   * 用户ID。
   */
  @Identifier
  private Long id;

  /**
   * 用户名。
   */
  @Size(min = 1, max = 64)
  private String username;

  /**
   * 真实姓名。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String name;

  /**
   * 昵称。
   */
  @Size(max = 64)
  @Nullable
  private String nickname;

  /**
   * 头像。
   */
  @Size(max = 512)
  @Nullable
  private String avatar;

  /**
   * 用户状态。
   */
  private State state;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public static UserInfo create(@Nullable final Long id,
          @Nullable final String username,
          @Nullable final String name,
          @Nullable final String nickname) {
    if (id == null && username == null && name == null && nickname == null) {
      return null;
    } else {
      final UserInfo result = new UserInfo();
      result.setId(id);
      result.setUsername(username);
      result.setName(name);
      result.setNickname(nickname);
      return result;
    }
  }

  public UserInfo() {
    // empty
  }

  public UserInfo(final UserInfo other) {
    assign(other);
  }

  public UserInfo(final User user) {
    assign(user);
  }

  @Override
  public void assign(final UserInfo other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    username = other.username;
    name = other.name;
    nickname = other.nickname;
    avatar = other.avatar;
    state = other.state;
    deleteTime = other.deleteTime;
  }

  public void assign(final User user) {
    Argument.requireNonNull("user", user);
    id = user.getId();
    username = user.getUsername();
    name = user.getName();
    nickname = user.getNickname();
    avatar = user.getAvatar();
    state = user.getState();
  }

  @Override
  public UserInfo cloneEx() {
    return new UserInfo(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getUsername() {
    return username;
  }

  public final void setUsername(final String username) {
    this.username = username;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public final String getNickname() {
    return nickname;
  }

  public final void setNickname(@Nullable final String nickname) {
    this.nickname = nickname;
  }

  @Nullable
  public final String getAvatar() {
    return avatar;
  }

  public final void setAvatar(@Nullable final String avatar) {
    this.avatar = avatar;
  }

  public final State getState() {
    return state;
  }

  public final void setState(final State state) {
    this.state = state;
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
    final UserInfo other = (UserInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(username, other.username)
        && Equality.equals(name, other.name)
        && Equality.equals(nickname, other.nickname)
        && Equality.equals(avatar, other.avatar)
        && Equality.equals(state, other.state)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, username);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, nickname);
    result = Hash.combine(result, multiplier, avatar);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("name", name)
        .append("nickname", nickname)
        .append("avatar", avatar)
        .append("state", state)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
