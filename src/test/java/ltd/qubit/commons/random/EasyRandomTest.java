////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.beans.AbstractBean;
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Gender;
import ltd.qubit.commons.random.beans.Human;
import ltd.qubit.commons.random.beans.ImmutableBean;
import ltd.qubit.commons.random.beans.Node;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.Salary;
import ltd.qubit.commons.random.beans.Street;
import ltd.qubit.commons.random.beans.TestBean;
import ltd.qubit.commons.random.beans.TestData;
import ltd.qubit.commons.random.beans.TestEnum;
import ltd.qubit.commons.random.util.ReflectionUtils;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;

import static ltd.qubit.commons.random.FieldPredicates.hasModifiers;
import static ltd.qubit.commons.random.FieldPredicates.inClass;
import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class EasyRandomTest {

  private static final String FOO = "foo";

  @Mock
  private Randomizer<String> randomizer = () -> FOO;

  private EasyRandom random;

  @BeforeEach
  void setUp() {
    random = new EasyRandom();
  }

  @Test
  void generatedBeansShouldBeCorrectlyPopulated() {
    final Person person = random.nextObject(Person.class);
    validatePerson(person);
  }

  @Test
  void shouldFailIfSetterInvocationFails() {
    final Throwable thrown = catchThrowable(() -> random.nextObject(Salary.class));

    assertThat(thrown).isInstanceOf(ObjectCreationException.class)
                      .hasMessageContaining("Unable to create a random instance "
                          + "of type class ltd.qubit.commons.random.beans.Salary");

    final Throwable cause = thrown.getCause();
    assertThat(cause).isInstanceOf(ObjectCreationException.class)
                     .hasMessageContaining("Unable to invoke setter for field amount "
                         + "of class ltd.qubit.commons.random.beans.Salary");

    final Throwable rootCause = cause.getCause();
    assertThat(rootCause).isInstanceOf(IllegalArgumentException.class)
                         .hasMessageContaining("Amount must be positive");
  }

  @Test
  void finalFieldsWithoutDefaultValueShouldBePopulated() {
    final Person person = random.nextObject(Person.class);
    assertThat(person).isNotNull();
    assertThat(person.getId()).isNotNull();
  }

  @Test
  void staticFieldsShouldNotBePopulated() {
    try {
      final Human human = random.nextObject(Human.class);
      assertThat(human).isNotNull();
    } catch (final Exception e) {
      fail("Should be able to populate types with private static final fields.", e);
    }
  }

  @Test
  void immutableBeansShouldBePopulated() {
    final ImmutableBean immutableBean = random.nextObject(ImmutableBean.class);
    assertThat(immutableBean).hasNoNullFieldsOrProperties();
  }

  @Test
  void generatedBeansNumberShouldBeEqualToSpecifiedNumber() {
    final Stream<Person> persons = random.objects(Person.class, 2);

    assertThat(persons).hasSize(2).hasOnlyElementsOfType(Person.class);
  }

  @Test
  void customRandomzierForFieldsShouldBeUsedToPopulateObjects() {
    randomizer = () -> FOO;
    final Parameters parameters = new Parameters()
            .randomize(named("name")
                    .and(ofType(String.class))
                    .and(inClass(Human.class)), randomizer);
    random = new EasyRandom(parameters);
    final Person person = random.nextObject(Person.class);
    assertThat(person).isNotNull();
    assertThat(person.getName()).isEqualTo(FOO);
  }

  @Test
  void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithOneModifier() {
    // Given
    randomizer = () -> FOO;
    final Parameters parameters = new Parameters()
            .randomize(hasModifiers(Modifier.TRANSIENT).and(ofType(String.class)), randomizer);
    random = new EasyRandom(parameters);
    // When
    final Person person = random.nextObject(Person.class);
    // Then
    assertThat(person.getEmail()).isEqualTo(FOO);
    assertThat(person.getName()).isNotEqualTo(FOO);
  }

  @Test
  void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithMultipleModifier() {
    // Given
    randomizer = () -> FOO;
    final int modifiers = Modifier.TRANSIENT | Modifier.PROTECTED;
    final Parameters parameters = new Parameters()
            .randomize(hasModifiers(modifiers).and(ofType(String.class)), randomizer);
    random = new EasyRandom(parameters);
    // When
    final Person person = random.nextObject(Person.class);
    // Then
    assertThat(person.getEmail()).isEqualTo(FOO);
    assertThat(person.getName()).isNotEqualTo(FOO);
  }

  @Test
  void customRandomzierForTypesShouldBeUsedToPopulateObjects() {
    randomizer = () -> FOO;
    final Parameters parameters = new Parameters()
            .randomize(String.class, randomizer);
    random = new EasyRandom(parameters);

    final String string = random.nextObject(String.class);

    assertThat(string).isEqualTo(FOO);
  }

  @Test
  void customRandomzierForTypesShouldBeUsedToPopulateFields() {
    randomizer = () -> FOO;
    final Parameters parameters = new Parameters()
        .randomize(String.class, randomizer);
    random = new EasyRandom(parameters);
    final Human human = random.nextObject(Human.class);
    assertThat(human.getName()).isEqualTo(FOO);
  }

  @Test
  void whenSpecifiedNumberOfBeansToGenerateIsNegative_thenShouldThrowAnIllegalArgumentException() {
    assertThatThrownBy(() -> random.objects(Person.class, -2))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void whenUnableToInstantiateField_thenShouldThrowObjectGenerationException() {
    assertThatThrownBy(() -> random.nextObject(AbstractBean.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  @Test
  void beansWithRecursiveStructureMustNotCauseStackOverflowException() {
    final Node node = random.nextObject(Node.class);
    assertThat(node).hasNoNullFieldsOrProperties();
  }

  @Test
  void objectTypeMustBeCorrectlyPopulated() {
    final Object object = random.nextObject(Object.class);

    assertThat(object).isNotNull();
  }

  @Test
  void annotatedRandomizerArgumentsShouldBeCorrectlyParsed() {
    final TestData data = random.nextObject(TestData.class);

    then(data.getDate()).isBetween(valueOf(of(2016, 1, 10, 0, 0, 0)),
        valueOf(of(2016, 1, 30, 23, 59, 59)));
    then(data.getPrice()).isBetween(200, 500);
  }

  @Test
  void nextEnumShouldNotAlwaysReturnTheSameValue() {
    final HashSet<TestEnum> distinctEnumBeans = new HashSet<>();
    for (int i = 0; i < 10; i++) {
      distinctEnumBeans.add(random.nextObject(TestEnum.class));
    }

    assertThat(distinctEnumBeans.size()).isGreaterThan(1);
  }

  @Test
  void fieldsOfTypeClassShouldBeSkipped() {
    try {
      final TestBean testBean = random.nextObject(TestBean.class);
      assertThat(testBean.getException()).isNotNull();
      assertThat(testBean.getClazz()).isNull();
    } catch (final Exception e) {
      fail("Should skip fields of type Class");
    }
  }

  @Test
  void differentCollectionsShouldBeRandomizedWithDifferentSizes() {
    // given
    class Foo {
      List<String> names;
      List<String> addresses;
    }

    // when
    final Foo foo = new EasyRandom().nextObject(Foo.class);

    // then
    assertThat(foo.names.size()).isNotEqualTo(foo.addresses.size());
  }

  @Test
  void differentArraysShouldBeRandomizedWithDifferentSizes() {
    // given
    class Foo {
      String[] names;
      String[] addresses;
    }

    // when
    final EasyRandom random = new EasyRandom();
    final Foo foo = random.nextObject(Foo.class);
    // then
    final Parameters parameters = random.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    assertThat(foo.names).isNotNull();
    assertThat(foo.names.length).isGreaterThanOrEqualTo(range.getMin())
                                .isLessThanOrEqualTo(range.getMax());
    assertThat(foo.addresses).isNotNull();
    assertThat(foo.addresses.length).isGreaterThanOrEqualTo(range.getMin())
                                    .isLessThanOrEqualTo(range.getMax());
  }

  private void validatePerson(final Person person) {
    assertThat(person).isNotNull();
    assertThat(person.getEmail()).isNotEmpty();
    assertThat(person.getGender()).isIn(Arrays.asList(Gender.values()));
    assertThat(person.getBirthDate()).isNotNull();
    assertThat(person.getPhoneNumber()).isNotEmpty();
    assertThat(person.getNicknames()).isNotNull();
    assertThat(person.getName()).isNotEmpty();

    final Address address = person.getAddress();
    assertThat(address).isNotNull();
    assertThat(address.getCity()).isNotEmpty();
    assertThat(address.getCountry()).isNotEmpty();
    assertThat(address.getZipCode()).isNotEmpty();

    final Street street = address.getStreet();
    assertThat(street).isNotNull();
    assertThat(street.getName()).isNotEmpty();
    assertThat(street.getNumber()).isNotNull();
    assertThat(street.getType()).isNotNull();
  }

  void validatePersons(final Collection<Person> persons,
          final int expectedSize) {
    assertThat(persons).hasSize(expectedSize);
    persons.forEach(this::validatePerson);
  }

  @Disabled("Dummy test to see possible reasons of randomization failures")
  @Test
  void tryToRandomizeAllPublicConcreteTypesInTheClasspath() {
    int success = 0;
    int failure = 0;
    final List<Class<?>> publicConcreteTypes =
        ReflectionUtils.getPublicConcreteSubTypesOf(Object.class);
    System.out.println("Found " + publicConcreteTypes.size()
        + " public concrete types in the classpath");
    for (final Class<?> aClass : publicConcreteTypes) {
      try {
        random.nextObject(aClass);
        System.out.println(aClass.getName() + " has been successfully randomized");
        success++;
      } catch (final Throwable e) {
        System.err.println("Unable to populate a random instance of type: " + aClass
                .getName());
        e.printStackTrace();
        System.err.println("----------------------------------------------");
        failure++;
      }
    }
    System.out.println("Success: " + success);
    System.out.println("Failure: " + failure);
  }

  @Test
  void generateArray() {
    final Parameters parameters = random.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final String[] r1 = random.nextObject(String[].class);
    assertThat(r1).isNotNull();
    assertThat(r1.length).isGreaterThanOrEqualTo(range.getMin())
                         .isLessThanOrEqualTo(range.getMax());

    final int[] r2 = random.nextObject(int[].class);
    assertThat(r2).isNotNull();
    assertThat(r2.length).isGreaterThanOrEqualTo(range.getMin())
                         .isLessThanOrEqualTo(range.getMax());

    final boolean[] r3 = random.nextObject(boolean[].class);
    assertThat(r3).isNotNull();
    assertThat(r3.length).isGreaterThanOrEqualTo(range.getMin())
                         .isLessThanOrEqualTo(range.getMax());
  }
}
