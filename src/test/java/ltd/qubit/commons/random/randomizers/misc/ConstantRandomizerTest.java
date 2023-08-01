////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConstantRandomizerTest {

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void shouldAlwaysGenerateTheSameValue() {
    assertThat(new ConstantRandomizer("a").getRandomValue()).isEqualTo("a");
  }
}
