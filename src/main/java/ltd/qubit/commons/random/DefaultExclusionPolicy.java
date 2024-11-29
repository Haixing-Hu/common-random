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
 * Component that encapsulates the logic of field/type exclusion in a given
 * randomization context. This class implements exclusion rules in the
 * predefined order.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DefaultExclusionPolicy implements ExclusionPolicy {

  /**
   * Given the current randomization context, should the field be excluded from
   * being populated ?
   *
   * @param field
   *         the field to check
   * @param context
   *         the current randomization context
   * @return true if the field should be excluded, false otherwise
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
   * Given the current randomization context, should the type be excluded from
   * being populated ?
   *
   * @param type
   *         the type to check
   * @param context
   *         the current randomization context
   * @return true if the type should be excluded, false otherwise
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
