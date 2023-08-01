////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.contact.Location;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示会话的环境。
 *
 * @author 胡海星
 */
public class Environment implements Serializable, Emptyful, Normalizable,
    Assignable<Environment> {

  private static final long serialVersionUID = -8699592133484381102L;

  /**
   * 客户端IP地址。
   */
  @Size(max = 128)
  @Nullable
  private String ip;

  /**
   * 客户端地理位置。
   */
  @Nullable
  private Location location;

  /**
   * 客户端的操作系统平台。
   */
  @Nullable
  private Platform platform;

  /**
   * 客户端的设备唯一ID。
   */
  @Size(max = 64)
  @Nullable
  private String udid;

  /**
   * 客户端的推送通知令牌。
   */
  @Size(max = 64)
  @Nullable
  private String pushToken;

  public Environment() {
    // empty
  }

  public Environment(final Environment other) {
    assign(other);
  }

  @Override
  public void assign(final Environment other) {
    Argument.requireNonNull("other", other);
    ip = other.ip;
    location = Assignment.clone(other.location);
    platform = other.platform;
    udid = other.udid;
    pushToken = other.pushToken;
  }

  @Override
  public Environment clone() {
    return new Environment(this);
  }

  @Nullable
  public final String getIp() {
    return ip;
  }

  public final void setIp(@Nullable final String ip) {
    this.ip = ip;
  }

  @Nullable
  public final Location getLocation() {
    return location;
  }

  public final void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Nullable
  public final Platform getPlatform() {
    return platform;
  }

  public final void setPlatform(@Nullable final Platform platform) {
    this.platform = platform;
  }

  @Nullable
  public final String getUdid() {
    return udid;
  }

  public final void setUdid(@Nullable final String udid) {
    this.udid = udid;
  }

  @Nullable
  public final String getPushToken() {
    return pushToken;
  }

  public final void setPushToken(@Nullable final String pushToken) {
    this.pushToken = pushToken;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Environment other = (Environment) o;
    return Equality.equals(ip, other.ip)
        && Equality.equals(location, other.location)
        && Equality.equals(platform, other.platform)
        && Equality.equals(udid, other.udid)
        && Equality.equals(pushToken, other.pushToken);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, ip);
    result = Hash.combine(result, multiplier, location);
    result = Hash.combine(result, multiplier, platform);
    result = Hash.combine(result, multiplier, udid);
    result = Hash.combine(result, multiplier, pushToken);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("ip", ip)
        .append("location", location)
        .append("platform", platform)
        .append("udid", udid)
        .append("pushToken", pushToken)
        .toString();
  }
}
