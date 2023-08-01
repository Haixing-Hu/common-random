////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.util;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.beans.commons.Token;

/**
 * 此接口表示实体类具有令牌属性。
 *
 * @author 胡海星
 */
public interface WithToken {

  /**
   * 获取当前对象的令牌。
   *
   * @return
   *     当前对象的令牌，可能为{@code null}。
   */
  @Nullable
  Token getToken();

  /**
   * 设置当前对象的令牌。
   *
   * @param token
   *     待设置的新的令牌，可以为{@code null}。
   */
  void setToken(@Nullable Token token);
}
