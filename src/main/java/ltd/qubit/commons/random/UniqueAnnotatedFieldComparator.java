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
 * A comparator used to sort fields by their {@link Unique} annotations.
 *
 * @author Haixing Hu
 */
public class UniqueAnnotatedFieldComparator implements Comparator<Field> {

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

  private boolean isUniqueAnnotated(final Field field) {
    return isAnnotationPresent(field, Unique.class);
  }

  private String[] getRespectTo(final Field field) {
    if (! isAnnotationPresent(field, Unique.class)) {
      return new String[0];
    }
    final Unique unique = getAnnotation(field, Unique.class);
    return unique.respectTo();
  }
}
