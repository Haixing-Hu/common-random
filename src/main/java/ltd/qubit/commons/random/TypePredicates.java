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
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * 用于识别类型的常用谓词。通常组合使用来定义一组类型。例如：
 *
 * <pre>
 *     Predicate&lt;Class&lt;?&gt;&gt; predicate = inPackage("java.util")
 *                                                  .or(inPackage("com.sun"));
 * </pre>
 *
 * @author 胡海星
 */
public class TypePredicates {

  /**
   * 创建一个谓词来检查类型是否具有给定的名称。
   *
   * @param name
   *     类型的名称
   * @return 检查类型是否具有给定名称的谓词。
   */
  public static Predicate<Class<?>> named(final String name) {
    return clazz -> clazz.getName().equals(name);
  }

  /**
   * 创建一个谓词来检查类是否具有特定类型。
   *
   * @param type
   *     要检查的类的类型
   * @return 检查类是否具有特定类型的谓词
   */
  public static Predicate<Class<?>> ofType(final Class<?> type) {
    return clazz -> clazz.equals(type);
  }

  /**
   * 创建一个谓词来检查类型是否定义在给定的包中。
   *
   * @param packageNamePrefix
   *     包名的前缀
   * @return 检查类型是否定义在给定包中的谓词。
   */
  public static Predicate<Class<?>> inPackage(final String packageNamePrefix) {
    return clazz -> clazz.getPackage().getName().startsWith(packageNamePrefix);
  }

  /**
   * 创建一个谓词来检查类型是否使用给定注解之一进行了注解。
   *
   * @param annotations
   *     类型上存在的注解
   * @return 检查类型是否使用给定注解之一进行了注解的谓词。
   */
  @SafeVarargs
  public static Predicate<Class<?>> isAnnotatedWith(
      final Class<? extends Annotation>... annotations) {
    return clazz -> {
      for (final Class<? extends Annotation> annotation : annotations) {
        if (clazz.isAnnotationPresent(annotation)) {
          return true;
        }
      }
      return false;
    };
  }

  /**
   * 创建一个谓词来检查类型是否为接口。
   *
   * @return 检查类型是否为接口的谓词
   */
  public static Predicate<Class<?>> isInterface() {
    return Class::isInterface;
  }

  /**
   * 创建一个谓词来检查类型是否为基本类型。
   *
   * @return 检查类型是否为基本类型的谓词
   */
  public static Predicate<Class<?>> isPrimitive() {
    return Class::isPrimitive;
  }

  /**
   * 创建一个谓词来检查类是否为抽象类。
   *
   * @return 检查类是否为抽象类的谓词
   */
  public static Predicate<Class<?>> isAbstract() {
    return hasModifiers(Modifier.ABSTRACT);
  }

  /**
   * 创建一个谓词来检查类型是否具有给定的修饰符集合。
   *
   * @param modifiers
   *     要检查的类型的修饰符
   * @return 检查类型是否具有给定修饰符集合的谓词
   */
  public static Predicate<Class<?>> hasModifiers(final Integer modifiers) {
    return clazz -> (modifiers & clazz.getModifiers()) == modifiers;
  }

  /**
   * 创建一个谓词来检查类型是否为枚举。
   *
   * @return 检查类型是否为枚举的谓词
   */
  public static Predicate<Class<?>> isEnum() {
    return Class::isEnum;
  }

  /**
   * 创建一个谓词来检查类型是否为数组。
   *
   * @return 检查类型是否为数组的谓词
   */
  public static Predicate<Class<?>> isArray() {
    return Class::isArray;
  }

  /**
   * 创建一个谓词来检查类型是否可以从另一个类型赋值。
   *
   * @param type
   *     要检查的类型
   * @return 检查类型是否可以从另一个类型赋值的谓词。
   */
  public static Predicate<Class<?>> isAssignableFrom(final Class<?> type) {
    return clazz -> clazz.isAssignableFrom(type);
  }

}
