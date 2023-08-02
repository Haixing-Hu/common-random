////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Array;

import ltd.qubit.commons.random.beans.ArrayBean;
import ltd.qubit.commons.random.beans.Person;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArrayPopulatorTest {

  private static final int INT = 10;
  private static final String STRING = "FOO";
  private static final int HEX = 0x1A;

  @Mock
  private Context context;
  @Mock
  private EasyRandom random;

  private ArrayPopulator arrayPopulator;

  @BeforeEach
  void setUp() {
    arrayPopulator = new ArrayPopulator(random);
  }

  @Test
  void getRandomArray() {
    when(context.getParameters()).thenReturn(new Parameters().collectionSizeRange(INT, INT));
    when(random.nextInt(new CloseRange<>(INT, INT))).thenReturn(INT);
    when(random.nextObject(String.class, context)).thenReturn(STRING);
    final String[] strings = (String[]) arrayPopulator.populate(String[].class, context, null);
    assertThat(strings).containsOnly(STRING);
  }

  /*
   * Integration tests for arrays population
   */

  @Test
  void testArrayPopulation() {
    final EasyRandom easyRandom = new EasyRandom();

    final String[] strings = easyRandom.nextObject(String[].class);

    assertThat(strings).isNotNull();
  }

  @Test
  void testPrimitiveArrayPopulation() {
    final EasyRandom easyRandom = new EasyRandom();

    final int[] ints = easyRandom.nextObject(int[].class);

    assertThat(ints).isNotNull();
  }

  @Test
  void primitiveArraysShouldBeCorrectlyPopulated() {
    final EasyRandom easyRandom = new EasyRandom();

    final ArrayBean bean = easyRandom.nextObject(ArrayBean.class);

    // primitive types
    assertThat(toObjectArray(bean.getByteArray())).hasOnlyElementsOfType(Byte.class);
    assertThat(toObjectArray(bean.getShortArray())).hasOnlyElementsOfType(Short.class);
    assertThat(toObjectArray(bean.getIntArray())).hasOnlyElementsOfType(Integer.class);
    assertThat(toObjectArray(bean.getLongArray())).hasOnlyElementsOfType(Long.class);
    assertThat(toObjectArray(bean.getFloatArray())).hasOnlyElementsOfType(Float.class);
    assertThat(toObjectArray(bean.getDoubleArray())).hasOnlyElementsOfType(Double.class);
    assertThat(toObjectArray(bean.getCharArray())).hasOnlyElementsOfType(Character.class);
    assertThat(toObjectArray(bean.getBooleanArray())).hasOnlyElementsOfType(Boolean.class);
  }

  @Test
  void wrapperTypeArraysShouldBeCorrectlyPopulated() {
    final EasyRandom easyRandom = new EasyRandom();

    final ArrayBean bean = easyRandom.nextObject(ArrayBean.class);

    // wrapper types
    assertThat(bean.getBytes()).hasOnlyElementsOfType(Byte.class);
    assertThat(bean.getShorts()).hasOnlyElementsOfType(Short.class);
    assertThat(bean.getIntegers()).hasOnlyElementsOfType(Integer.class);
    assertThat(bean.getLongs()).hasOnlyElementsOfType(Long.class);
    assertThat(bean.getFloats()).hasOnlyElementsOfType(Float.class);
    assertThat(bean.getDoubles()).hasOnlyElementsOfType(Double.class);
    assertThat(bean.getCharacters()).hasOnlyElementsOfType(Character.class);
    assertThat(bean.getBooleans()).hasOnlyElementsOfType(Boolean.class);
  }

  @Test
  void arraysWithCustomTypesShouldBeCorrectlyPopulated() {
    final EasyRandom easyRandom = new EasyRandom();

    final ArrayBean bean = easyRandom.nextObject(ArrayBean.class);

    // custom types
    assertThat(bean.getStrings()).doesNotContain(null, "");

    final Person[] persons = bean.getPersons();
    assertContainsOnlyNonEmptyPersons(persons);
  }

  private void assertContainsOnlyNonEmptyPersons(final Person[] persons) {
    for (final Person person : persons) {
      assertThat(person).isNotNull();
      assertThat(person.getAddress().getCity()).isNotEmpty();
      assertThat(person.getAddress().getZipCode()).isNotEmpty();
      assertThat(person.getName()).isNotEmpty();
    }
  }

  private Object[] toObjectArray(final Object primitiveArray) {
    final int length = Array.getLength(primitiveArray);
    final Object[] objectArray = new Object[length];
    for (int i = 0; i < length; ++i) {
      objectArray[i] = Array.get(primitiveArray, i);
    }
    return objectArray;
  }
}
