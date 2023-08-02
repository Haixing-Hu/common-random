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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ltd.qubit.commons.random.beans.CollectionBean;
import ltd.qubit.commons.random.beans.CompositeCollectionBean;
import ltd.qubit.commons.random.beans.CustomList;
import ltd.qubit.commons.random.beans.DelayedQueueBean;
import ltd.qubit.commons.random.beans.ListSubclass;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.random.beans.SynchronousQueueBean;
import ltd.qubit.commons.random.beans.TypeVariableCollectionBean;
import ltd.qubit.commons.random.beans.WildCardCollectionBean;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
class CollectionPopulatorTest {

  private static final int SIZE = 2;
  private static final String STRING = "foo";

  @Mock
  private Context context;
  @Mock
  private EasyRandom random;
  private Parameters parameters;

  private CollectionPopulator collectionPopulator;

  @BeforeEach
  void setUp() {
    parameters = new Parameters().collectionSizeRange(SIZE, SIZE);
    collectionPopulator = new CollectionPopulator(random);
  }

  /*
   * Unit tests for CollectionPopulator class
   */
  @Test
  void rawInterfaceCollectionTypesMustBeReturnedEmpty() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    final Field field = Foo.class.getDeclaredField("rawInterfaceList");
    // When
    final Collection<?> collection = collectionPopulator.populate(field, context, null);
    // Then
    assertThat(collection).isEmpty();
  }

  @Test
  void rawConcreteCollectionTypesMustBeReturnedEmpty() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    final Field field = Foo.class.getDeclaredField("rawConcreteList");

    // When
    final Collection<?> collection = collectionPopulator.populate(field, context, null);

    // Then
    assertThat(collection).isEmpty();
  }

  @Test
  void typedInterfaceCollectionTypesMightBePopulated() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextObject(String.class, context)).thenReturn(STRING);
    final Field field = Foo.class.getDeclaredField("typedInterfaceList");

    // When
    @SuppressWarnings("unchecked")final
    Collection<String> collection = (Collection<String>)
        collectionPopulator.populate(field, context, null);
    // Then
    assertThat(collection).containsExactly(STRING, STRING);
  }

  @Test
  void typedConcreteCollectionTypesMightBePopulated() throws Exception {
    // Given
    when(context.getParameters()).thenReturn(parameters);
    when(random.nextObject(String.class, context)).thenReturn(STRING);
    final Field field = Foo.class.getDeclaredField("typedConcreteList");

    // When
    @SuppressWarnings("unchecked")final
    Collection<String> collection = (Collection<String>)
        collectionPopulator.populate(field, context, null);

    // Then
    assertThat(collection).containsExactly(STRING, STRING);
  }

  @SuppressWarnings("rawtypes")
  class Foo {
    private List rawInterfaceList;
    private List<String> typedInterfaceList;
    private ArrayList rawConcreteList;
    private ArrayList<String> typedConcreteList;

    public Foo() {
    }

    public List getRawInterfaceList() {
      return this.rawInterfaceList;
    }

    public List<String> getTypedInterfaceList() {
      return this.typedInterfaceList;
    }

    public ArrayList getRawConcreteList() {
      return this.rawConcreteList;
    }

    public ArrayList<String> getTypedConcreteList() {
      return this.typedConcreteList;
    }

    public void setRawInterfaceList(final List rawInterfaceList) {
      this.rawInterfaceList = rawInterfaceList;
    }

    public void setTypedInterfaceList(final List<String> typedInterfaceList) {
      this.typedInterfaceList = typedInterfaceList;
    }

    public void setRawConcreteList(final ArrayList rawConcreteList) {
      this.rawConcreteList = rawConcreteList;
    }

    public void setTypedConcreteList(final ArrayList<String> typedConcreteList) {
      this.typedConcreteList = typedConcreteList;
    }
  }

  /*
   * Integration tests for Collection types population
   */

  @Test
  void rawCollectionInterfacesShouldBeEmpty() {
    final CollectionBean collectionsBean = random.nextObject(CollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getCollection()).isEmpty();
    assertThat(collectionsBean.getSet()).isEmpty();
    assertThat(collectionsBean.getSortedSet()).isEmpty();
    assertThat(collectionsBean.getNavigableSet()).isEmpty();
    assertThat(collectionsBean.getList()).isEmpty();
    assertThat(collectionsBean.getQueue()).isEmpty();
    assertThat(collectionsBean.getBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getTransferQueue()).isEmpty();
    assertThat(collectionsBean.getDeque()).isEmpty();
    assertThat(collectionsBean.getBlockingDeque()).isEmpty();
  }

  @Test
  void unboundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
    final WildCardCollectionBean collectionsBean = random.nextObject(WildCardCollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getUnboundedWildCardTypedCollection()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedSortedSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedNavigableSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedList()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedTransferQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedDeque()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedBlockingDeque()).isEmpty();
  }

  @Test
  void boundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
    final WildCardCollectionBean collectionsBean = random.nextObject(WildCardCollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getBoundedWildCardTypedCollection()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedSortedSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedNavigableSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedList()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedTransferQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedDeque()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedBlockingDeque()).isEmpty();
  }

  @Test
  void typedCollectionInterfacesShouldNotBeEmpty() {
    final CollectionBean collectionsBean = random.nextObject(CollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedCollection());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSortedSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedNavigableSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedList());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTransferQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedDeque());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingDeque());
  }

  @Test
  void rawCollectionClassesShouldBeEmpty() {
    final CollectionBean collectionsBean = random.nextObject(CollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getArrayList()).isEmpty();
    assertThat(collectionsBean.getLinkedList()).isEmpty();
    assertThat(collectionsBean.getVector()).isEmpty();
    assertThat(collectionsBean.getStack()).isEmpty();
    assertThat(collectionsBean.getHashSet()).isEmpty();
    assertThat(collectionsBean.getLinkedHashSet()).isEmpty();
    assertThat(collectionsBean.getTreeSet()).isEmpty();
    assertThat(collectionsBean.getConcurrentSkipListSet()).isEmpty();
    assertThat(collectionsBean.getArrayBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getLinkedBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getConcurrentLinkedQueue()).isEmpty();
    assertThat(collectionsBean.getLinkedTransferQueue()).isEmpty();
    assertThat(collectionsBean.getPriorityQueue()).isEmpty();
    assertThat(collectionsBean.getPriorityBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getArrayDeque()).isEmpty();
    assertThat(collectionsBean.getLinkedBlockingDeque()).isEmpty();
    assertThat(collectionsBean.getConcurrentLinkedDeque()).isEmpty();
  }

  @Test
  void unboundedWildCardTypedCollectionClassesShouldBeEmpty() {
    final WildCardCollectionBean collectionsBean = random.nextObject(WildCardCollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getUnboundedWildCardTypedArrayList()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedLinkedList()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedVector()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedStack()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedHashSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedLinkedHashSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedTreeSet()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentSkipListSet())
            .isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedArrayBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedLinkedBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentLinkedQueue())
            .isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedLinkedTransferQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedPriorityQueue()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedPriorityBlockingQueue())
            .isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedArrayDeque()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedLinkedBlockingDeque()).isEmpty();
    assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentLinkedDeque())
            .isEmpty();
  }

  @Test
  void boundedWildCardTypedCollectionClassesShouldBeEmpty() {
    final WildCardCollectionBean collectionsBean = random.nextObject(WildCardCollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertThat(collectionsBean.getBoundedWildCardTypedArrayList()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedLinkedList()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedVector()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedStack()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedHashSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedLinkedHashSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedTreeSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedConcurrentSkipListSet()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedArrayBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedLinkedBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedConcurrentLinkedQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedLinkedTransferQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedPriorityQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedPriorityBlockingQueue()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedArrayDeque()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedLinkedBlockingDeque()).isEmpty();
    assertThat(collectionsBean.getBoundedWildCardTypedConcurrentLinkedDeque()).isEmpty();
  }

  @Test
  void typedCollectionClassesShouldNoBeEmpty() {
    final CollectionBean collectionsBean = random.nextObject(CollectionBean.class);
    assertThat(collectionsBean).isNotNull();
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayList());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedList());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedVector());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedStack());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedHashSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedHashSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTreeSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentSkipListSet());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayBlockingQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedTransferQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityBlockingQueue());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayDeque());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingDeque());
    assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedDeque());
  }

  @Test
  void compositeCollectionTypesShouldBeEmpty() {
    final CompositeCollectionBean compositeCollectionBean =
        random.nextObject(CompositeCollectionBean.class);
    assertThat(compositeCollectionBean.getListOfLists()).isEmpty();
    assertThat(compositeCollectionBean.getTypedListOfLists()).isEmpty();
    assertThat(compositeCollectionBean.getSetOfSets()).isEmpty();
    assertThat(compositeCollectionBean.getTypedSetOfSets()).isEmpty();
    assertThat(compositeCollectionBean.getQueueOfQueues()).isEmpty();
    assertThat(compositeCollectionBean.getTypedQueueOdQueues()).isEmpty();
  }

  @Test
  void synchronousQueueTypeMustBeRejected() {
    assertThatThrownBy(() -> random.nextObject(SynchronousQueueBean.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  @Test
  void delayedQueueTypeMustBeRejected() {
    assertThatThrownBy(() -> random.nextObject(DelayedQueueBean.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  @Test
  void rawInterfaceCollectionTypesMustBeGeneratedEmpty() {
    final Parameters params = new Parameters().scanClasspathForConcreteTypes(true);
    final EasyRandom rand = new EasyRandom(params);
    final List<?> list = rand.nextObject(List.class);
    assertThat(list).isEmpty();
  }

  @Test
  void rawConcreteCollectionTypesMustBeGeneratedEmpty() {
    final Parameters params = new Parameters().scanClasspathForConcreteTypes(true);
    final EasyRandom rand = new EasyRandom(params);
    final ArrayList<?> list = rand.nextObject(ArrayList.class);
    assertThat(list).isEmpty();
  }

  @Test
  void rawInterfaceMapTypesMustBeGeneratedEmpty() {
    final Parameters params = new Parameters().scanClasspathForConcreteTypes(true);
    final EasyRandom rand = new EasyRandom(params);
    final Map<?, ?> map = rand.nextObject(Map.class);
    assertThat(map).isEmpty();
  }

  @Test
  void rawConcreteMapTypesMustBeGeneratedEmpty() {
    final Parameters params = new Parameters().scanClasspathForConcreteTypes(true);
    final EasyRandom rand = new EasyRandom(params);
    final HashMap<?, ?> map = rand.nextObject(HashMap.class);
    assertThat(map).isEmpty();
  }

  @Test
  void userDefinedCollectionTypeShouldBePopulated() {
    final CustomList customList = random.nextObject(CustomList.class);
    assertThat(customList).isNotNull();
    assertThat(customList.getName()).isNotNull();
  }

  @Test
  void typeVariableCollectionTypesMustBeGeneratedEmpty() {
    final TypeVariableCollectionBean<String, String> bean =
        random.nextObject(TypeVariableCollectionBean.class);
    assertThat(bean.getCollection()).isEmpty();
    assertThat(bean.getList()).isEmpty();
    assertThat(bean.getSet()).isEmpty();
    assertThat(bean.getMap()).isEmpty();
  }

  private void assertContainsOnlyNonEmptyPersons(final Collection<Person> persons) {
    for (final Person Person : persons) {
      assertThat(Person).isNotNull();
      assertThat(Person.getAddress().getCity()).isNotEmpty();
      assertThat(Person.getAddress().getZipCode()).isNotEmpty();
      assertThat(Person.getName()).isNotEmpty();
    }
  }

  @Test
  void collectionSubclassShouldBePopulated() {
    final EasyRandom rand = new EasyRandom();
    final Parameters params = rand.getParameters();
    params.ignoreRandomizationErrors(false);
    final CloseRange<Integer> range = params.getCollectionSizeRange();
    final ListSubclass bean = rand.nextObject(ListSubclass.class);
    assertNotNull(bean.getName());
    assertNotNull(bean.getAliases());
    assertThat(bean.getAliases().size())
            .isGreaterThanOrEqualTo(range.getMin())
            .isLessThanOrEqualTo(range.getMax());
    assertThat(bean.size())
            .isGreaterThanOrEqualTo(range.getMin())
            .isLessThanOrEqualTo(range.getMax());
  }
}
