////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.ObjectCreationException;

/**
 * 对象创建策略接口。
 *
 * @author 胡海星
 * @since 4.0
 */
public interface ObjectFactory {

  /**
   * 在给定的随机化上下文中创建{@code type}的新实例。
   *
   * @param type
   *     要创建的类型。
   * @param context
   *     当前的随机化上下文。
   * @param <T>
   *     泛型类型。
   * @return 给定类型的新实例。
   * @throws ObjectCreationException
   *     当无法创建给定类型的实例时抛出。
   */
  <T> T createInstance(final Class<T> type, final Context context)
          throws ObjectCreationException;

}
