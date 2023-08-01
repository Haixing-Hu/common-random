////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.util.List;
import java.util.Random;

/**
 * Collection utility methods.
 *
 * <strong>This class is intended for internal use only.</strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class CollectionUtils {

  private CollectionUtils() {
  }

  /**
   * Get a random element from the list.
   *
   * @param list
   *         the input list
   * @param <T>
   *         the type of elements in the list
   * @return a random element from the list or null if the list is empty
   */
  public static <T> T randomElementOf(final List<T> list) {
    if (list.isEmpty()) {
      return null;
    }
    return list.get(nextInt(0, list.size()));
  }

  private static int nextInt(final int startInclusive, final int endExclusive) {
    return startInclusive + new Random().nextInt(endExclusive - startInclusive);
  }
}
