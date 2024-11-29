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
 * A {@link Randomizer} that generates random {@link ZoneId}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZoneIdRandomizer extends AbstractRandomizer<ZoneId> {

  /**
   * Create a new {@link ZoneIdRandomizer}.
   */
  public ZoneIdRandomizer() {
  }

  /**
   * Create a new {@link ZoneIdRandomizer}.
   *
   * @param seed
   *         initial seed
   */
  public ZoneIdRandomizer(final long seed) {
    super(seed);
  }

  @Override
  public ZoneId getRandomValue() {
    final List<Map.Entry<String, String>> zoneIds = new ArrayList<>(ZoneId.SHORT_IDS.entrySet());
    final Map.Entry<String, String> randomZoneId = zoneIds.get(random.nextInt(zoneIds.size()));
    return ZoneId.of(randomZoneId.getValue());
  }
}
