////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.util;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.random.beans.system.Expired;

/**
 * 此接口表示模型具有可过期信息属性。
 *
 * @author 胡海星
 */
public interface Expirable {

  /**
   * 获取此对象的过期信息。
   *
   * @return
   *     此对象的过期信息，或{@code null}如果它尚未过期。
   */
  @Nullable
  Expired getExpired();


  /**
   * 设置此对象的过期信息。
   *
   * @param expired
   *     此对象的过期信息，或{@code null}如果它尚未过期。
   */
  void setExpired(@Nullable Expired expired);

  /**
   * 判定此对象是否已过期。
   *
   * @return
   *     此对象是否已过期。
   */
  @Computed("expiredTime")
  default boolean hasExpired() {
    return getExpired() != null;
  }
}
