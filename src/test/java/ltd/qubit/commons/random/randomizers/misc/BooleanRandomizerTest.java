////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

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
