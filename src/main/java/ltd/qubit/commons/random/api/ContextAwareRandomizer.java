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

/**
 * 一个 {@link Randomizer} 的接口，该随机化器能感知其被调用时所处的
 * {@link Context 随机化上下文}。
 *
 * @param <T>
 *     随机化器生成的类型。
 * @author 胡海星
 */
public interface ContextAwareRandomizer<T> extends Randomizer<T> {

  /**
   * 设置随机化上下文。
   *
   * @param context
   *     随机化上下文。
   */
  void setContext(Context context);

}
