////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class DoubleRangeRandomizerTest extends AbstractRangeRandomizerTest<Double> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = 1d;
    max = 10d;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new DoubleRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Double randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new DoubleRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new DoubleRangeRandomizer(null, max);
    final Double randomDouble = randomizer.getRandomValue();
    assertThat(randomDouble).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new DoubleRangeRandomizer(min, null);
    final Double randomDouble = randomizer.getRandomValue();
    assertThat(randomDouble).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final DoubleRangeRandomizer doubleRangeRandomizer =
        new DoubleRangeRandomizer(min, max, SEED);

    // when
    final Double d = doubleRangeRandomizer.getRandomValue();
    // stop checkstyle: MagicNumberCheck
    then(d).isEqualTo(7.508567826974321);
    // resume checkstyle: MagicNumberCheck
  }

  /* This test is for the first comment on https://stackoverflow.com/a/3680648/5019386. This test
   * never fails (tested with IntelliJ's feature "Repeat Test Until Failure") */
  /*
    @Test
    void testInfinity() {
        // given
        DoubleRangeRandomizer doubleRangeRandomizer =
        new DoubleRangeRandomizer(-Double.MAX_VALUE, Double.MAX_VALUE);

        // when
        Double d = doubleRangeRandomizer.getRandomValue();

        then(d).isNotNull();
    }
   */
}
