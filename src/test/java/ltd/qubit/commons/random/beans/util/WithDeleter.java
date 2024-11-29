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

import ltd.qubit.commons.random.beans.person.UserInfo;

/**
 * 此接口表示实体类记录了标记删除者。
 *
 * @author 胡海星
 */
public interface WithDeleter {

  /**
   * 获取当前对象的标记删除者的基本信息。
   *
   * @return
   *     当前对象的标记删除者的基本信息；如为{@code null}则表示当前对象未被标记删除。
   */
  @Nullable
  UserInfo getDeleter();

  /**
   * 设置当前对象的标记删除者的基本信息。
   *
   * @param deleter
   *     待设置的当前对象的标记删除者的新的基本信息；如为{@code null}则表示当前对象未被标
   *     记删除。
   */
  void setDeleter(@Nullable UserInfo deleter);
}
