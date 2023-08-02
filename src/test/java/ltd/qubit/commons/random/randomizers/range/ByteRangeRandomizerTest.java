////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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

class ByteRangeRandomizerTest extends AbstractRangeRandomizerTest<Byte> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = (byte) 1;
    max = (byte) 10;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new ByteRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Byte randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new ByteRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new ByteRangeRandomizer(null, max);
    final Byte randomByte = randomizer.getRandomValue();
    assertThat(randomByte).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new ByteRangeRandomizer(min, null);
    final Byte randomByte = randomizer.getRandomValue();
    assertThat(randomByte).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final ByteRangeRandomizer byteRangeRandomizer = new ByteRangeRandomizer(min, max, SEED);
    // when
    final Byte b = byteRangeRandomizer.getRandomValue();
    // stop checkstyle: MagicNumberCheck
    then(b).isEqualTo((byte) 8);
    // resume checkstyle: MagicNumberCheck
  }
}
