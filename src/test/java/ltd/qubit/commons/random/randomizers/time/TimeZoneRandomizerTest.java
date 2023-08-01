////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.util.TimeZone;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeZoneRandomizerTest extends AbstractRandomizerTest<TimeZone> {

  @BeforeEach
  void setUp() {
    randomizer = new TimeZoneRandomizer(SEED);
  }

  @Test
  void generatedValueShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // we can not use a canned value, because the available TimeZones differ between locales/jdks
    final TimeZone firstTimeZone = new TimeZoneRandomizer(SEED).getRandomValue();
    final TimeZone secondTimeZone = new TimeZoneRandomizer(SEED).getRandomValue();

    assertThat(firstTimeZone).isNotNull();
    assertThat(secondTimeZone).isNotNull();
    assertThat(firstTimeZone).isEqualTo(secondTimeZone);
  }
}
