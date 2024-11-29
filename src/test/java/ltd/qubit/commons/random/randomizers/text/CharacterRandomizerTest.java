////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterRandomizerTest extends AbstractRandomizerTest<Character> {

  @BeforeEach
  void setUp() {
    randomizer = new CharacterRandomizer();
  }

  @Test
  void generatedValueMustNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // Given
    randomizer = new CharacterRandomizer(SEED);
    final char expected = 'e';

    // When
    final Character actual = randomizer.getRandomValue();

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldGenerateOnlyAlphabeticLetters() {
    assertThat(randomizer.getRandomValue()).isBetween('A', 'z');
  }

}
