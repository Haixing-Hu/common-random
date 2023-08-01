////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.Token;
import ltd.qubit.commons.random.beans.person.UserInfo;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示用户的登录信息。
 *
 * @author 胡海星
 */
public class Login {

  /**
   * 用户ID，唯一标识，系统自动生成。
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
  @Size(min = 1, max = 128)
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
   * 用户的访问令牌。
   */
  private Token token;

  /**
   * 用户的权限列表。
   */
  private String[] privileges;

  /**
   * 用户的角色列表。
   */
  private String[] roles;

  public Login() {
    // empty
  }

  public Login(final Session session) {
    final UserInfo user = session.getUser();
    id = user.getId();
    username = user.getUsername();
    name = user.getName();
    nickname = user.getNickname();
    avatar = user.getAvatar();
    token = session.getToken();
    privileges = session.getPrivileges();
    roles = session.getRoles();
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

  public final Token getToken() {
    return token;
  }

  public final void setToken(final Token token) {
    this.token = token;
  }

  public final String[] getPrivileges() {
    return privileges;
  }

  public final void setPrivileges(final String[] privileges) {
    this.privileges = privileges;
  }

  public final String[] getRoles() {
    return roles;
  }

  public final void setRoles(final String[] roles) {
    this.roles = roles;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Login other = (Login) o;
    return Equality.equals(id, other.id)
        && Equality.equals(username, other.username)
        && Equality.equals(name, other.name)
        && Equality.equals(nickname, other.nickname)
        && Equality.equals(avatar, other.avatar)
        && Equality.equals(token, other.token)
        && Equality.equals(privileges, other.privileges)
        && Equality.equals(roles, other.roles);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, username);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, nickname);
    result = Hash.combine(result, multiplier, avatar);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, privileges);
    result = Hash.combine(result, multiplier, roles);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("name", name)
        .append("nickname", nickname)
        .append("avatar", avatar)
        .append("token", token)
        .append("privileges", privileges)
        .append("roles", roles)
        .toString();
  }
}
