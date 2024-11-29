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

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleRandomizerTest extends AbstractRandomizerTest<Locale> {

  @Test
  void shouldGenerateRandomLocale() {
    assertThat(new LocaleRandomizer().getRandomValue())
        .isIn((Object[]) Locale.getAvailableLocales());
  }
}
