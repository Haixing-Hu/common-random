////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class BigDecimalRangeRandomizerTest extends
        AbstractRangeRandomizerTest<BigDecimal> {

  private Double min;
  private Double max;

  //  stop checkstyle: MagicNumberCheck
  @BeforeEach
  void setUp() {
    min = 1.1;
    max = 9.9;
    randomizer = new BigDecimalRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final BigDecimal randomValue = randomizer.getRandomValue();
    assertThat(randomValue.doubleValue()).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new BigDecimalRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new BigDecimalRangeRandomizer(null, max);
    final BigDecimal randomBigDecimal = randomizer.getRandomValue();
    assertThat(randomBigDecimal.doubleValue()).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new BigDecimalRangeRandomizer(min, null);
    final BigDecimal randomBigDecimal = randomizer.getRandomValue();
    assertThat(randomBigDecimal.doubleValue()).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final BigDecimalRangeRandomizer bigDecimalRangeRandomizer =
        new BigDecimalRangeRandomizer(min, max, SEED);

    // when
    final BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

    then(bigDecimal).isEqualTo(new BigDecimal("7.46393298637489266411648713983595371246337890625"));
  }

  @Test
  void generatedValueShouldHaveProvidedPositiveScale() {
    // given
    final Integer scale = 2;
    final BigDecimalRangeRandomizer bigDecimalRangeRandomizer =
        new BigDecimalRangeRandomizer(min, max, scale);

    // when
    final BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

    then(bigDecimal.scale()).isEqualTo(scale);
  }

  @Test
  void generatedValueShouldHaveProvidedNegativeScale() {
    // given
    final Integer scale = -2;
    final BigDecimalRangeRandomizer bigDecimalRangeRandomizer =
        new BigDecimalRangeRandomizer(min, max, scale);

    // when
    final BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

    then(bigDecimal.scale()).isEqualTo(scale);
  }

  @Test
  void testCustomRoundingMode() {
    // given
    final Integer scale = 2;
    final int seed = 123;
    final RoundingMode roundingMode = RoundingMode.DOWN;
    final BigDecimalRangeRandomizer bigDecimalRangeRandomizer =
        new BigDecimalRangeRandomizer(min, max, seed, scale, roundingMode);

    // when
    final BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

    then(bigDecimal).isEqualTo(new BigDecimal("7.46"));
  }
  //  resume checkstyle: MagicNumberCheck
}
