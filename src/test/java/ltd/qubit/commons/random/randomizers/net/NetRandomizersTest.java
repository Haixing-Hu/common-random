////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.net;

import java.net.URI;
import java.net.URL;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.beans.Website;
import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.BDDAssertions.then;

class NetRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

  static Object[] generateRandomizers() {
    return new Object[]{
        new UriRandomizer(),
        new UrlRandomizer()
    };
  }

  @ParameterizedTest
  @MethodSource("generateRandomizers")
  void generatedValueShouldNotBeNull(final Randomizer<?> randomizer) {
    // when
    final Object value = randomizer.getRandomValue();

    then(value).isNotNull();
  }

  static Object[][] generateSeededRandomizersAndTheirExpectedValues()
      throws Exception {
    return new Object[][]{
        {new UriRandomizer(SEED), new URI("telnet://192.0.2.16:80/")},
        {new UrlRandomizer(SEED), new URL("http://www.google.com")}
    };
  }

  @ParameterizedTest
  @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
  void shouldGenerateTheSameValueForTheSameSeed(final Randomizer<?> randomizer,
      final Object expected) {
    //when
    final Object actual = randomizer.getRandomValue();

    then(actual).isEqualTo(expected);
  }

  @Test
  void javaNetTypesShouldBePopulated() {
    // when
    final Website website = new EasyRandom().nextObject(Website.class);

    then(website).hasNoNullFieldsOrProperties();
  }
}
