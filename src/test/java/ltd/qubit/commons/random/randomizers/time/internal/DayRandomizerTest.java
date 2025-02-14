////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;
import ltd.qubit.commons.random.randomizers.time.DayRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

import static ltd.qubit.commons.random.randomizers.time.DayRandomizer.MAX_DAY;
import static ltd.qubit.commons.random.randomizers.time.DayRandomizer.MIN_DAY;

class DayRandomizerTest extends AbstractRandomizerTest<Integer> {

  @BeforeEach
  void setUp() {
    randomizer = new DayRandomizer();
  }

  @Test
  void generatedValueShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedValueShouldBeWithinRange() {
    assertThat(randomizer.getRandomValue()).isBetween(MIN_DAY, MAX_DAY);
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // Given
    randomizer = new DayRandomizer(SEED);
    final Integer expected = 14;
    // When
    final Integer actual = randomizer.getRandomValue();
    // Then
    assertThat(actual).isEqualTo(expected);
  }
}
