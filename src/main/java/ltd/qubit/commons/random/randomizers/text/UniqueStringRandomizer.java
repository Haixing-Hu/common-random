////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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
 * A string randomizer which always generate unique strings.
 *
 * @author Haixing Hu
 */
public class UniqueStringRandomizer extends AbstractContextAwareRandomizer<String> {

  private static final Set<String> CACHE = new HashSet<>();

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
