////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Human;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.Street;
import ltd.qubit.commons.random.beans.Website;
import ltd.qubit.commons.random.beans.exclusion.A;
import ltd.qubit.commons.random.beans.exclusion.B;
import ltd.qubit.commons.random.beans.exclusion.C;

import static org.assertj.core.api.Assertions.assertThat;

import static ltd.qubit.commons.random.FieldPredicates.hasModifiers;
import static ltd.qubit.commons.random.FieldPredicates.inClass;
import static ltd.qubit.commons.random.FieldPredicates.isAnnotatedWith;
import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

@ExtendWith(MockitoExtension.class)
class FieldExclusionTest {

  private EasyRandom random;

  @BeforeEach
  void setUp() {
    random = new EasyRandom();
  }

  @Test
  void excludedFieldsShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(named("name"));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    //then
    assertThat(person).isNotNull();
    assertThat(person.getName()).isNull();
  }

  @Test
  void excludedFieldsUsingSkipRandomizerShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(named("name").and(ofType(String.class))
                                   .and(inClass(Human.class)));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getName()).isNull();
  }

  @Test
  void excludedFieldsUsingFieldDefinitionShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters().excludeField(named("name"));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getAddress()).isNotNull();
    assertThat(person.getAddress().getStreet()).isNotNull();

    // person.name and street.name should be null
    assertThat(person.getName()).isNull();
    assertThat(person.getAddress().getStreet().getName()).isNull();
  }

  @Test
  void excludedDottedFieldsShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(named("name").and(inClass(Street.class)));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getAddress()).isNotNull();
    assertThat(person.getAddress().getStreet()).isNotNull();
    assertThat(person.getAddress().getStreet().getName()).isNull();
  }

  @Test
  void fieldsExcludedWithAnnotationShouldNotBePopulated() {
    final Person person = random.nextObject(Person.class);

    assertThat(person).isNotNull();
    assertThat(person.getExcluded()).isNull();
  }

  @Test
  @SuppressWarnings("deprecation")
  void fieldsExcludedWithAnnotationViaFieldDefinitionShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(isAnnotatedWith(Deprecated.class));
    random = new EasyRandom(parameters);

    // when
    final Website website = random.nextObject(Website.class);

    // then
    assertThat(website).isNotNull();
    assertThat(website.getProvider()).isNull();
  }

  @Test
  void fieldsExcludedFromTypeViaFieldDefinitionShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(inClass(Address.class));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getAddress()).isNotNull();
    // all fields declared in class Address must be null
    assertThat(person.getAddress().getCity()).isNull();
    assertThat(person.getAddress().getStreet()).isNull();
    assertThat(person.getAddress().getZipCode()).isNull();
    assertThat(person.getAddress().getCountry()).isNull();
  }

  @Test
  void testFirstLevelExclusion() {
    final Parameters parameters = new Parameters()
        .excludeField(named("b2").and(inClass(C.class)));
    random = new EasyRandom(parameters);

    final C c = random.nextObject(C.class);

    assertThat(c).isNotNull();

    // B1 and its "children" must not be null
    assertThat(c.getB1()).isNotNull();
    assertThat(c.getB1().getA1()).isNotNull();
    assertThat(c.getB1().getA1().getS1()).isNotNull();
    assertThat(c.getB1().getA1().getS2()).isNotNull();
    assertThat(c.getB1().getA2()).isNotNull();
    assertThat(c.getB1().getA2().getS1()).isNotNull();
    assertThat(c.getB1().getA2().getS2()).isNotNull();

    // B2 must be null
    assertThat(c.getB2()).isNull();
  }

  @Test
  void testSecondLevelExclusion() { // goal: exclude only b2.a2
    final Parameters parameters = new Parameters()
        .randomize(ofType(A.class).and(inClass(B.class)),
            new ContextAwareRandomizer<A>() {
              private Context context;

              @Override
              public void setContext(final Context context) {
                this.context = context;
              }

              @Override
              public A getRandomValue() {
                if (context.getCurrentFieldPath().equals("b2.a2")) {
                  return null;
                }
                return new EasyRandom().nextObject(A.class);
              }
            });
    random = new EasyRandom(parameters);
    final C c = random.nextObject(C.class);

    assertThat(c).isNotNull();

    // B1 and its "children" must not be null
    assertThat(c.getB1()).isNotNull();
    assertThat(c.getB1().getA1()).isNotNull();
    assertThat(c.getB1().getA1().getS1()).isNotNull();
    assertThat(c.getB1().getA1().getS2()).isNotNull();
    assertThat(c.getB1().getA2()).isNotNull();
    assertThat(c.getB1().getA2().getS1()).isNotNull();
    assertThat(c.getB1().getA2().getS2()).isNotNull();

    // Only B2.A2 must be null
    assertThat(c.getB2()).isNotNull();
    assertThat(c.getB2().getA1()).isNotNull();
    assertThat(c.getB2().getA1().getS1()).isNotNull();
    assertThat(c.getB2().getA1().getS2()).isNotNull();
    assertThat(c.getB2().getA2()).isNull();
  }

  @Test
  void testThirdLevelExclusion() { // goal: exclude only b2.a2.s2
    final Parameters parameters = new Parameters()
        .randomize(FieldPredicates.named("s2")
                                  .and(inClass(A.class)),
            new ContextAwareRandomizer<String>() {
              private Context context;

              @Override
              public void setContext(final Context context) {
                this.context = context;
              }

              @Override
              public String getRandomValue() {
                if (context.getCurrentFieldPath().equals("b2.a2.s2")) {
                  return null;
                }
                return new EasyRandom().nextObject(String.class);
              }
            });
    random = new EasyRandom(parameters);
    final C c = random.nextObject(C.class);

    // B1 and its "children" must not be null
    assertThat(c.getB1()).isNotNull();
    assertThat(c.getB1().getA1()).isNotNull();
    assertThat(c.getB1().getA1().getS1()).isNotNull();
    assertThat(c.getB1().getA1().getS2()).isNotNull();
    assertThat(c.getB1().getA2()).isNotNull();
    assertThat(c.getB1().getA2().getS1()).isNotNull();
    assertThat(c.getB1().getA2().getS2()).isNotNull();

    // Only B2.A2.S2 must be null
    assertThat(c.getB2()).isNotNull();
    assertThat(c.getB2().getA1()).isNotNull();
    assertThat(c.getB2().getA1().getS1()).isNotNull();
    assertThat(c.getB2().getA1().getS2()).isNotNull();
    assertThat(c.getB2().getA2().getS1()).isNotNull();
    assertThat(c.getB2().getA2().getS2()).isNull();
  }

  @Test
  void testFirstLevelCollectionExclusion() {
    final Parameters parameters = new Parameters()
        .excludeField(FieldPredicates.named("b3").and(inClass(C.class)));
    random = new EasyRandom(parameters);

    final C c = random.nextObject(C.class);

    assertThat(c).isNotNull();

    // B1 and its "children" must not be null
    assertThat(c.getB1()).isNotNull();
    assertThat(c.getB1().getA1()).isNotNull();
    assertThat(c.getB1().getA1().getS1()).isNotNull();
    assertThat(c.getB1().getA1().getS2()).isNotNull();
    assertThat(c.getB1().getA2()).isNotNull();
    assertThat(c.getB1().getA2().getS1()).isNotNull();
    assertThat(c.getB1().getA2().getS2()).isNotNull();

    // B1 and its "children" must not be null
    assertThat(c.getB2()).isNotNull();
    assertThat(c.getB2().getA1()).isNotNull();
    assertThat(c.getB2().getA1().getS1()).isNotNull();
    assertThat(c.getB2().getA1().getS2()).isNotNull();
    assertThat(c.getB2().getA2()).isNotNull();
    assertThat(c.getB2().getA2().getS1()).isNotNull();
    assertThat(c.getB2().getA2().getS2()).isNotNull();

    // B3 must be null
    assertThat(c.getB3()).isNull();
  }

  @Test
  void testSecondLevelCollectionExclusion() { // b3.a2 does not make sense, should be ignored
    final Parameters parameters = new Parameters()
        .randomize(FieldPredicates.named("a2")
                                  .and(inClass(B.class)),
            new ContextAwareRandomizer<A>() {
              private Context context;

              @Override
              public void setContext(final Context context) {
                this.context = context;
              }

              @Override
              public A getRandomValue() {
                if (context.getCurrentFieldPath().equals("b3.a2")) {
                  return null;
                }
                return new EasyRandom().nextObject(A.class);
              }
            });
    random = new EasyRandom(parameters);

    final C c = random.nextObject(C.class);

    assertThat(c).isNotNull();

    // B1 and its "children" must not be null
    assertThat(c.getB1()).isNotNull();
    assertThat(c.getB1().getA1()).isNotNull();
    assertThat(c.getB1().getA1().getS1()).isNotNull();
    assertThat(c.getB1().getA1().getS2()).isNotNull();
    assertThat(c.getB1().getA2()).isNotNull();
    assertThat(c.getB1().getA2().getS1()).isNotNull();
    assertThat(c.getB1().getA2().getS2()).isNotNull();

    // B2 and its "children" must not be null
    assertThat(c.getB2()).isNotNull();
    assertThat(c.getB2().getA1()).isNotNull();
    assertThat(c.getB2().getA1().getS1()).isNotNull();
    assertThat(c.getB2().getA1().getS2()).isNotNull();
    assertThat(c.getB2().getA2()).isNotNull();
    assertThat(c.getB2().getA2().getS1()).isNotNull();
    assertThat(c.getB2().getA2().getS2()).isNotNull();

    // B3 must not be null
    assertThat(c.getB3()).isNotNull();
  }

  @Test
  void whenFieldIsExcluded_thenItsInlineInitializationShouldBeUsedAsIs() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(named("myList").and(ofType(List.class))
                                     .and(inClass(
                                         InlineInitializationBean.class)));
    random = new EasyRandom(parameters);

    // when
    final InlineInitializationBean bean = random
        .nextObject(InlineInitializationBean.class);

    // then
    assertThat(bean).isNotNull();
    assertThat(bean.getMyList()).isEmpty();
  }

  @Test
  void whenFieldIsExcluded_itsInlineInitializationShouldBeUsedAsIs() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(named("myList").and(ofType(List.class))
                                     .and(inClass(
                                         InlineInitializationBeanPrivateConstructor.class)));
    random = new EasyRandom(parameters);

    // when
    final InlineInitializationBeanPrivateConstructor bean = random
        .nextObject(InlineInitializationBeanPrivateConstructor.class);

    // then
    assertThat(bean.getMyList()).isEmpty();
  }

  @Test
  void fieldsExcludedWithOneModifierShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(hasModifiers(Modifier.TRANSIENT));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getEmail()).isNull();
  }

  @Test
  void fieldsExcludedWithTwoModifiersShouldNotBePopulated() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(hasModifiers(Modifier.TRANSIENT | Modifier.PROTECTED));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getEmail()).isNull();
  }

  @Test
  void fieldsExcludedWithTwoModifiersShouldBePopulatedIfOneModifierIsNotFit() {
    // given
    final Parameters parameters = new Parameters()
        .excludeField(hasModifiers(Modifier.TRANSIENT | Modifier.PUBLIC));
    random = new EasyRandom(parameters);

    // when
    final Person person = random.nextObject(Person.class);

    // then
    assertThat(person).isNotNull();
    assertThat(person.getEmail()).isNotNull();
  }

  public static class InlineInitializationBean {

    private List<String> myList = new ArrayList<>();

    public List<String> getMyList() {
      return myList;
    }

    public void setMyList(final List<String> myList) {
      this.myList = myList;
    }
  }

  public static class InlineInitializationBeanPrivateConstructor {

    private final List<String> myList = new ArrayList<>();

    public List<String> getMyList() {
      return myList;
    }

    private InlineInitializationBeanPrivateConstructor() {
    }
  }
}
