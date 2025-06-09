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

/**
 * 字段/类型排除策略接口。
 *
 * @author 胡海星
 * @since 4.0
 */
public interface ExclusionPolicy {

  /**
   * 在给定的随机化上下文中，是否应排除该字段的随机化？
   *
   * @param field
   *     要检查的字段。
   * @param context
   *     当前的随机化上下文。
   * @return 如果应排除该字段，则返回 {@code true}，否则返回 {@code false}。
   */
  boolean shouldBeExcluded(Field field, Context context);

  /**
   * 在给定的随机化上下文中，是否应排除该类型的随机化？
   *
   * @param type
   *     要检查的类型。
   * @param context
   *     当前的随机化上下文。
   * @return 如果应排除该类型，则返回 {@code true}，否则返回 {@code false}。
   */
  boolean shouldBeExcluded(Class<?> type, Context context);

}
