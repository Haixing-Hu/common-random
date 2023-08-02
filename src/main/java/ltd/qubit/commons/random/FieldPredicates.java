////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * Common predicates to identify fields. Usually used in combination to define a
 * field in an object graph. For example:
 *
 * <pre>
 *  Predicate&lt;Field&gt; predicate = named("name").and(ofType(String.class))
 *                                                  .and(inClass(Person.class));
 * </pre>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldPredicates {

  /**
   * Create a predicate to check that a field has a certain name pattern.
   *
   * @param name
   *     pattern of the field name to check
   * @return Predicate to check that a field has a certain name pattern
   */
  public static Predicate<Field> named(final String name) {
    final Pattern pattern = Pattern.compile(name);
    return field -> pattern.matcher(field.getName()).matches();
  }

  /**
   * Create a predicate to check that a field has a certain type.
   *
   * @param type
   *     of the field to check
   * @return Predicate to check that a field has a certain type
   */
  public static Predicate<Field> ofType(final Class<?> type) {
    return field -> field.getType().equals(type);
  }

  /**
   * Create a predicate to check that a field is defined in a given class.
   *
   * @param clazz
   *     enclosing type of the field to check
   * @return Predicate to check that a field is defined in a given class.
   */
  public static Predicate<Field> inClass(final Class<?> clazz) {
    return field -> field.getDeclaringClass().equals(clazz);
  }

  /**
   * Create a predicate to check that a field is annotated with one of the given
   * annotations.
   *
   * @param annotations
   *     present on the field
   * @return Predicate to check that a field is annotated with one of the given
   *     annotations.
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
   * Create a predicate to check that a field has a given set of modifiers.
   *
   * @param modifiers
   *     of the field to check
   * @return Predicate to check that a field has a given set of modifiers
   */
  public static Predicate<Field> hasModifiers(final Integer modifiers) {
    return field -> (modifiers & field.getModifiers()) == modifiers;
  }

}
