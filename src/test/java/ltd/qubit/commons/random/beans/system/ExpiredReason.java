////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

/**
 * 此枚举表示会话 (session) 过期原因。
 *
 * @author 胡海星
 */
public enum ExpiredReason {

  /**
   * 用户登出。
   */
  LOGOUT,

  /**
   * 超时。
   */
  TIMEOUT,

  /**
   * 单点登录，即用户在其他位置登录导致当前会话结束。
   */
  SINGLE_POINT,

  /**
   * 系统维护。
   */
  MAINTENANCE,

  /**
   * 无，即会话未过期。
   */
  NONE;

  public String id() {
    return name().toLowerCase();
  }
}
