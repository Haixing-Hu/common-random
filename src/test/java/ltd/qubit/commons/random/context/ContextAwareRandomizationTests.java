////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.FieldPredicates.inClass;
import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

import static org.assertj.core.api.Assertions.assertThat;

class ContextAwareRandomizationTests {

  @Test
  void testContextAwareRandomization() {
    // given
    final String[] names = {"james", "daniel"};
    final Parameters parameters = new Parameters()
            .randomize(named("firstName").and(ofType(String.class))
                                         .and(inClass(Person.class)),
                new FirstNameRandomizer(names))
            .randomize(named("lastName").and(ofType(String.class))
                                        .and(inClass(Person.class)),
                new LastNameRandomizer())
            .excludeField(named("nickname"));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Person person = easyRandom.nextObject(Person.class);

    // then
    final String firstName = person.getFirstName();
    final String lastName = person.getLastName();
    assertThat(firstName).isIn((Object[]) names);
    assertThat(lastName).isNotNull();
    if (firstName.equalsIgnoreCase("james")) {
      assertThat(lastName.equalsIgnoreCase("bond"));
    }
    if (firstName.equalsIgnoreCase("daniel")) {
      assertThat(lastName.equalsIgnoreCase("craig"));
    }
    assertThat(person.getNickname()).isNull();
  }

  @Test
  void testContextAwareRandomizerWithMultipleTypes() {
    // given
    final String[] names = {"james", "daniel"};
    final String[] countries = {"france", "germany", "belgium"};
    final Parameters parameters = new Parameters()
            .randomize(named("firstName").and(ofType(String.class)),
                new FirstNameRandomizer(names))
            .randomize(named("lastName").and(ofType(String.class)),
                new LastNameRandomizer())
            .randomize(ofType(Country.class), new CountryRandomizer(countries))
            .randomize(ofType(City.class), new CityRandomizer())
            .excludeField(named("nickname"));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Person person = easyRandom.nextObject(Person.class);

    // then
    if (person.getFirstName().equalsIgnoreCase("james")) {
      assertThat(person.getLastName().equalsIgnoreCase("bond"));
    }
    if (person.getFirstName().equalsIgnoreCase("daniel")) {
      assertThat(person.getLastName().equalsIgnoreCase("craig"));
    }
    assertThat(person.getNickname()).isNull();

    final Pet pet = person.getPet();
    if (pet.getFirstName().equalsIgnoreCase("james")) {
      assertThat(pet.getLastName().equalsIgnoreCase("bond"));
    }
    if (pet.getFirstName().equalsIgnoreCase("daniel")) {
      assertThat(pet.getLastName().equalsIgnoreCase("craig"));
    }

    final Country country = person.getCountry();
    final City city = person.getCity();

    assertThat(country).isNotNull();
    if (country.getName().equalsIgnoreCase("france")) {
      assertThat(city.getName().equalsIgnoreCase("paris"));
    }
    if (country.getName().equalsIgnoreCase("germany")) {
      assertThat(city.getName().equalsIgnoreCase("berlin"));
    }
    if (country.getName().equalsIgnoreCase("belgium")) {
      assertThat(city.getName().equalsIgnoreCase("brussels"));
    }

  }
}
