////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.ZonedDateTime;

import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ZonedDateTimeRangeRandomizerTest extends
        AbstractRangeRandomizerTest<ZonedDateTime> {
  // stop checkstyle: MagicNumberCheck
  private ZonedDateTime minZonedDateTime;
  private ZonedDateTime maxZonedDateTime;

  @BeforeEach
  void setUp() {
    minZonedDateTime = Parameters.DEFAULT_DATES_RANGE.getMin()
                                                     .minusYears(50);
    maxZonedDateTime = Parameters.DEFAULT_DATES_RANGE.getMax()
                                                     .plusYears(50);
    randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, maxZonedDateTime);
  }

  @Test
  void generatedZonedDateTimeShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedZonedDateTimeShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minZonedDateTime, maxZonedDateTime);
  }

  @Test
  void generatedZonedDateTimeShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, maxZonedDateTime, SEED);
    final ZonedDateTime expected = ZonedDateTime.parse("1990-05-17T01:11:57Z");
    // When
    final ZonedDateTime randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinZonedDateTimeIsAfterMaxZonedDateTime_throwIllegalArgumentException() {
    assertThatThrownBy(() -> new ZonedDateTimeRangeRandomizer(maxZonedDateTime, minZonedDateTime))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinZonedDateTimeIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new ZonedDateTimeRangeRandomizer(null, maxZonedDateTime);

    // When
    final ZonedDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxZonedDateTime);
  }

  @Test
  void whenSpecifiedMaxZonedDateTimeIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, null);

    // when
    final ZonedDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minZonedDateTime);
  }
  // resume checkstyle: MagicNumberCheck
}
