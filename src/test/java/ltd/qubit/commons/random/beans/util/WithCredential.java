////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.util;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.beans.commons.CredentialInfo;

/**
 * 此接口表示实体具有证件信息。
 *
 * @author 胡海星
 */
public interface WithCredential {

  /**
   * 获取当前对象的证件信息。
   *
   * @return
   *     当前对象的证件信息，或{@code null}如果当前对象没有设置证件信息。
   */
  @Nullable
  CredentialInfo getCredential();
}
