////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Predicate;

import ltd.qubit.commons.random.api.ExclusionPolicy;

import static ltd.qubit.commons.reflect.FieldUtils.isStatic;

/**
 * 该组件封装了在给定随机化上下文中排除字段/类型的逻辑。此类按预定义顺序实现排除规则。
 *
 * @author 胡海星
 */
public class DefaultExclusionPolicy implements ExclusionPolicy {

  /**
   * 根据当前的随机化上下文，是否应将该字段排除在填充之外？
   *
   * @param field
   *     要检查的字段
   * @param context
   *     当前的随机化上下文
   * @return 如果该字段应该被排除，则返回 true，否则返回 false
   */
  public boolean shouldBeExcluded(final Field field, final Context context) {
    if (isStatic(field)) {
      return true;
    }
    final Set<Predicate<Field>> predicates = context.getParameters()
                                                    .getFieldExclusionPredicates();
    for (final Predicate<Field> predicate : predicates) {
      if (predicate.test(field)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 根据当前的随机化上下文，是否应将该类型排除在填充之外？
   *
   * @param type
   *     要检查的类型
   * @param context
   *     当前的随机化上下文
   * @return 如果该类型应该被排除，则返回 true，否则返回 false
   */
  public boolean shouldBeExcluded(final Class<?> type, final Context context) {
    final Set<Predicate<Class<?>>> predicates = context.getParameters()
                                                       .getTypeExclusionPredicates();
    for (final Predicate<Class<?>> predicate : predicates) {
      if (predicate.test(type)) {
        return true;
      }
    }
    return false;
  }
}
