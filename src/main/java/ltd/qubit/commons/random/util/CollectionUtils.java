////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.util.List;
import java.util.Random;

/**
 * 集合工具类。
 *
 * <p><strong>此类仅供内部使用。</strong>
 *
 * @author 胡海星
 */
public final class CollectionUtils {

  private CollectionUtils() {
  }

  /**
   * 从列表中获取一个随机元素。
   *
   * @param list
   *     输入列表
   * @param <T>
   *     列表中元素的类型
   * @return 列表中的一个随机元素，如果列表为空则返回null
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
