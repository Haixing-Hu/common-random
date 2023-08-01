////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.util.Date;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.Ape;
import ltd.qubit.commons.random.beans.Bar;
import ltd.qubit.commons.random.beans.ClassUsingAbstractEnum;
import ltd.qubit.commons.random.beans.ComparableBean;
import ltd.qubit.commons.random.beans.ConcreteBar;
import ltd.qubit.commons.random.beans.Foo;
import ltd.qubit.commons.random.beans.Human;
import ltd.qubit.commons.random.beans.Mamals;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.SocialPerson;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class ScanClasspathForConcreteTypesParameterTests {

  private EasyRandom easyRandom;

  @Test
  void scanClasspathForConcreteTypesDisabled_populateInterfacesAndAbstractClasses() {
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(false);
    easyRandom = new EasyRandom(parameters);

    assertThatThrownBy(() -> easyRandom.nextObject(Mamals.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  @Test
  void scanClasspathForConcreteTypesEnabled_populateInterfacesAndAbstractClasses() {
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(true);
    easyRandom = new EasyRandom(parameters);

    final Mamals mamals = easyRandom.nextObject(Mamals.class);

    assertThat(mamals.getMamal())
        .isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    assertThat(mamals.getMamalImpl())
        .isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
  }

  @Test
  void scanClasspathForConcreteTypesEnabled_populateConcreteTypesForFieldsWithGenericParameters() {
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(true);
    easyRandom = new EasyRandom(parameters);

    final ComparableBean comparableBean = easyRandom.nextObject(ComparableBean.class);

    assertThat(comparableBean.getDateComparable())
        .isOfAnyClassIn(ComparableBean.AlwaysEqual.class, Date.class);
  }

  @Test
  void scanClasspathForConcreteTypesEnabled_populateAbstractTypesWithConcreteSubTypes() {
    // Given
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(true);
    easyRandom = new EasyRandom(parameters);

    // When
    final Bar bar = easyRandom.nextObject(Bar.class);

    // Then
    assertThat(bar).isNotNull();
    assertThat(bar).isInstanceOf(ConcreteBar.class);
    // https://github.com/j-easy/easy-random/issues/204
    assertThat(bar.getNumber()).isNotNull();
  }

  @Test
  void scanClasspathForConcreteTypesEnabled_populateFieldsOfAbstractTypeWithConcreteSubTypes() {
    // Given
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(true);
    easyRandom = new EasyRandom(parameters);

    // When
    final Foo foo = easyRandom.nextObject(Foo.class);

    // Then
    assertThat(foo).isNotNull();
    assertThat(foo.getBar()).isInstanceOf(ConcreteBar.class);
    assertThat(foo.getBar().getName()).isNotEmpty();
  }

  @Test
  void scanClasspathForConcreteTypesIsEnabled_populateAbstractEnumeration() {
    final Parameters parameters = new Parameters()
        .scanClasspathForConcreteTypes(true);
    easyRandom = new EasyRandom(parameters);

    final ClassUsingAbstractEnum randomValue =
        easyRandom.nextObject(ClassUsingAbstractEnum.class);

    then(randomValue.getTestEnum()).isNotNull();
  }

  // issue https://github.com/j-easy/easy-random/issues/353

  @Test
  void scanClasspathForConcreteTypes_whenConcreteTypeIsAnInnerClass() {
    final Parameters parameters =
            new Parameters().scanClasspathForConcreteTypes(true);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    final Foobar foobar = easyRandom.nextObject(Foobar.class);

    Assertions.assertThat(foobar).isNotNull();
    Assertions.assertThat(foobar.getToto()).isNotNull();
  }

  public class Foobar {

    public abstract class Toto {
    }

    public class TotoImpl extends Toto {
    }

    private Toto toto;

    public Toto getToto() {
      return toto;
    }
  }

}
