////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class YearMonthRangeRandomizerTest extends
        AbstractRangeRandomizerTest<YearMonth> {

  private YearMonth minYearMonth;
  private YearMonth maxYearMonth;

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    minYearMonth = YearMonth.of(1000, 1);
    maxYearMonth = YearMonth.of(3000, 12);
    //  resume checkstyle: MagicNumberCheck
    randomizer = new YearMonthRangeRandomizer(minYearMonth, maxYearMonth);
  }

  @Test
  void generatedYearMonthShouldNotBeNull() {
    assertThat(randomizer.getRandomValue()).isNotNull();
  }

  @Test
  void generatedYearMonthShouldBeWithinSpecifiedRange() {
    assertThat(randomizer.getRandomValue()).isBetween(minYearMonth, maxYearMonth);
  }

  @Test
  void generatedYearMonthShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    randomizer = new YearMonthRangeRandomizer(minYearMonth, maxYearMonth, SEED);
    final YearMonth expected = YearMonth.of(1894, 2);
    // When
    final YearMonth randomValue = randomizer.getRandomValue();
    // Then
    assertThat(randomValue).isEqualTo(expected);
  }

  @Test
  void whenSpecifiedMinYearMonthIsAfterMaxYearMonth_thenShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new YearMonthRangeRandomizer(maxYearMonth, minYearMonth))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinYearMonthIsNull_thenShouldUseDefaultMinValue() {
    // Given
    randomizer = new YearMonthRangeRandomizer(null, maxYearMonth);

    // When
    final YearMonth randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isLessThanOrEqualTo(maxYearMonth);
  }

  @Test
  void whenSpecifiedMaxYearMonthIsNull_thenShouldUseDefaultMaxValue() {
    // Given
    randomizer = new YearMonthRangeRandomizer(minYearMonth, null);

    // when
    final YearMonth randomValue = randomizer.getRandomValue();

    // Then
    assertThat(randomValue).isGreaterThanOrEqualTo(minYearMonth);
  }
}
