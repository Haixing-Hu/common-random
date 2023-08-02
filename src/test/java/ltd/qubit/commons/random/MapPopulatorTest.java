////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.beans.CompositeMapBean;
import ltd.qubit.commons.random.beans.CustomMap;
import ltd.qubit.commons.random.beans.EnumMapBean;
import ltd.qubit.commons.random.beans.MapBean;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.WildCardMapBean;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
class MapPopulatorTest {

  private static final int SIZE = 1;
  private static final String FOO = "foo";
  private static final String BAR = "bar";

  @Mock
  private Context context;
  @Mock
  private EasyRandom random;
  private Parameters parameters;

  private MapPopulator mapPopulator;

  @BeforeEach
  void setUp() {
    parameters = new Parameters().collectionSizeRange(SIZE, SIZE);
    final ObjectFactory objectFactory = new ObjenesisObjectFactory();
    mapPopulator = new MapPopulator(random, objectFactory);
  }

  /*
   * Unit tests for MapPopulator class
   */

  @Test
  void rawInterfaceMapTypesMustBeGeneratedEmpty() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextInt(new CloseRange<>(SIZE, SIZE))).thenReturn(SIZE);
    final Field field = Foo.class.getDeclaredField("rawMap");
    // When
    final Map<?, ?> randomMap = mapPopulator.populate(field, context, null);
    // Then
    assertThat(randomMap).isEmpty();
  }

  @Test
  void rawConcreteMapTypesMustBeGeneratedEmpty() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextInt(new CloseRange<>(SIZE, SIZE))).thenReturn(SIZE);
    final Field field = Foo.class.getDeclaredField("concreteMap");
    // When
    final Map<?, ?> randomMap = mapPopulator.populate(field, context, null);
    // Then
    assertThat(randomMap).isEmpty();
  }

  @Test
  void typedInterfaceMapTypesMightBePopulated() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextInt(new CloseRange<>(SIZE, SIZE))).thenReturn(SIZE);
    when(random.nextObject(String.class, context)).thenReturn(FOO, BAR);
    final Field field = Foo.class.getDeclaredField("typedMap");
    // When
    final Map<String, String> randomMap =
        (Map<String, String>) mapPopulator.populate(field, context, null);
    // Then
    assertThat(randomMap).containsExactly(entry(FOO, BAR));
  }

  @Test
  void typedConcreteMapTypesMightBePopulated() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextInt(new CloseRange<>(SIZE, SIZE))).thenReturn(SIZE);
    when(random.nextObject(String.class, context)).thenReturn(FOO, BAR);
    final Field field = Foo.class.getDeclaredField("typedConcreteMap");
    // When
    final Map<String, String> randomMap =
        (Map<String, String>) mapPopulator.populate(field, context, null);
    // Then
    assertThat(randomMap).containsExactly(entry(FOO, BAR));
  }

  @Test
  void notAddNullKeysToMap() throws NoSuchFieldException {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextInt(new CloseRange<>(SIZE, SIZE))).thenReturn(SIZE);
    when(random.nextObject(String.class, context)).thenReturn(null);
    final Field field = Foo.class.getDeclaredField("typedConcreteMap");
    // When
    final Map<String, String> randomMap =
        (Map<String, String>) mapPopulator.populate(field, context, null);
    // Then
    assertThat(randomMap).isEmpty();
  }

  class Foo {
    private Map rawMap;
    private HashMap concreteMap;
    private Map<String, String> typedMap;
    private HashMap<String, String> typedConcreteMap;

    public Foo() {
    }

    public Map getRawMap() {
      return this.rawMap;
    }

    public HashMap getConcreteMap() {
      return this.concreteMap;
    }

    public Map<String, String> getTypedMap() {
      return this.typedMap;
    }

    public HashMap<String, String> getTypedConcreteMap() {
      return this.typedConcreteMap;
    }

    public void setRawMap(final Map rawMap) {
      this.rawMap = rawMap;
    }

    public void setConcreteMap(final HashMap concreteMap) {
      this.concreteMap = concreteMap;
    }

    public void setTypedMap(final Map<String, String> typedMap) {
      this.typedMap = typedMap;
    }

    public void setTypedConcreteMap(final HashMap<String, String> typedConcreteMap) {
      this.typedConcreteMap = typedConcreteMap;
    }
  }

  /*
   * Integration tests for map types population
   */

  @Test
  void rawMapInterfacesShouldBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final MapBean mapBean = easyRandom.nextObject(MapBean.class);

    assertThat(mapBean).isNotNull();

    assertThat(mapBean.getMap()).isEmpty();
    assertThat(mapBean.getSortedMap()).isEmpty();
    assertThat(mapBean.getNavigableMap()).isEmpty();
    assertThat(mapBean.getConcurrentMap()).isEmpty();
    assertThat(mapBean.getConcurrentNavigableMap()).isEmpty();
  }

  @Test
  void typedMapInterfacesShouldNotBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final MapBean mapBean = easyRandom.nextObject(MapBean.class);

    assertThat(mapBean).isNotNull();

    assertContainsNonZeroIntegers(mapBean.getTypedMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedSortedMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedSortedMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedNavigableMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedNavigableMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedConcurrentMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedConcurrentNavigableMap()
                                         .keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentNavigableMap()
                                             .values());
  }

  @Test
  void rawMapClassesShouldBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final MapBean mapBean = easyRandom.nextObject(MapBean.class);

    assertThat(mapBean).isNotNull();

    assertThat(mapBean.getHashMap()).isEmpty();
    assertThat(mapBean.getHashtable()).isEmpty();
    assertThat(mapBean.getLinkedHashMap()).isEmpty();
    assertThat(mapBean.getWeakHashMap()).isEmpty();
    assertThat(mapBean.getIdentityHashMap()).isEmpty();
    assertThat(mapBean.getTreeMap()).isEmpty();
    assertThat(mapBean.getConcurrentSkipListMap()).isEmpty();
  }

  @Test
  void typedMapClassesShouldNotBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final MapBean mapBean = easyRandom.nextObject(MapBean.class);

    assertThat(mapBean).isNotNull();

    assertContainsNonZeroIntegers(mapBean.getTypedHashMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedHashtable().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashtable().values());

    assertContainsNonZeroIntegers(mapBean.getTypedLinkedHashMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedLinkedHashMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedWeakHashMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedWeakHashMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedIdentityHashMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedIdentityHashMap()
                                             .values());

    assertContainsNonZeroIntegers(mapBean.getTypedTreeMap().keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedTreeMap().values());

    assertContainsNonZeroIntegers(mapBean.getTypedConcurrentSkipListMap()
                                         .keySet());
    assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentSkipListMap()
                                             .values());
  }

  @Test
  void wildcardTypedMapInterfacesShouldBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final WildCardMapBean wildCardMapBean = easyRandom.nextObject(WildCardMapBean.class);

    assertThat(wildCardMapBean).isNotNull();

    assertThat(wildCardMapBean.getBoundedWildCardTypedMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedSortedMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedSortedMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedNavigableMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedNavigableMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentNavigableMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentNavigableMap())
            .isEmpty();
  }

  @Test
  void wildcardTypedMapClassesShouldBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final WildCardMapBean wildCardMapBean = easyRandom.nextObject(WildCardMapBean.class);

    assertThat(wildCardMapBean).isNotNull();

    assertThat(wildCardMapBean.getBoundedWildCardTypedHashMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedHashMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedHashtable()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedHashtable()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedLinkedHashMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedHinkedHashMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedWeakHashMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedWeakHashMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedIdentityHashMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedIdentityHashMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedTreeMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedTreeMap()).isEmpty();

    assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentSkipListMap()).isEmpty();
    assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentSkipListMap())
            .isEmpty();
  }

  @Test
  void compositeMapTypesShouldBeEmpty() {
    final EasyRandom easyRandom = new EasyRandom();

    final CompositeMapBean compositeMapBean = easyRandom.nextObject(CompositeMapBean.class);

    assertThat(compositeMapBean.getPersonToNicknames()).isEmpty();
    assertThat(compositeMapBean.getPersonToAccounts()).isEmpty();
    assertThat(compositeMapBean.getReallyStrangeCompositeDataStructure()).isEmpty();
  }

  @Test
  void userDefinedMapTypeShouldBePopulated() {
    final EasyRandom easyRandom = new EasyRandom();

    final CustomMap customMap = easyRandom.nextObject(CustomMap.class);

    assertThat(customMap).isNotNull();
    assertThat(customMap.getName()).isNotNull();
  }

  @Test
  void enumMapTypeShouldBePopulated() {
    final EasyRandom easyRandom = new EasyRandom();

    final EnumMapBean enumMapBean = easyRandom.nextObject(EnumMapBean.class);

    assertThat(enumMapBean).isNotNull();
    assertThat(enumMapBean.getTypedEnumMap()).isNotNull();
    assertThat(enumMapBean.getUntypedEnumMap()).isNull();
  }

  private void assertContainsOnlyNonEmptyPersons(final Collection<Person> persons) {
    for (final Person person : persons) {
      assertThat(person).isNotNull();
      assertThat(person.getAddress().getCity()).isNotEmpty();
      assertThat(person.getAddress().getZipCode()).isNotEmpty();
      assertThat(person.getName()).isNotEmpty();
    }
  }

  private void assertContainsNonZeroIntegers(final Collection collection) {
    assertThat(collection).hasOnlyElementsOfType(Integer.class)
                          .doesNotContain(0);
  }
}
