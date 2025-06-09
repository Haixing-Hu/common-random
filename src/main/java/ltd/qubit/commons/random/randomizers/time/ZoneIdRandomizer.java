////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成随机{@link ZoneId}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class ZoneIdRandomizer extends AbstractRandomizer<ZoneId> {

  /**
   * 创建一个新的{@link ZoneIdRandomizer}。
   */
  public ZoneIdRandomizer() {
  }

  /**
   * 创建一个新的{@link ZoneIdRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ZoneIdRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的时区ID。
   *
   * @return 一个随机的{@link ZoneId}
   */
  @Override
  public ZoneId getRandomValue() {
    final List<Map.Entry<String, String>> zoneIds = new ArrayList<>(ZoneId.SHORT_IDS.entrySet());
    final Map.Entry<String, String> randomZoneId = zoneIds.get(random.nextInt(zoneIds.size()));
    return ZoneId.of(randomZoneId.getValue());
  }
}
