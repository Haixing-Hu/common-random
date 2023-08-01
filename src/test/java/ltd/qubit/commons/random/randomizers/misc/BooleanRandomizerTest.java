////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanRandomizerTest extends AbstractRandomizerTest<Boolean> {

  @Test
  void generatedBooleanShouldNotBeNull() {
    assertThat(new BooleanRandomizer().getRandomValue()).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    assertThat(new BooleanRandomizer(SEED).getRandomValue()).isTrue();
  }
}
