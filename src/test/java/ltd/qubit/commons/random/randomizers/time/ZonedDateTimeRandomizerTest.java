////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.ZonedDateTime;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ZonedDateTimeRandomizerTest extends
        AbstractRandomizerTest<ZonedDateTime> {

  @Test
  void generatedValueShouldNotBeNull() {
    // given
    final ZonedDateTimeRandomizer zonedDateTimeRandomizer = new ZonedDateTimeRandomizer();

    // when
    final ZonedDateTime zonedDateTime = zonedDateTimeRandomizer.getRandomValue();

    then(zonedDateTime).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // given
    final ZonedDateTimeRandomizer firstSeededZonedDateTimeRandomizer =
        new ZonedDateTimeRandomizer(SEED);
    final ZonedDateTimeRandomizer secondSeededZonedDateTimeRandomizer =
        new ZonedDateTimeRandomizer(SEED);

    // when
    // we can not use a canned value, because the available TimeZones
    // differ between locales/jdks
    final ZonedDateTime firstZonedTimeDate = firstSeededZonedDateTimeRandomizer.getRandomValue();
    final ZonedDateTime secondZonedTimeDate = secondSeededZonedDateTimeRandomizer.getRandomValue();

    then(firstZonedTimeDate).isNotNull();
    then(secondZonedTimeDate).isNotNull();
    then(firstZonedTimeDate).isEqualTo(secondZonedTimeDate);
  }
}
