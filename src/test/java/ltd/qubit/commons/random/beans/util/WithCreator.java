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

import ltd.qubit.commons.random.beans.person.UserInfo;

/**
 * 此接口表示实体类记录了创建者。
 *
 * @author 胡海星
 */
public interface WithCreator {

  /**
   * 获取当前对象的创建者的基本信息。
   *
   * @return
   *     当前对象的创建者的基本信息，若为{@code null}则表示当前对象没有创建者。
   */
  @Nullable
  UserInfo getCreator();

  /**
   * 设置当前对象的创建者的基本信息。
   *
   * @param creator
   *     待设置的当前对象的创建者的新的基本信息，若为{@code null}则表示当前对象没有创建者。
   */
  void setCreator(@Nullable UserInfo creator);
}
