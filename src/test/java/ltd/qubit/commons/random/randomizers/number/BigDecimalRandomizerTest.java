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
import java.math.RoundingMode;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class BigDecimalRandomizerTest extends AbstractRandomizerTest<BigDecimal> {

  @Test
  void generatedValueShouldHaveProvidedPositiveScale() {
    // given
    final Integer scale = 1;
    final BigDecimalRandomizer bigDecimalRandomizer = new BigDecimalRandomizer(scale);

    // when
    final BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

    then(bigDecimal.scale()).isEqualTo(scale);
  }

  @Test
  void generatedValueShouldHaveProvidedNegativeScale() {
    // given
    final Integer scale = -1;
    final BigDecimalRandomizer bigDecimalRangeRandomizer =
        new BigDecimalRandomizer(scale);

    // when
    final BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

    then(bigDecimal.scale()).isEqualTo(scale);
  }

  @Test
  void testCustomRoundingMode() {
    // given
    final long initialSeed = 123;
    final Integer scale = 1;
    final RoundingMode roundingMode = RoundingMode.DOWN;
    final BigDecimalRandomizer bigDecimalRandomizer =
        new BigDecimalRandomizer(initialSeed, scale, roundingMode);

    // when
    final BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

    then(bigDecimal).isEqualTo(new BigDecimal("0.7"));
  }
}
