////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NullRandomizerTest {

  @Test
  void generatedValueShouldBeNull() {
    assertThat(new NullRandomizer().getRandomValue()).isNull();
  }

}
