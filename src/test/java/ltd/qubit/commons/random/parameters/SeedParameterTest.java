////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SeedParameterTest {

  private static final long SEED = 123L;

  @Test
  void generatedObjectShouldBeAlwaysTheSameForTheSameSeed() {
    final Parameters param1 = new Parameters().seed(SEED);

    final EasyRandom random1 = new EasyRandom(param1);
    final String expectedString = random1.nextObject(String.class);
    final int[] expectedInts = random1.nextObject(int[].class);
    final Person expectedPerson = random1.nextObject(Person.class);

    final Parameters param2 = new Parameters().seed(SEED);
    final EasyRandom random2 = new EasyRandom(param2);
    final String actualString = random2.nextObject(String.class);
    final int[] actualInts = random2.nextObject(int[].class);
    final Person actualPerson = random2.nextObject(Person.class);

    assertThat(actualString).isEqualTo(expectedString);
    assertThat(actualInts).isEqualTo(expectedInts);
    assertThat(actualPerson)
        .usingRecursiveComparison()
        .ignoringExpectedNullFields()
        .isEqualTo(expectedPerson);
  }
}