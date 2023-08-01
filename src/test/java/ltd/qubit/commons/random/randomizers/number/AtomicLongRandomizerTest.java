////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicLong;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class AtomicLongRandomizerTest extends AbstractRandomizerTest<AtomicLong> {

  @Test
  void generateValueShouldNotBeNull() {
    // given
    final AtomicLongRandomizer atomicLongRandomizer = new AtomicLongRandomizer();

    // when
    final AtomicLong atomicLong = atomicLongRandomizer.getRandomValue();

    then(atomicLong).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // given
    final AtomicLongRandomizer atomicLongRandomizer = new AtomicLongRandomizer(SEED);

    // when
    final AtomicLong atomicLong = atomicLongRandomizer.getRandomValue();

    //  stop checkstyle: MagicNumberCheck
    then(atomicLong).hasValue(-5106534569952410475L);
    //  resume checkstyle: MagicNumberCheck
  }
}
