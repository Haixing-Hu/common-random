////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SqlDateRangeRandomizerTest extends AbstractRangeRandomizerTest<Date> {

  private Date minDate;
  private Date maxDate;

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    minDate = new Date(1460448795091L);
    maxDate = new Date(1460448795179L);
    //  resume checkstyle: MagicNumberCheck
    randomizer = new SqlDateRangeRandomizer(minDate, maxDate);
  }

  @Test
  void generatedDateShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedDateShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minDate, maxDate);
  }

  @Test
  void generatedDateShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new SqlDateRangeRandomizer(minDate, maxDate, SEED);
    final Date expected = new Date(1460448795102L);
    // When
    final Date randomDate = randomizer.getRandomValue();
    // Then
    assertThat(randomDate).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new DateRangeRandomizer(maxDate, minDate))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinDateIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new SqlDateRangeRandomizer(null, maxDate);

    // When
    final Date randomDate = randomizer.getRandomValue();

    // Then
    assertThat(randomDate).isBeforeOrEqualTo(maxDate);
  }

  @Test
  void whenSpecifiedMaxDateIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new SqlDateRangeRandomizer(minDate, null);

    // when
    final Date randomDate = randomizer.getRandomValue();

    // Then
    assertThat(randomDate).isAfterOrEqualTo(minDate);
  }

}
