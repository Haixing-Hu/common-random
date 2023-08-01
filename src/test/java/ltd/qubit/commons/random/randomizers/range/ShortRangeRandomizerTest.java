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

class ShortRangeRandomizerTest extends AbstractRangeRandomizerTest<Short> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = (short) 1;
    max = (short) 10;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new ShortRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Short randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new ShortRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new ShortRangeRandomizer(null, max);
    final Short randomShort = randomizer.getRandomValue();
    assertThat(randomShort).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new ShortRangeRandomizer(min, null);
    final Short randomShort = randomizer.getRandomValue();
    assertThat(randomShort).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final ShortRangeRandomizer shortRangeRandomizer =
        new ShortRangeRandomizer(min, max, SEED);
    // when
    final Short s = shortRangeRandomizer.getRandomValue();
    // stop checkstyle: MagicNumberCheck
    then(s).isEqualTo((short) 8);
    // resume checkstyle: MagicNumberCheck
  }
}
