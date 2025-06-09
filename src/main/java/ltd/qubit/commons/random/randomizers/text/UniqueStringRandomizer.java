////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import java.util.HashSet;
import java.util.Set;

import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.UuidUtils;

/**
 * 始终生成唯一字符串的字符串随机化器。
 *
 * @author 胡海星
 */
public class UniqueStringRandomizer extends AbstractContextAwareRandomizer<String> {

  private static final Set<String> CACHE = new HashSet<>();

  /**
   * 生成一个唯一的字符串。
   *
   * @return 一个唯一的字符串。
   */
  @Override
  public String getRandomValue() {
    String result = UuidUtils.getUuid();
    synchronized (CACHE) {
      while (CACHE.contains(result)) {
        result = UuidUtils.getUuid();
      }
      CACHE.add(result);
    }
    return result;
  }
}
