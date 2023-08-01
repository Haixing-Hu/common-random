////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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

class FloatRangeRandomizerTest extends AbstractRangeRandomizerTest<Float> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = 0.001f;
    max = 0.002f;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new FloatRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Float randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new FloatRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new FloatRangeRandomizer(null, max);
    final Float randomFloat = randomizer.getRandomValue();
    assertThat(randomFloat).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new FloatRangeRandomizer(min, null);
    final Float randomFloat = randomizer.getRandomValue();
    assertThat(randomFloat).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final FloatRangeRandomizer floatRangeRandomizer = new FloatRangeRandomizer(min, max, SEED);

    // when
    final Float f = floatRangeRandomizer.getRandomValue();

    //  stop checkstyle: MagicNumberCheck
    then(f).isEqualTo(0.0017231742f);
    //  resume checkstyle: MagicNumberCheck
  }
}
