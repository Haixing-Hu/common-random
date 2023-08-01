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
import ltd.qubit.commons.random.beans.PersonTuple;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaxObjectPoolSizeTests {

  @Test
  void testMaxObjectPoolSize() {
    // Given
    final Parameters parameters = new Parameters().objectPoolSize(1);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // When
    final PersonTuple persons = easyRandom.nextObject(PersonTuple.class);

    // Then
    assertThat(persons.left).isSameAs(persons.right);
  }

}
