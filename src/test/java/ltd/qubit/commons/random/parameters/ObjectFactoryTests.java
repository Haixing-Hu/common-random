////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.Street;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectFactoryTests {

  @Test
  void testCustomObjectFactory() {
    // given
    final Parameters parameters = new Parameters()
        .objectFactory(new ObjectFactory() {
          @SuppressWarnings("unchecked")
          @Override
          public <T> T createInstance(final Class<T> type,
              final Context context) throws ObjectCreationException {
            try {
              // use custom logic for a specific type
              if (type.isAssignableFrom(Address.class)) {
                final Address address = new Address();
                address.setCity("Brussels");
                address.setCountry("Belgium");
                address.setZipCode("1000");

                final Street street = new Street();
                street.setName("main street");
                street.setNumber(1);
                street.setType((byte) 1);
                address.setStreet(street);
                return (T) address;
              }
              // use regular constructor for other types
              return type.getDeclaredConstructor().newInstance();
            } catch (final Exception e) {
              throw new ObjectCreationException(
                  "Unable to create a new instance of " + type, e);
            }
          }
        });
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Person person = easyRandom.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getId())
        .isNotNull();  // should populate final field without default value
    assertThat(person.getName()).isNotNull();
    assertThat(person.getGender()).isNotNull();
    assertThat(person.getEmail()).isNotNull();
    assertThat(person.getPhoneNumber()).isNotNull();
    assertThat(person.getBirthDate()).isNotNull();
    assertThat(person.getNicknames()).isNotNull();
    assertThat(person.getExcluded()).isNull();

    final Address address = person.getAddress();
    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isEqualTo("Belgium");
    assertThat(address.getCity()).isEqualTo("Brussels");
    assertThat(address.getZipCode()).isEqualTo("1000");
    final Street street = address.getStreet();
    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("main street");
    assertThat(street.getNumber()).isEqualTo(1);
    assertThat(street.getType()).isEqualTo((byte) 1);
  }
}
