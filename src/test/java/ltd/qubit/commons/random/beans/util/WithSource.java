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

/**
 * 此接口表示实体类属于某个来源。
 *
 * @author 胡海星
 */
public interface WithSource {

  /**
   * 获取当前对象所属来源的基本信息。
   *
   * @return
   *     当前对象所属来源的基本信息。
   */
  @Nullable
  Info getSource();

  /**
   * 设置当前对象所属来源的基本信息。
   *
   * @param source
   *     当前对象所属的新来源的基本信息。
   */
  void setSource(@Nullable Info source);
}
