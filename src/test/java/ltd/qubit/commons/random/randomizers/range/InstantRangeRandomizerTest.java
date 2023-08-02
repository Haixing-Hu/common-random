////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InstantRangeRandomizerTest extends
        AbstractRangeRandomizerTest<Instant> {

  private Instant minInstant;
  private Instant maxInstant;

  @BeforeEach
  void setUp() {
    minInstant = Instant.ofEpochMilli(Long.MIN_VALUE);
    maxInstant = Instant.ofEpochMilli(Long.MAX_VALUE);
    randomizer = new InstantRangeRandomizer(minInstant, maxInstant);
  }

  @Test
  void generatedInstantShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedInstantShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minInstant, maxInstant);
  }

  @Test
  void generatedInstantShouldBeAlwaysTheSameForTheSameSeed() {

    // Given
    randomizer = new InstantRangeRandomizer(minInstant, maxInstant, SEED);
    final Instant expected = Instant.parse("-161817669-05-04T13:13:09.525Z");
    // When
    final Instant randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinInstantIsAfterMaxInstant_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new InstantRangeRandomizer(maxInstant, minInstant))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinInstantIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new InstantRangeRandomizer(null, maxInstant);

    // When
    final Instant randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxInstant);
  }

  @Test
  void whenSpecifiedMaxInstantIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new InstantRangeRandomizer(minInstant, null);

    // when
    final Instant randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minInstant);
  }

  @Test
  void whenMaxDateIsAfterMidnight_thenShouldNotThrowException() {
    minInstant = Instant.parse("2019-10-21T23:33:44.00Z");
    minInstant = Instant.parse("2019-10-22T00:33:22.00Z");

    // Given
    randomizer = new InstantRangeRandomizer(minInstant, maxInstant);

    // when
    final Instant randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBetween(minInstant, maxInstant);
  }

}
