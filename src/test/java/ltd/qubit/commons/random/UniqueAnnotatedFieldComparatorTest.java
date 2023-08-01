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
import java.util.Arrays;
import java.util.List;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.Option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit test of the {@link UniqueAnnotatedFieldComparator}.
 *
 * @author Haixing Hu
 */
public class UniqueAnnotatedFieldComparatorTest {

  static class F1 {
    @Unique
    private String f1;
    private String f2;
  }

  static class F2 {
    @Unique
    private String f1;
    @Unique
    private String f2;
  }

  static class F3 {
    private String f1;
    private String f2;
  }

  static class F4 {
    @Unique(respectTo = "f3")
    private String f1;
    private String f2;
    @Unique(respectTo = "f2")
    private String f3;
  }

  static class F5 {
    @Unique(respectTo = {"f3", "f2"})
    private String f1;
    @Unique(respectTo = "f4")
    private String f2;
    private String f3;
    @Unique
    private String f4;
  }

  static class Foo {
    @Unique
    private String f1;

    private String f2;

    @Unique(respectTo = {"f4", "f5"})
    private String f3;

    @Unique(respectTo = {"f1", "f5"})
    private String f4;

    private String f5;
  }

  @Test
  public void testCompareFieldF1() throws Exception {
    testCompareFieldImpl(F1.class, new String[]{"f2", "f1"});
  }

  @Test
  public void testCompareFieldF2() throws Exception {
    testCompareFieldImpl(F2.class, new String[]{"f1", "f2"});
  }

  @Test
  public void testCompareFieldF3() throws Exception {
    testCompareFieldImpl(F3.class, new String[]{"f1", "f2"});
  }

  @Test
  public void testCompareFieldF4() throws Exception {
    testCompareFieldImpl(F4.class, new String[]{"f2", "f3", "f1"});
  }

  @Test
  public void testCompareFieldF5() throws Exception {
    testCompareFieldImpl(F5.class, new String[]{"f3", "f4", "f2", "f1"});
  }

  @Test
  public void testCompareFieldFoo() throws Exception {
    testCompareFieldImpl(Foo.class, new String[]{"f2", "f5", "f1", "f4", "f3"});
  }

  private void testCompareFieldImpl(final Class<?> cls, final String[] expected) {
    final UniqueAnnotatedFieldComparator comparator = new UniqueAnnotatedFieldComparator();
    final List<Field> fields = FieldUtils.getAllFields(cls, Option.ALL);
    fields.sort(comparator);
    final Object[] actual = fields.stream().map((f) -> f.getName()).toArray();
    System.out.println(Arrays.toString(actual));
    assertArrayEquals(expected, actual);
  }
}
