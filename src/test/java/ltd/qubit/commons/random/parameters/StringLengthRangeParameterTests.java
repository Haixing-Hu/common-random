////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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

class StringLengthRangeParameterTests {

  @Test
  void testStringLengthRange() {
    // Given
    final int minStringLength = 3;
    final int maxStringLength = 50;
    final Parameters parameters = new Parameters()
        .stringLengthRange(minStringLength, maxStringLength);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // When
    final Person person = easyRandom.nextObject(Person.class);

    // Then
    assertThat(person.getName()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getEmail()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getPhoneNumber()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getAddress()
                     .getCity()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getAddress()
                     .getCountry()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getAddress()
                     .getZipCode()
                     .length()).isBetween(minStringLength, maxStringLength);
    assertThat(person.getAddress()
                     .getStreet()
                     .getName()
                     .length()).isBetween(minStringLength, maxStringLength);
  }

}
