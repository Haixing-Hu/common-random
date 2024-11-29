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
 * Strategy interface for field/type exclusion.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @since 4.0
 */
public interface ExclusionPolicy {

  /**
   * Given the current randomization context, should the field be excluded from
   * being randomized?
   *
   * @param field
   *         the field to check
   * @param context
   *         the current randomization context
   * @return true if the field should be excluded, false otherwise
   */
  boolean shouldBeExcluded(Field field, Context context);

  /**
   * Given the current randomization context, should the type be excluded from
   * being randomized?
   *
   * @param type
   *         the type to check
   * @param context
   *         the current randomization context
   * @return true if the type should be excluded, false otherwise
   */
  boolean shouldBeExcluded(Class<?> type, Context context);

}
