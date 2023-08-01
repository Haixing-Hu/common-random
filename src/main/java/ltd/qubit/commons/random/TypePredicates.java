////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * Common predicates to identify types. Usually used in combination to define a
 * collection of types. For example:
 *
 * <pre>
 *     Predicate&lt;Class&lt;?&gt;&gt; predicate = inPackage("java.util")
 *                                                  .or(inPackage("com.sun"));
 * </pre>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class TypePredicates {

  /**
   * Create a predicate to check that a type has a given name.
   *
   * @param name
   *     name on the type
   * @return Predicate to check that a type has a given name.
   */
  public static Predicate<Class<?>> named(final String name) {
    return clazz -> clazz.getName().equals(name);
  }

  /**
   * Create a predicate to check that a class has a certain type.
   *
   * @param type
   *     of the class to check
   * @return Predicate to check that a class has a certain type
   */
  public static Predicate<Class<?>> ofType(final Class<?> type) {
    return clazz -> clazz.equals(type);
  }

  /**
   * Create a predicate to check that a type is defined in a given package.
   *
   * @param packageNamePrefix
   *     prefix of the package name
   * @return Predicate to check that a type is defined in a given package.
   */
  public static Predicate<Class<?>> inPackage(final String packageNamePrefix) {
    return clazz -> clazz.getPackage().getName().startsWith(packageNamePrefix);
  }

  /**
   * Create a predicate to check that a type is annotated with one of the given
   * annotations.
   *
   * @param annotations
   *     present on the type
   * @return Predicate to check that a type is annotated with one of the given
   *     annotations.
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
   * Create a predicate to check if a type is an interface.
   *
   * @return a predicate to check if a type is an interface
   */
  public static Predicate<Class<?>> isInterface() {
    return Class::isInterface;
  }

  /**
   * Create a predicate to check if a type is primitive.
   *
   * @return a predicate to check if a type is primitive
   */
  public static Predicate<Class<?>> isPrimitive() {
    return Class::isPrimitive;
  }

  /**
   * Create a predicate to check if a class is abstract.
   *
   * @return a predicate to check if a class is abstract
   */
  public static Predicate<Class<?>> isAbstract() {
    return hasModifiers(Modifier.ABSTRACT);
  }

  /**
   * Create a predicate to check that a type has a given set of modifiers.
   *
   * @param modifiers
   *     of the type to check
   * @return Predicate to check that a type has a given set of modifiers
   */
  public static Predicate<Class<?>> hasModifiers(final Integer modifiers) {
    return clazz -> (modifiers & clazz.getModifiers()) == modifiers;
  }

  /**
   * Create a predicate to check if a type is an enumeration.
   *
   * @return a predicate to check if a type is an enumeration
   */
  public static Predicate<Class<?>> isEnum() {
    return Class::isEnum;
  }

  /**
   * Create a predicate to check if a type is an array.
   *
   * @return a predicate to check if a type is an array
   */
  public static Predicate<Class<?>> isArray() {
    return Class::isArray;
  }

  /**
   * Create a predicate to check if a type is is assignable from another type.
   *
   * @param type
   *     to check
   * @return a predicate to check if a type is is assignable from another type.
   */
  public static Predicate<Class<?>> isAssignableFrom(final Class<?> type) {
    return clazz -> clazz.isAssignableFrom(type);
  }

}
