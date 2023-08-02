////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.facker;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;
import ltd.qubit.commons.random.randomizers.faker.GenericStringRandomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

import static org.assertj.core.api.BDDAssertions.then;

class GenericStringRandomizerTest extends AbstractRandomizerTest<String> {

  private String[] words;

  @BeforeEach
  void setUp() {
    words = new String[]{"foo", "bar"};
  }

  @Test
  void randomValueShouldBeGeneratedFromTheGivenWords() {
    // given
    randomizer = new GenericStringRandomizer(words);

    // when
    final String randomWord = randomizer.getRandomValue();

    then(randomWord).isIn(asList(words));
  }

  @Test
  void randomValueShouldBeAlwaysTheSameForTheSameSeed() {
    // given
    randomizer = new GenericStringRandomizer(words, SEED);

    // when
    final String randomWord = randomizer.getRandomValue();

    then(randomWord).isEqualTo("bar");
  }
}
