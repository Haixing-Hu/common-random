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
import java.util.Comparator;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.ArrayUtils;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * 一个比较器，用于根据字段的 {@link Unique} 注解对字段进行排序。
 *
 * @author 胡海星
 */
public class UniqueAnnotatedFieldComparator implements Comparator<Field> {

  /**
   * 比较两个字段的顺序。
   *
   * <p>该方法根据字段是否标注了{@link Unique}注解以及注解的{@code respectTo}属性
   * 来决定字段的顺序。
   *
   * @param f1
   *     要比较的第一个字段。
   * @param f2
   *     要比较的第二个字段。
   * @return 一个负整数、零或正整数，表示第一个参数是小于、等于还是大于第二个参数。
   */
  @Override
  public int compare(final Field f1, final Field f2) {
    if (f1 == null) {
      return (f2 == null ? 0 : -1);
    } else if (f2 == null) {
      return +1;
    }
    if (isUniqueAnnotated(f1)) {
      if (isUniqueAnnotated(f2)) {
        final String[] p1 = getRespectTo(f1);
        final String[] p2 = getRespectTo(f2);
        if (ArrayUtils.contains(p1, f2.getName())) {
          return +1;
        } else if (ArrayUtils.contains(p2, f1.getName())) {
          return -1;
        } else {
          return p1.length - p2.length;
        }
      } else {
        return +1;
      }
    } else if (isUniqueAnnotated(f2)) {
      return -1;
    }
    return 0;
  }

  /**
   * 检查字段是否使用了 {@link Unique} 注解。
   *
   * @param field
   *     要检查的字段。
   * @return 如果字段使用了 {@link Unique} 注解，则返回 `true`；否则返回 `false`。
   */
  private boolean isUniqueAnnotated(final Field field) {
    return isAnnotationPresent(field, Unique.class);
  }

  /**
   * 从字段的 {@link Unique} 注解中获取 `respectTo` 的值。
   *
   * @param field
   *     要获取注解值的字段。
   * @return 字段的 {@link Unique} 注解的 `respectTo` 属性值；如果字段没有该注解，则返回一个空数组。
   */
  private String[] getRespectTo(final Field field) {
    if (! isAnnotationPresent(field, Unique.class)) {
      return new String[0];
    }
    final Unique unique = getAnnotation(field, Unique.class);
    return unique.respectTo();
  }
}
