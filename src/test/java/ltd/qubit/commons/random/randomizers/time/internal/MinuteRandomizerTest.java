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
import ltd.qubit.commons.random.randomizers.time.MinuteRandomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.randomizers.time.MinuteRandomizer.MAX_MINUTE;
import static ltd.qubit.commons.random.randomizers.time.MinuteRandomizer.MIN_MINUTE;

import static org.assertj.core.api.Assertions.assertThat;

class MinuteRandomizerTest extends AbstractRandomizerTest<Integer> {

  @BeforeEach
  void setUp() {
    randomizer = new MinuteRandomizer();
  }

  @Test
  void generatedValueShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedValueShouldBeWithinRange() {
    assertThat(randomizer.getRandomValue()).isBetween(MIN_MINUTE, MAX_MINUTE);
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // Given
    randomizer = new MinuteRandomizer(SEED);
    final Integer expected = 57;
    // When
    final Integer actual = randomizer.getRandomValue();
    // Then
    assertThat(actual).isEqualTo(expected);
  }
}
