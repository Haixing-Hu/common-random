////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class BigIntegerRangeRandomizerTest extends
        AbstractRangeRandomizerTest<BigInteger> {

  //  stop checkstyle: MagicNumberCheck
  private Integer min;
  private Integer max;

  @BeforeEach
  void setUp() {
    min = 1;
    max = 10;
    randomizer = new BigIntegerRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final BigInteger randomValue = randomizer.getRandomValue();
    assertThat(randomValue.intValue()).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new BigIntegerRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new BigIntegerRangeRandomizer(null, max);
    final BigInteger radomBigInteger = randomizer.getRandomValue();
    assertThat(radomBigInteger.intValue()).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new BigIntegerRangeRandomizer(min, null);
    final BigInteger radomBigInteger = randomizer.getRandomValue();
    assertThat(radomBigInteger.intValue()).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final BigIntegerRangeRandomizer randomizer = new BigIntegerRangeRandomizer(min, max, SEED);
    // when
    final BigInteger bigInteger = randomizer.getRandomValue();
    then(bigInteger).isEqualTo(new BigInteger("8"));
  }
  //  resume checkstyle: MagicNumberCheck
}
