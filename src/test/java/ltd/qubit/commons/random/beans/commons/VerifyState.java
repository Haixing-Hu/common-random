////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

/**
 * 此枚举表示验证状态。
 *
 * @author 胡海星
 */
public enum VerifyState {

  /**
   * 未验证。
   */
  NONE,

  /**
   * 验证中。
   */
  VERIFYING,

  /**
   * 验证正确。
   */
  VALID,

  /**
   * 验证错误。
   */
  INVALID,
}
