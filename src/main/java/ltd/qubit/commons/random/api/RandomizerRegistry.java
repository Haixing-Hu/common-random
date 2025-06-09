////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import java.lang.reflect.Field;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

/**
 * {@link Randomizer} 注册表接口。
 *
 * @author 胡海星
 */
public interface RandomizerRegistry {

  /**
   * 初始化注册表。
   *
   * @param random
   *     正在配置的 {@link EasyRandom} 实例。
   * @param parameters
   *     正在配置的 {@link Parameters} 实例。
   */
  void init(EasyRandom random, Parameters parameters);

  /**
   * 获取指定字段的随机化器。
   *
   * @param field
   *     已注册随机化器的字段。
   * @param context
   *     当前上下文。
   * @return 为指定字段注册的随机化器。
   */
  Randomizer<?> get(Field field, Context context);

  /**
   * 获取指定类型的随机化器。
   *
   * @param type
   *     已注册随机化器的类型。
   * @param context
   *     当前上下文。
   * @return 为指定类型注册的随机化器。
   */
  Randomizer<?> get(Class<?> type, Context context);
}
