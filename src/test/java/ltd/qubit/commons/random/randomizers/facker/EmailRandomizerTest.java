////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.facker;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ltd.qubit.commons.random.randomizers.faker.EmailRandomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailRandomizerTest {

  @Test
  void whenSafeModeIsEnabled_thenDomainNameShouldBeInvalid() {
    // given
    final EmailRandomizer emailRandomizer = new EmailRandomizer(123L, Locale.ENGLISH, true);

    // when
    final String randomValue = emailRandomizer.getRandomValue();

    // then
    // example.com is the only domain in faker's data file with 'en' locale
    // (see en:faker:internet:safe_email: in en.yml)
    assertThat(randomValue).contains("@example.com");
  }

  @Test
  void whenSafeModeIsDisabled_thenDomainNameShouldBeValid() {
    // given
    // see en:faker:internet:free_email: in faker's en.yml
    final List<String> expectedDomains = Arrays.asList("gmail.com", "yahoo.com", "hotmail.com");
    final EmailRandomizer emailRandomizer = new EmailRandomizer(123L, Locale.ENGLISH, false);

    // when
    final String randomValue = emailRandomizer.getRandomValue();
    final String actualDomain = randomValue.substring(randomValue.lastIndexOf("@") + 1);

    // then
    assertThat(expectedDomains).contains(actualDomain);
  }

}
