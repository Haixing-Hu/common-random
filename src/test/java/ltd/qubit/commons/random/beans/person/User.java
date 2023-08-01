////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.person;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.AuthorizeRecord;
import ltd.qubit.commons.random.beans.commons.State;
import ltd.qubit.commons.random.beans.contact.Phone;
import ltd.qubit.commons.random.beans.organization.Organization;
import ltd.qubit.commons.random.beans.system.VerifyState;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Desensitizable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.Stateful;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithComment;
import ltd.qubit.commons.random.beans.util.WithEmail;
import ltd.qubit.commons.random.beans.util.WithMobile;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.random.beans.util.WithOrganization;
import ltd.qubit.commons.random.beans.util.WithPassword;
import ltd.qubit.commons.random.beans.util.WithUsername;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示系统用户。
 *
 * @author 胡海星
 */
public class User implements Identifiable, WithUsername, WithPassword, WithName,
    WithMobile, WithEmail, WithOrganization, Stateful, WithComment, Auditable,
    Desensitizable, Emptyful, Normalizable, Assignable<User> {

  private static final long serialVersionUID = -4419177823491290198L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 用户名，全局唯一不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String username;

  /**
   * 密码，通常从数据库内取出的是加盐后的哈希值。
   */
  @Size(min = 1, max = 64)
  private String password;

  /**
   * 真实姓名。
   */
  @Size(max = 128)
  @Nullable
  private String name;

  /**
   * 昵称。
   */
  @Size(max = 128)
  @Nullable
  private String nickname;

  /**
   * 手机号码。
   */
  @Unique
  @Nullable
  private Phone mobile;

  /**
   * 手机号码验证状态。
   */
  @Nullable
  private VerifyState mobileVerified;

  /**
   * 电子邮件地址。
   */
  @Size(max = 512)
  @Unique
  @Nullable
  private String email;

  /**
   * 电子邮件地址验证状态。
   */
  @Nullable
  private VerifyState emailVerified;

  /**
   * 头像。
   */
  @Size(max = 512)
  @Nullable
  private String avatar;

  /**
   * 网址。
   */
  @Size(max = 512)
  @Nullable
  private String url;

  /**
   * 描述/自我介绍。
   */
  @Nullable
  private String description;

  /**
   * 所属机构基本信息。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private StatefulInfo organization;

  /**
   * 用户状态。
   */
  private State state = State.NORMAL;

  /**
   * 上次登录的信息记录。
   */
  private AuthorizeRecord lastLogin;

  /**
   * 登录后是否应该立即更改密码。
   */
  private boolean changePassword;

  /**
   * 此账号生效时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant validTime;

  /**
   * 此账号过期时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant expiredTime;

  /**
   * 个人档案ID，对应于 {@link Person} 类的 id 属性。
   *
   * <p>注意若系统中不存在该员工的个人信息，此属性可以为{@code null}
   */
  @Reference(entity = Person.class, property = "id")
  @Nullable
  private Long personId;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

  /**
   * 是否是预定义的数据。
   */
  private boolean predefined;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public User() {
    // empty
  }

  public User(final User other) {
    assign(other);
  }

  @Override
  public void assign(final User other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    username = other.username;
    password = other.password;
    name = other.name;
    nickname = other.nickname;
    mobile = Assignment.clone(other.mobile);
    mobileVerified = other.mobileVerified;
    email = other.email;
    emailVerified = other.emailVerified;
    avatar = other.avatar;
    url = other.url;
    description = other.description;
    organization = Assignment.clone(other.organization);
    state = other.state;
    lastLogin = Assignment.clone(other.lastLogin);
    changePassword = other.changePassword;
    validTime = other.validTime;
    expiredTime = other.expiredTime;
    personId = other.personId;
    comment = other.comment;
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public User clone() {
    return new User(this);
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

  public final String getPassword() {
    return password;
  }

  public final void setPassword(final String password) {
    this.password = password;
  }

  @Override
  @Nullable
  public final String getName() {
    return name;
  }

  @Override
  public final void setName(@Nullable final String name) {
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
  public final Phone getMobile() {
    return mobile;
  }

  public final void setMobile(@Nullable final Phone mobile) {
    this.mobile = mobile;
  }

  @Nullable
  public final VerifyState getMobileVerified() {
    return mobileVerified;
  }

  public final void setMobileVerified(@Nullable final VerifyState mobileVerified) {
    this.mobileVerified = mobileVerified;
  }

  @Nullable
  public final String getEmail() {
    return email;
  }

  public final void setEmail(@Nullable final String email) {
    this.email = email;
  }

  @Nullable
  public final VerifyState getEmailVerified() {
    return emailVerified;
  }

  public final void setEmailVerified(@Nullable final VerifyState emailVerified) {
    this.emailVerified = emailVerified;
  }

  @Nullable
  public final String getAvatar() {
    return avatar;
  }

  public final void setAvatar(@Nullable final String avatar) {
    this.avatar = avatar;
  }

  @Nullable
  public final String getUrl() {
    return url;
  }

  public final void setUrl(@Nullable final String url) {
    this.url = url;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public final StatefulInfo getOrganization() {
    return organization;
  }

  public final void setOrganization(@Nullable final StatefulInfo organization) {
    this.organization = organization;
  }

  public final State getState() {
    return state;
  }

  public final void setState(final State state) {
    this.state = state;
  }

  public AuthorizeRecord getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(final AuthorizeRecord lastLogin) {
    this.lastLogin = lastLogin;
  }

  public final boolean isChangePassword() {
    return changePassword;
  }

  public final void setChangePassword(final boolean changePassword) {
    this.changePassword = changePassword;
  }

  @Nullable
  public final Instant getValidTime() {
    return validTime;
  }

  public final void setValidTime(@Nullable final Instant validTime) {
    this.validTime = validTime;
  }

  @Nullable
  public final Instant getExpiredTime() {
    return expiredTime;
  }

  public final void setExpiredTime(@Nullable final Instant expiredTime) {
    this.expiredTime = expiredTime;
  }

  @Nullable
  public final Long getPersonId() {
    return personId;
  }

  public final void setPersonId(@Nullable final Long personId) {
    this.personId = personId;
  }

  @Nullable
  public final String getComment() {
    return comment;
  }

  public final void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  public final boolean isPredefined() {
    return predefined;
  }

  public final void setPredefined(final boolean predefined) {
    this.predefined = predefined;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  public final void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  public final void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  @Computed({"id", "username", "name", "nickname", "avatar", "state", "deleteTime"})
  public UserInfo getInfo() {
    return new UserInfo(this);
  }

  /**
   * 对此对象脱敏。
   */
  @Override
  public final void desensitize() {
    password = null;
    lastLogin = null;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final User other = (User) o;
    return Equality.equals(id, other.id)
        && Equality.equals(username, other.username)
        && Equality.equals(password, other.password)
        && Equality.equals(name, other.name)
        && Equality.equals(nickname, other.nickname)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(mobileVerified, other.mobileVerified)
        && Equality.equals(email, other.email)
        && Equality.equals(emailVerified, other.emailVerified)
        && Equality.equals(avatar, other.avatar)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(organization, other.organization)
        && Equality.equals(state, other.state)
        && Equality.equals(lastLogin, other.lastLogin)
        && Equality.equals(changePassword, other.changePassword)
        && Equality.equals(validTime, other.validTime)
        && Equality.equals(expiredTime, other.expiredTime)
        && Equality.equals(personId, other.personId)
        && Equality.equals(comment, other.comment)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, username);
    result = Hash.combine(result, multiplier, password);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, nickname);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, mobileVerified);
    result = Hash.combine(result, multiplier, email);
    result = Hash.combine(result, multiplier, emailVerified);
    result = Hash.combine(result, multiplier, avatar);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, organization);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, lastLogin);
    result = Hash.combine(result, multiplier, changePassword);
    result = Hash.combine(result, multiplier, validTime);
    result = Hash.combine(result, multiplier, expiredTime);
    result = Hash.combine(result, multiplier, personId);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("password", password)
        .append("name", name)
        .append("nickname", nickname)
        .append("mobile", mobile)
        .append("mobileVerified", mobileVerified)
        .append("email", email)
        .append("emailVerified", emailVerified)
        .append("avatar", avatar)
        .append("url", url)
        .append("description", description)
        .append("organization", organization)
        .append("state", state)
        .append("lastLogin", lastLogin)
        .append("changePassword", changePassword)
        .append("validTime", validTime)
        .append("expiredTime", expiredTime)
        .append("personId", personId)
        .append("comment", comment)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
