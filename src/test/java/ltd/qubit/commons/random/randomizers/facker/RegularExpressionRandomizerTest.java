////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.facker;

import ltd.qubit.commons.random.randomizers.faker.RegularExpressionRandomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class RegularExpressionRandomizerTest {

  @Test
  void leadingBoundaryMatcherIsRemoved() {
    //given
    final RegularExpressionRandomizer randomizer = new RegularExpressionRandomizer("^A");

    //when
    final String actual = randomizer.getRandomValue();

    then(actual).isEqualTo("A");
  }

  @Test
  void tailingBoundaryMatcherIsRemoved() {
    //given
    final RegularExpressionRandomizer randomizer = new RegularExpressionRandomizer("A$");

    //when
    final String actual = randomizer.getRandomValue();

    then(actual).isEqualTo("A");
  }

  @Test
  void leadingAndTailingBoundaryMatcherIsRemoved() {
    //given
    final RegularExpressionRandomizer randomizer = new RegularExpressionRandomizer("^A$");

    //when
    final String actual = randomizer.getRandomValue();

    then(actual).isEqualTo("A");
  }
}
