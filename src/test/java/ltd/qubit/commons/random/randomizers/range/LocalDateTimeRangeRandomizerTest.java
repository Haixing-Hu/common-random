////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalDateTimeRangeRandomizerTest extends
        AbstractRangeRandomizerTest<LocalDateTime> {

  private LocalDateTime minDateTime;
  private LocalDateTime maxDateTime;

  @BeforeEach
  void setUp() {
    minDateTime = LocalDateTime.MIN;
    maxDateTime = LocalDateTime.MAX;
    randomizer = new LocalDateTimeRangeRandomizer(minDateTime, maxDateTime);
  }

  @Test
  void generatedLocalDateTimeShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedLocalDateTimeShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minDateTime, maxDateTime);
  }

  @Test
  void generatedLocalDateTimeShouldBeAlwaysTheSameForTheSameSeed() {

    // Given
    randomizer = new LocalDateTimeRangeRandomizer(minDateTime, maxDateTime, SEED);
    final LocalDateTime expected = LocalDateTime.parse("-41081222-01-25T06:06:21");
    // When
    final LocalDateTime randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinDateTimeIsAfterMaxDateTime_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new LocalDateTimeRangeRandomizer(maxDateTime, minDateTime))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinDateTimeIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new LocalDateTimeRangeRandomizer(null, maxDateTime);

    // When
    final LocalDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxDateTime);
  }

  @Test
  void whenSpecifiedMaxDateTimeIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new LocalDateTimeRangeRandomizer(minDateTime, null);

    // when
    final LocalDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minDateTime);
  }

  @Test
  void whenMaxDateIsAfterMidnight_thenShouldNotThrowException() {
    minDateTime = LocalDateTime.parse("2019-10-21T23:33:44");
    maxDateTime = LocalDateTime.parse("2019-10-22T00:33:22");

    // Given
    randomizer = new LocalDateTimeRangeRandomizer(minDateTime, maxDateTime);

    // when
    final LocalDateTime randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBetween(minDateTime, maxDateTime);
  }

  @Test
  void generatedLocalDateTimeShouldHavePrecisionOfSeconds() {
    final Parameters parameters = new Parameters();
    final CloseRange<LocalDate> dateRange = parameters.getDateRange();
    final LocalDate minDate = dateRange.getMin();
    final LocalDate maxDate = dateRange.getMax();
    final CloseRange<LocalTime> timeRange = parameters.getTimeRange();
    final LocalTime minTime = timeRange.getMin();
    final LocalTime maxTime = timeRange.getMax();
    final LocalDateTimeRangeRandomizer randomizer = new LocalDateTimeRangeRandomizer(
            LocalDateTime.of(minDate, minTime), LocalDateTime.of(maxDate, maxTime));
    final LocalDateTime datetime = randomizer.getRandomValue();
    assertThat(datetime.getNano()).isEqualTo(0);  // this will fail
  }

}
