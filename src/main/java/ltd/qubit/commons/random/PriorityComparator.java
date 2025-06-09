////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.util.Comparator;

import ltd.qubit.commons.annotation.Priority;

/**
 * 按升序比较使用{@link Priority}注解的组件。
 *
 * @author 胡海星
 */
public class PriorityComparator implements Comparator<Object> {

  /**
   * {@inheritDoc}
   */
  @Override
  public int compare(final Object o1, final Object o2) {
    final int p1 = getPriority(o1);
    final int p2 = getPriority(o2);
    return Integer.compare(p2, p1);
  }

  private int getPriority(final Object object) {
    if (object != null) {
      final Priority annotation = object.getClass().getAnnotation(Priority.class);
      if (annotation != null) {
        return annotation.value();
      }
    }
    return 0;
  }
}
