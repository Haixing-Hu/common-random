////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.OffsetDateTime;

import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OffsetDateTimeRangeRandomizerTest extends
    AbstractRangeRandomizerTest<OffsetDateTime> {

  //  stop checkstyle: MagicNumberCheck
  private OffsetDateTime minOffsetDateTime;
  private OffsetDateTime maxOffsetDateTime;

  @BeforeEach
  void setUp() {
    minOffsetDateTime = Parameters.DEFAULT_DATES_RANGE.getMin()
                                                      .toOffsetDateTime()
                                                      .minusYears(50);
    maxOffsetDateTime = Parameters.DEFAULT_DATES_RANGE.getMax()
                                                      .toOffsetDateTime()
                                                      .plusYears(50);
    randomizer = new OffsetDateTimeRangeRandomizer(minOffsetDateTime,
        maxOffsetDateTime);
  }

  @Test
  void generatedOffsetDateTimeShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedOffsetDateTimeShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue())
        .isBetween(minOffsetDateTime, maxOffsetDateTime);
  }

  @Test
  void generatedOffsetDateTimeShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new OffsetDateTimeRangeRandomizer(minOffsetDateTime,
        maxOffsetDateTime, SEED);
    final OffsetDateTime expected = OffsetDateTime
        .parse("1990-05-17T01:11:57Z");
    // When
    final OffsetDateTime randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void specifiedMinOffsetDateTimeIsAfterMaxOffsetDateTime_throwIllegalArgumentException() {
    assertThatThrownBy(
        () -> new OffsetDateTimeRangeRandomizer(maxOffsetDateTime,
            minOffsetDateTime))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinOffsetDateTimeIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new OffsetDateTimeRangeRandomizer(null, maxOffsetDateTime);

    // When
    final OffsetDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxOffsetDateTime);
  }

  @Test
  void whenSpecifiedMaxOffsetDateTimeIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new OffsetDateTimeRangeRandomizer(minOffsetDateTime, null);

    // when
    final OffsetDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minOffsetDateTime);
  }
  //  resume checkstyle: MagicNumberCheck
}
