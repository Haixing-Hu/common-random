////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.Year;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YearRangeRandomizerTest extends AbstractRangeRandomizerTest<Year> {

  private Year minYear;
  private Year maxYear;

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    minYear = Year.of(1000);
    maxYear = Year.of(3000);
    //  resume checkstyle: MagicNumberCheck
    randomizer = new YearRangeRandomizer(minYear, maxYear);
  }

  @Test
  void generatedYearShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedYearShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minYear, maxYear);
  }

  @Test
  void generatedYearShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new YearRangeRandomizer(minYear, maxYear, SEED);
    final Year expected = Year.of(1894);
    // When
    final Year randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinYearIsAfterMaxYear_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new YearRangeRandomizer(maxYear, minYear))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinYearIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new YearRangeRandomizer(null, maxYear);

    // When
    final Year randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isLessThanOrEqualTo(maxYear);
  }

  @Test
  void whenSpecifiedMaxYearIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new YearRangeRandomizer(minYear, null);

    // when
    final Year randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isGreaterThanOrEqualTo(minYear);
  }
}
