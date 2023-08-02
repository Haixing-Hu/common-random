////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalDateRangeRandomizerTest extends
        AbstractRangeRandomizerTest<LocalDate> {

  private LocalDate minDate;
  private LocalDate maxDate;

  @BeforeEach
  void setUp() {
    minDate = LocalDate.MIN;
    maxDate = LocalDate.MAX;
    randomizer = new LocalDateRangeRandomizer(minDate, maxDate);
  }

  @Test
  void generatedLocalDateShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedLocalDateShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minDate, maxDate);
  }

  @Test
  void generatedLocalDateShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new LocalDateRangeRandomizer(minDate, maxDate, SEED);
    final LocalDate expected = LocalDate.of(2158145, Month.JUNE, 17);
    // When
    final LocalDate randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new LocalDateRangeRandomizer(maxDate, minDate))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinDateIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new LocalDateRangeRandomizer(null, maxDate);

    // When
    final LocalDate randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isBeforeOrEqualTo(maxDate);
  }

  @Test
  void whenSpecifiedMaxDateIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new LocalDateRangeRandomizer(minDate, null);

    // when
    final LocalDate randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isAfterOrEqualTo(minDate);
  }

}
