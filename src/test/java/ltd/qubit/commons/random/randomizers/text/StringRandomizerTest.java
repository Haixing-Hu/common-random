////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringRandomizerTest extends AbstractRandomizerTest<String> {

  @BeforeEach
  void setUp() {
    randomizer = new StringRandomizer();
  }

  @Test
  void generatedValueMustNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // Given
    randomizer = new StringRandomizer(SEED);
    final String expected = "OMtThyhVNLWUZNRcBaQKxIyedUsFwd";
    // When
    final String actual = randomizer.getRandomValue();
    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void theLengthOfTheGeneratedValueShouldBeLowerThanTheSpecifiedMaxLength() {
    // Given
    final int maxLength = 10;
    randomizer = new StringRandomizer(maxLength, SEED);
    final String expectedValue = "OMtThyhV";
    // When
    final String actual = randomizer.getRandomValue();
    // Then
    assertThat(actual).isEqualTo(expectedValue);
    assertThat(actual.length()).isLessThanOrEqualTo(maxLength);
  }

  @Test
  void theLengthOfTheGeneratedValueShouldBeGreaterThanTheSpecifiedMinLength() {
    // Given
    final int minLength = 3;
    final int maxLength = 10;
    randomizer = new StringRandomizer(minLength, maxLength, SEED);
    final String expectedValue = "OMtThyhV";

    // When
    final String actual = randomizer.getRandomValue();

    // Then
    assertThat(actual).isEqualTo(expectedValue);
    assertThat(actual.length()).isBetween(minLength, maxLength);
  }

}
