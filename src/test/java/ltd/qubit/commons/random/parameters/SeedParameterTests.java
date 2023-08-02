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
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Gender;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.Street;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeedParameterTests {

  private static final long SEED = 123L;

  @Test
  void generatedObjectShouldBeAlwaysTheSameForTheSameSeed() {
    // Given
    final Parameters parameters = new Parameters().seed(SEED);
    final EasyRandom random = new EasyRandom(parameters);

    final String expectedString = "OMtThyhVNLWUZNRcBaQKxIyedUsFwd";
    final Person expectedPerson = buildExpectedPerson();
    final int[] expectedInts = buildExpectedInts();

    // When
    final String actualString = random.nextObject(String.class);
    final Person actualPerson = random.nextObject(Person.class);
    final int[] actualInts = random.nextObject(int[].class);

    // Then
    assertThat(actualString).isEqualTo(expectedString);
    assertThat(actualPerson).isEqualToIgnoringNullFields(expectedPerson);
    assertThat(actualInts).isEqualTo(expectedInts);
  }

  //  stop checkstyle: MagicNumberCheck
  private Person buildExpectedPerson() {
    final Person expectedPerson = new Person();

    final Street street = new Street();
    street.setName("vaScf");
    street.setNumber(-1188957731);
    street.setType((byte) -35);

    final Address address = new Address();
    address.setCity("Jxkyv");
    address.setCountry("nLRYtGKbgicZaHCBRQDSxVLhpfQG");
    address.setZipCode("IOOma");
    address.setStreet(street);

    expectedPerson.setName("UBCL");
    expectedPerson.setEmail("elQbxeTeQ");
    expectedPerson.setPhoneNumber("MDYpsBZx");
    expectedPerson.setGender(Gender.FEMALE);
    expectedPerson.setAddress(address);

    return expectedPerson;
  }

  private int[] buildExpectedInts() {
    return new int[]{
        -535098017, -1935747844
    };
  }
  //  resume checkstyle: MagicNumberCheck
}
