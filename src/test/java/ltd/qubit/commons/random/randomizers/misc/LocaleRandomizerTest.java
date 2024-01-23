////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleRandomizerTest extends AbstractRandomizerTest<Locale> {

  @Test
  void shouldGenerateRandomLocale() {
    assertThat(new LocaleRandomizer().getRandomValue())
        .isIn((Object[]) Locale.getAvailableLocales());
  }

  @Disabled
  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    final BigDecimal javaVersion = new BigDecimal(System.getProperty("java.specification.version"));
    System.out.println("Java version: " + javaVersion);
    if (javaVersion.compareTo(new BigDecimal("17")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("mni"));
    } else if (javaVersion.compareTo(new BigDecimal("15")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "TK"));
    } else if (javaVersion.compareTo(new BigDecimal("14")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("rn", "BI"));
    } else if (javaVersion.compareTo(new BigDecimal("13")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("zh", "CN"));
    } else if (javaVersion.compareTo(new BigDecimal("11")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("en", "CK"));
    } else if (javaVersion.compareTo(new BigDecimal("9")) >= 0) {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("sw", "ke"));
    } else {
      assertThat(new LocaleRandomizer(SEED).getRandomValue()).isEqualTo(new Locale("nl", "be"));
    }
  }
}
