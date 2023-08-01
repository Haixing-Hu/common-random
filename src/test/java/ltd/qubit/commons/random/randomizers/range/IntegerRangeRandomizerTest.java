////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.Street;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class IntegerRangeRandomizerTest extends AbstractRangeRandomizerTest<Integer> {

  @BeforeEach
  void setUp() {
    //  stop checkstyle: MagicNumberCheck
    min = 1;
    max = 10;
    //  resume checkstyle: MagicNumberCheck
    randomizer = new IntegerRangeRandomizer(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange() {
    final Integer randomValue = randomizer.getRandomValue();
    assertThat(randomValue).isBetween(min, max);
  }

  @Test
  void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
    assertThatThrownBy(() -> new IntegerRangeRandomizer(max, min))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
    randomizer = new IntegerRangeRandomizer(null, max);
    final Integer randomInteger = randomizer.getRandomValue();
    assertThat(randomInteger).isLessThanOrEqualTo(max);
  }

  @Test
  void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
    randomizer = new IntegerRangeRandomizer(min, null);
    final Integer randomInteger = randomizer.getRandomValue();
    assertThat(randomInteger).isGreaterThanOrEqualTo(min);
  }

  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    // given
    final IntegerRangeRandomizer integerRangeRandomizer =
        new IntegerRangeRandomizer(min, max, SEED);
    // when
    final Integer i = integerRangeRandomizer.getRandomValue();
    //  stop checkstyle: MagicNumberCheck
    then(i).isEqualTo(8);
    //  resume checkstyle: MagicNumberCheck
  }

  /*
   * Integration tests
   */
  @Test
  void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizePrimitiveIntegerType() {
    final Parameters parameters = new Parameters()
            .randomize(int.class, new IntegerRangeRandomizer(min, max));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    final int integer = easyRandom.nextObject(int.class);
    assertThat(integer).isBetween(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeWrapperIntegerType() {
    final Parameters parameters = new Parameters()
        .randomize(Integer.class, new IntegerRangeRandomizer(min, max));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    final Integer integer = easyRandom.nextObject(Integer.class);
    assertThat(integer).isBetween(min, max);
  }

  @Test
  void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeNonIntegerType() {
    final Parameters parameters = new Parameters()
        .randomize(Integer.class, new IntegerRangeRandomizer(min, max));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    final Street street = easyRandom.nextObject(Street.class);
    assertThat(street.getNumber()).isBetween(min, max);
  }

}
