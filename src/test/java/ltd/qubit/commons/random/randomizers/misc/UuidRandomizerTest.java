////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class UuidRandomizerTest extends AbstractRandomizerTest<Locale> {

  @Test
  void shouldGenerateRandomUuid() {
    assertThat(new UuidRandomizer().getRandomValue()).isNotNull();
  }

  //  stop checkstyle: MagicNumberCheck
  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    assertThat(new UuidRandomizer(SEED).getRandomValue())
        .isEqualTo(new UUID(-5106534569952410475L, -167885730524958550L));
  }
  //  resume checkstyle: MagicNumberCheck
}
