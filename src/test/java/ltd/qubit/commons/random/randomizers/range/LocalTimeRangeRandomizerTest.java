////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalTimeRangeRandomizerTest extends
        AbstractRangeRandomizerTest<LocalTime> {

  private LocalTime minTime;
  private LocalTime maxTime;

  @BeforeEach
  void setUp() {
    minTime = LocalTime.MIN;
    maxTime = LocalTime.MAX;
    randomizer = new LocalTimeRangeRandomizer(minTime, maxTime);
  }

  @Test
  void generatedLocalTimeShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedLocalTimeShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
  }

  @Test
  void generatedLocalTimeShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new LocalTimeRangeRandomizer(minTime, maxTime, SEED);
    final LocalTime expected = LocalTime.of(4, 16, 51);
    // When
    final LocalTime randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new LocalTimeRangeRandomizer(maxTime, minTime))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new LocalTimeRangeRandomizer(null, maxTime);

    // When
    final LocalTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxTime);
  }

  @Test
  void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new LocalTimeRangeRandomizer(minTime, null);

    // when
    final LocalTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minTime);
  }

}
