////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.util.concurrent.atomic.AtomicInteger;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class AtomicIntegerRandomizerTest extends
        AbstractRandomizerTest<AtomicInteger> {

  @Test
  void generateValueShouldNotBeNull() {
    // given
    final AtomicIntegerRandomizer atomicIntegerRandomizer = new AtomicIntegerRandomizer();

    // when
    final AtomicInteger atomicInteger = atomicIntegerRandomizer.getRandomValue();

    then(atomicInteger).isNotNull();
  }

  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    // given
    final AtomicIntegerRandomizer atomicIntegerRandomizer = new AtomicIntegerRandomizer(SEED);

    // when
    final AtomicInteger atomicInteger = atomicIntegerRandomizer.getRandomValue();

    //  stop checkstyle: MagicNumberCheck
    then(atomicInteger).hasValue(-1188957731);
    //  resume checkstyle: MagicNumberCheck
  }
}
