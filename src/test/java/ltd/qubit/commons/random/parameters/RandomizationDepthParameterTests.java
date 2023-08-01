////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.Person;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomizationDepthParameterTests {

  @Test
  void testRandomizationDepth() {
    // Given
    final Parameters parameters = new Parameters().randomizationDepth(2);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // When
    final Person person = easyRandom.nextObject(Person.class);

    // Then
    assertThat(person).isNotNull();
    assertThat(person.getParent()).isNotNull();
    assertThat(person.getParent().getParent()).isNotNull();
    assertThat(person.getParent().getParent().getParent()).isNull();
  }

}
