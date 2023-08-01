////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.lang.reflect.Field;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ExclusionPolicy;
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Person;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExclusionPolicyTests {

  @Test
  void testCustomExclusionPolicy() {
    // given
    final Parameters parameters = new Parameters()
            .exclusionPolicy(new ExclusionPolicy() {
              @Override
              public boolean shouldBeExcluded(final Field field, final Context context) {
                return field.getName().equals("birthDate");
              }

              @Override
              public boolean shouldBeExcluded(final Class<?> type, final Context context) {
                return type.isAssignableFrom(Address.class);
              }
            });
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Person person = easyRandom.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getName()).isNotNull();
    assertThat(person.getEmail()).isNotNull();
    assertThat(person.getPhoneNumber()).isNotNull();

    assertThat(person.getBirthDate()).isNull();
    assertThat(person.getAddress()).isNull();
  }
}
