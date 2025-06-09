////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 用于识别字段的常见谓词。通常用于组合定义对象图中的字段。例如：
 *
 * <pre>
 *  Predicate&lt;Field&gt; predicate = named("name").and(ofType(String.class))
 *                                                  .and(inClass(Person.class));
 * </pre>
 *
 * @author 胡海星
 */
public class FieldPredicates {

  /**
   * 创建一个谓词来检查字段是否具有特定的名称模式。
   *
   * @param name
   *     要检查的字段名称的模式
   * @return 用于检查字段是否具有特定名称模式的谓词
   */
  public static Predicate<Field> named(final String name) {
    final Pattern pattern = Pattern.compile(name);
    return field -> pattern.matcher(field.getName()).matches();
  }

  /**
   * 创建一个谓词来检查字段是否具有特定的类型。
   *
   * @param type
   *     要检查的字段的类型
   * @return 用于检查字段是否具有特定类型的谓词
   */
  public static Predicate<Field> ofType(final Class<?> type) {
    return field -> field.getType().equals(type);
  }

  /**
   * 创建一个谓词来检查字段是否在给定的类中定义。
   *
   * @param clazz
   *     要检查的字段的封闭类型
   * @return 用于检查字段是否在给定类中定义的谓词
   */
  public static Predicate<Field> inClass(final Class<?> clazz) {
    return field -> field.getDeclaringClass().equals(clazz);
  }

  /**
   * 创建一个谓词，检查字段是否被给定的注解之一所注解。
   *
   * @param annotations
   *     字段上存在的注解
   * @return 谓词，用于检查字段是否被给定的注解之一所注解
   */
  @SafeVarargs
  public static Predicate<Field> isAnnotatedWith(
      final Class<? extends Annotation>... annotations) {
    return field -> {
      for (final Class<? extends Annotation> annotation : annotations) {
        if (field.isAnnotationPresent(annotation)) {
          return true;
        }
      }
      return false;
    };
  }

  /**
   * 创建一个谓词来检查字段是否具有一组给定的修饰符。
   *
   * @param modifiers
   *     要检查的字段的修饰符
   * @return 用于检查字段是否具有一组给定修饰符的谓词
   */
  public static Predicate<Field> hasModifiers(final Integer modifiers) {
    return field -> (modifiers & field.getModifiers()) == modifiers;
  }

}
