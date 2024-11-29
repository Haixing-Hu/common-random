////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OffsetTimeRangeRandomizerTest extends
        AbstractRangeRandomizerTest<OffsetTime> {
  //  stop checkstyle: MagicNumberCheck
  private OffsetTime minTime;
  private OffsetTime maxTime;

  @BeforeEach
  void setUp() {
    minTime = OffsetTime.MIN;
    maxTime = OffsetTime.MAX;
    randomizer = new OffsetTimeRangeRandomizer(minTime, maxTime);
  }

  @Test
  void generatedOffsetTimeShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedOffsetTimeShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
  }

  @Test
  void generatedOffsetTimeShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new OffsetTimeRangeRandomizer(minTime, maxTime, SEED);
    final OffsetTime expected = OffsetTime.of(1, 11, 57, 0, ZoneOffset.UTC);
    // When
    final OffsetTime randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new OffsetTimeRangeRandomizer(maxTime, minTime))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new OffsetTimeRangeRandomizer(null, maxTime);

    // When
    final OffsetTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxTime);
  }

  @Test
  void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new OffsetTimeRangeRandomizer(minTime, null);

    // when
    final OffsetTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minTime);
  }
  //  resume checkstyle: MagicNumberCheck
}
