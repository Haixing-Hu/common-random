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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class ZoneIdRandomizerTest extends AbstractRandomizerTest<ZoneId> {

  @BeforeEach
  void setUp() {
    randomizer = new ZoneIdRandomizer(SEED);
  }

  @Test
  void generatedValueShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // we can not use a canned value, because values returned by the randomizer
    // differ between locales/jdks
    final ZoneId firstZoneId = new ZoneIdRandomizer(SEED).getRandomValue();
    final ZoneId secondZoneId = new ZoneIdRandomizer(SEED).getRandomValue();

    assertThat(firstZoneId).isNotNull();
    assertThat(secondZoneId).isNotNull();
    assertThat(firstZoneId).isEqualTo(secondZoneId);
  }
}
