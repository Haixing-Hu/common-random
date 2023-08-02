////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.App;
import ltd.qubit.commons.random.beans.contact.Phone;
import ltd.qubit.commons.random.beans.util.Creatable;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示手机验证码或邮箱验证码。
 *
 * @author 胡海星
 */
public class VerifyCode implements Identifiable, WithApp, Creatable,
    Assignable<VerifyCode> {

  private static final long serialVersionUID = 3900326335477262558L;

  /**
   * 唯一标识。
   */
  @Identifier
  private Long id;

  /**
   * 所属App。
   */
  @Reference(entity = App.class, property = "info")
  private StatefulInfo app;

  /**
   * 手机号码。
   */
  @Nullable
  private Phone mobile;

  /**
   * 电子邮件地址。
   */
  @Size(min = 1, max = 512)
  @Nullable
  private String email;

  /**
   * 验证场景。
   */
  private VerifyScene scene;

  /**
   * 验证码。
   */
  @Size(min = 1, max = 64)
  private String code;

  /**
   * 是否已验证过。
   */
  private boolean verified;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 过期时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant expiredTime;

  public VerifyCode() {
    // empty
  }

  public VerifyCode(final VerifyCode other) {
    assign(other);
  }

  @Override
  public void assign(final VerifyCode other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    mobile = Assignment.clone(other.mobile);
    email = other.email;
    scene = other.scene;
    code = other.code;
    verified = other.verified;
    createTime = other.createTime;
    expiredTime = other.expiredTime;
  }

  @Override
  public VerifyCode clone() {
    return new VerifyCode(this);
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
  public final Phone getMobile() {
    return mobile;
  }

  public final void setMobile(@Nullable final Phone mobile) {
    this.mobile = mobile;
  }

  @Nullable
  public final String getEmail() {
    return email;
  }

  public final void setEmail(@Nullable final String email) {
    this.email = email;
  }

  public final VerifyScene getScene() {
    return scene;
  }

  public final void setScene(final VerifyScene scene) {
    this.scene = scene;
  }

  public final String getCode() {
    return code;
  }

  public final void setCode(final String code) {
    this.code = code;
  }

  public final boolean isVerified() {
    return verified;
  }

  public final void setVerified(final boolean verified) {
    this.verified = verified;
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

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final VerifyCode other = (VerifyCode) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(email, other.email)
        && Equality.equals(scene, other.scene)
        && Equality.equals(code, other.code)
        && Equality.equals(verified, other.verified)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(expiredTime, other.expiredTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, email);
    result = Hash.combine(result, multiplier, scene);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, verified);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, expiredTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("mobile", mobile)
        .append("email", email)
        .append("scene", scene)
        .append("code", code)
        .append("verified", verified)
        .append("createTime", createTime)
        .append("expiredTime", expiredTime)
        .toString();
  }
}
