////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time.internal;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;
import ltd.qubit.commons.random.randomizers.time.HourRandomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.randomizers.time.HourRandomizer.MAX_HOUR;
import static ltd.qubit.commons.random.randomizers.time.HourRandomizer.MIN_HOUR;

import static org.assertj.core.api.Assertions.assertThat;

class HourRandomizerTest extends AbstractRandomizerTest<Integer> {

  @BeforeEach
  void setUp() {
    randomizer = new HourRandomizer();
  }

  @Test
  void generatedValueShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedValueShouldBeWithinRange() {
    assertThat(randomizer.getRandomValue()).isBetween(MIN_HOUR, MAX_HOUR);
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // Given
    randomizer = new HourRandomizer(SEED);
    final Integer expected = 21;
    // When
    final Integer actual = randomizer.getRandomValue();
    // Then
    assertThat(actual).isEqualTo(expected);
  }
}
