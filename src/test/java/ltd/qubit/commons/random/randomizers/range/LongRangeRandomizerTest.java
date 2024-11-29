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

public class LongRangeRandomizerTest extends AbstractRangeRandomizerTest<Long> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = 1L;
    max = 10L;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new LongRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Long randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new LongRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new LongRangeRandomizer(null, max);
    final Long randomLong = randomizer.getRandomValue();
    assertThat(randomLong).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new LongRangeRandomizer(min, null);
    final Long randomLong = randomizer.getRandomValue();
    assertThat(randomLong).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final LongRangeRandomizer longRangeRandomizer = new LongRangeRandomizer(min, max, SEED);
    // when
    final Long l = longRangeRandomizer.getRandomValue();
    // stop checkstyle: MagicNumberCheck
    then(l).isEqualTo(8L);
    // resume checkstyle: MagicNumberCheck
  }
}
