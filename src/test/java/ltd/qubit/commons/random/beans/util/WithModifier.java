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
 * 此接口表示实体类记录了最后一次修改者。
 *
 * @author 胡海星
 */
public interface WithModifier {

  /**
   * 获取当前对象的最后一次修改者的基本信息。
   *
   * @return
   *     当前对象的最后一次修改者的基本信息；如为{@code null}则表示当前对象未被修改过。
   */
  @Nullable
  UserInfo getModifier();

  /**
   * 设置当前对象的最后一次修改者的基本信息。
   *
   * @param modifier
   *     待设置的当前对象的最后一次修改者的新的基本信息；如为{@code null}则表示当前对象未被
   *     修改过。
   */
  void setModifier(@Nullable UserInfo modifier);
}
