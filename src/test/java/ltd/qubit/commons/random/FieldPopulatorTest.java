////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.JAXBElement;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.beans.ArrayBean;
import ltd.qubit.commons.random.beans.CollectionBean;
import ltd.qubit.commons.random.beans.Human;
import ltd.qubit.commons.random.beans.MapBean;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.randomizers.misc.SkipRandomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class FieldPopulatorTest {

  private static final String NAME = "foo";

  @Mock
  private EasyRandom random;
  @Mock
  private RegistriesRandomizerProvider randomizerProvider;
  @SuppressWarnings("rawtypes")
  @Mock
  private Randomizer randomizer;
  @Mock
  private ArrayPopulator arrayPopulator;
  @Mock
  private CollectionPopulator collectionPopulator;
  @Mock
  private MapPopulator mapPopulator;

  private FieldPopulator fieldPopulator;

  @BeforeEach
  void setUp() {
    fieldPopulator = new FieldPopulator(random, randomizerProvider,
            arrayPopulator, collectionPopulator, mapPopulator);
  }

  @Test
  void whenSkipRandomizerIsRegisteredForTheField_thenTheFieldShouldBeSkipped() throws Exception {
    // Given
    final Field name = Human.class.getDeclaredField("name");
    final Human human = new Human();
    randomizer = new SkipRandomizer();
    final Context context = new Context(Human.class, new Parameters());
    when(randomizerProvider.getByField(name, context)).thenReturn(randomizer);
    // When
    fieldPopulator.populate(human, name, context);
    // Then
    assertThat(human.getName()).isNull();
  }

  @Test
  void customRandomizerIsRegisteredForTheField_populatedWithTheRandomValue() throws Exception {
    // Given
    final Field name = Human.class.getDeclaredField("name");
    final Human human = new Human();
    final Context context = new Context(Human.class, new Parameters());
    when(randomizerProvider.getByField(name, context)).thenReturn(randomizer);
    when(randomizer.getRandomValue()).thenReturn(NAME);

    // When
    fieldPopulator.populate(human, name, context);

    // Then
    assertThat(human.getName()).isEqualTo(NAME);
  }

  @Test
  void fieldIsOfTypeArray_populationToArrayPopulator() throws Exception {
    // Given
    final Field strings = ArrayBean.class.getDeclaredField("strings");
    final ArrayBean arrayBean = new ArrayBean();
    final String[] object = new String[0];
    final Context context = new Context(ArrayBean.class, new Parameters());
    when(arrayPopulator.populate(strings.getType(), context, null)).thenReturn(object);

    // When
    fieldPopulator.populate(arrayBean, strings, context);

    // Then
    assertThat(arrayBean.getStrings()).isEqualTo(object);
  }

  @Test
  void fieldIsOfTypeCollection_populationToCollectionPopulator() throws Exception {
    // Given
    final Field strings = CollectionBean.class.getDeclaredField("typedCollection");
    final CollectionBean collectionBean = new CollectionBean();
    final Collection<Person> persons = Collections.emptyList();
    final Context context = new Context(CollectionBean.class, new Parameters());

    // When
    fieldPopulator.populate(collectionBean, strings, context);

    // Then
    assertThat(collectionBean.getTypedCollection()).isEqualTo(persons);
  }

  @Test
  void whenTheFieldIsOfTypeMap_thenShouldDelegatePopulationToMapPopulator() throws Exception {
    // Given
    final Field strings = MapBean.class.getDeclaredField("typedMap");
    final MapBean mapBean = new MapBean();
    final Map<Integer, Person> idToPerson = new HashMap<>();
    final Context context = new Context(MapBean.class, new Parameters());

    // When
    fieldPopulator.populate(mapBean, strings, context);

    // Then
    assertThat(mapBean.getTypedMap()).isEqualTo(idToPerson);
  }

  @Test
  void whenRandomizationDepthIsExceeded_thenFieldsAreNotInitialized() throws Exception {
    // Given
    final Field name = Human.class.getDeclaredField("name");
    final Human human = new Human();
    final Context context = Mockito.mock(Context.class);
    when(context.hasExceededRandomizationDepth()).thenReturn(true);

    // When
    fieldPopulator.populate(human, name, context);

    // Then
    assertThat(human.getName()).isNull();
  }

  @Test //https://github.com/j-easy/easy-random/issues/221
  @Disabled("Objenesis is able to create an instance of JAXBElement type. "
              + "Hence no error is thrown as expected in this test")
  void shouldFailWithNiceErrorMessageWhenUnableToCreateFieldValue() throws Exception {
    // Given
    final FieldPopulator populator = new FieldPopulator(new EasyRandom(), randomizerProvider,
            arrayPopulator, collectionPopulator, mapPopulator);
    final Field jaxbElementField = JaxbElementFieldBean.class.getDeclaredField("jaxbElementField");
    final JaxbElementFieldBean jaxbElementFieldBean = new JaxbElementFieldBean();
    final Context context = Mockito.mock(Context.class);

    thenThrownBy(() -> {
      populator.populate(jaxbElementFieldBean, jaxbElementField, context);
    }).hasMessage("Unable to create type: javax.xml.bind.JAXBElement for field: "
        + "jaxbElementField of class: ltd.qubit.commons.random."
        + "FieldPopulatorTest$JaxbElementFieldBean");
  }

  public class JaxbElementFieldBean {
    JAXBElement<String> jaxbElementField;
  }
}
