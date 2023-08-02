////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigDecimal;
import java.math.BigInteger;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.BDDAssertions.then;

class NumberRandomizersTest extends AbstractRandomizerTest<Object> {

  //  stop checkstyle: MagicNumberCheck
  static Object[] generateRandomizers() {
    return new Object[]{
        new ByteRandomizer(),
        new ShortRandomizer(),
        new IntegerRandomizer(),
        new NumberRandomizer(),
        new LongRandomizer(),
        new FloatRandomizer(),
        new DoubleRandomizer(),
        new BigDecimalRandomizer(),
        new BigIntegerRandomizer(),
    };
  }

  @ParameterizedTest
  @MethodSource("generateRandomizers")
  void generatedNumberShouldNotBeNull(final Randomizer<?> randomizer) {
    // when
    final Object randomNumber = randomizer.getRandomValue();

    then(randomNumber).isNotNull();
  }

  static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
    return new Object[][]{
        {new ByteRandomizer(SEED), (byte) -35},
        {new ShortRandomizer(SEED), (short) -3619},
        {new IntegerRandomizer(SEED), -1188957731},
        {new NumberRandomizer(SEED), -1188957731},
        {new LongRandomizer(SEED), -5106534569952410475L},
        {new FloatRandomizer(SEED), 0.72317415F},
        {new DoubleRandomizer(SEED), 0.7231742029971469},
        {new BigDecimalRandomizer(SEED),
            new BigDecimal(
                "0.723174202997146853277854461339302361011505126953125")},
        {new BigIntegerRandomizer(SEED),
            new BigInteger("295011414634219278107705585431435293517")},
    };
  }

  @ParameterizedTest
  @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
  void shouldGenerateTheSameValueForTheSameSeed(final Randomizer<?> randomizer,
      final Object expected) {
    //when
    final Object actual = randomizer.getRandomValue();

    then(actual).isEqualTo(expected);
  }
  //  resume checkstyle: MagicNumberCheck
}
