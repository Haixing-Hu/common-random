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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.random.beans.AbstractFoo;
import ltd.qubit.commons.random.beans.BarBar;
import ltd.qubit.commons.random.beans.ComplexUniqueAnnotatedBean;
import ltd.qubit.commons.random.beans.DerivedClassBar;
import ltd.qubit.commons.random.beans.DerivedClassFoo;
import ltd.qubit.commons.random.beans.IdentifierAnnotated;
import ltd.qubit.commons.random.beans.MapSubclass;
import ltd.qubit.commons.random.beans.NullableFieldObject;
import ltd.qubit.commons.random.beans.NullableSizeAnnotatedFieldObject;
import ltd.qubit.commons.random.beans.ObjectWithList;
import ltd.qubit.commons.random.beans.ObjectWithListNoSize;
import ltd.qubit.commons.random.beans.ObjectWithSet;
import ltd.qubit.commons.random.beans.ObjectWithSetNoSize;
import ltd.qubit.commons.random.beans.Organization;
import ltd.qubit.commons.random.beans.Payload;
import ltd.qubit.commons.random.beans.ScaleAnnotatedFieldObject;
import ltd.qubit.commons.random.beans.ScaleUniqueNullableAnnotatedFieldObject;
import ltd.qubit.commons.random.beans.SizeAnnotatedFieldObject;
import ltd.qubit.commons.random.beans.StringList;
import ltd.qubit.commons.random.beans.StringMap;
import ltd.qubit.commons.random.beans.StringSet;
import ltd.qubit.commons.random.beans.UniqueAnnotatedFieldObject;
import ltd.qubit.commons.random.beans.UniqueSizeNullableAnnotatedFieldObject;
import ltd.qubit.commons.util.range.CloseRange;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.handlers.NullableAnnotationHandler.DEFAULT_NULL_RATIO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test of the {@link RandomBeanGenerator}.
 *
 * @author Haixing Hu
 */
public class RandomBeanGeneratorTest {

  static final int TEST_COUNT = 500;
  static final double EPSILON = 0.05;

  private final RandomBeanGenerator generator = new RandomBeanGenerator();

  @Test
  public void testGenerateNullableField() {
    final int[] nullCounts = {0, 0, 0, 0, 0};
    final int testCount = TEST_COUNT * 10;
    for (int i = 0; i < testCount; ++i) {
      System.out.println("testGenerateNullableField: " + i);
      final NullableFieldObject obj = generator.nextObject(NullableFieldObject.class);
      assertNotNull(obj);
      if (obj.f0 == null) {
        ++nullCounts[0];
      }
      if (obj.f1 == null) {
        ++nullCounts[1];
      }
      if (obj.f2 == null) {
        ++nullCounts[2];
      }
      if (obj.f3 == null) {
        ++nullCounts[3];
      }
      if (obj.f4 == null) {
        ++nullCounts[4];
      }
    }
    assertEquals(0, nullCounts[0]);
    assertEquals(0, nullCounts[1]);
    assertEquals(0, nullCounts[3]);
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f2: %f\n", nullCounts[2] / (double) testCount);
    System.out.printf("Null ratio of f4: %f\n", nullCounts[4] / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[2] / (double) testCount, EPSILON);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[4] / (double) testCount, EPSILON);
  }

  @Test
  public void testSizeAnnotatedField() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testSizeAnnotatedField: " + i);
      final SizeAnnotatedFieldObject obj = generator.nextObject(SizeAnnotatedFieldObject.class);
      assertNotNull(obj);
      assertThat(obj.f1).isGreaterThanOrEqualTo(10).isLessThanOrEqualTo(100);
      assertThat(obj.f1a).isNotNull()
                         .isGreaterThanOrEqualTo(10)
                         .isLessThanOrEqualTo(100);
      assertThat(obj.f2).isNotNull();
      assertThat(obj.f2.length()).isGreaterThanOrEqualTo(3)
                                 .isLessThanOrEqualTo(6);
      assertThat(obj.f2b).isNotNull();
      assertThat(obj.f2b.length()).isGreaterThanOrEqualTo(0)
                                  .isLessThanOrEqualTo(3);
      assertThat(obj.f3).isNotNull();
      assertThat(obj.f3.length).isGreaterThanOrEqualTo(1)
                               .isLessThanOrEqualTo(5);
      assertThat(obj.f3c).isNotNull();
      assertThat(obj.f3c.length).isGreaterThanOrEqualTo(1)
                                .isLessThanOrEqualTo(5);
      assertThat(obj.f4).isNotNull();
      assertThat(obj.f4.size()).isGreaterThanOrEqualTo(2)
                               .isLessThanOrEqualTo(4);
      assertThat(obj.f5).isNotNull();
      assertThat(obj.f5.size()).isGreaterThanOrEqualTo(4)
                               .isLessThanOrEqualTo(6);
    }
  }

  @Test
  public void testNullableSizeAnnotatedField() {
    int nullCount = 0;
    final int testCount = TEST_COUNT * 10;
    for (int i = 0; i < testCount; ++i) {
      System.out.println("testNullableSizeAnnotatedField: " + i);
      final NullableSizeAnnotatedFieldObject obj =
          generator.nextObject(NullableSizeAnnotatedFieldObject.class);
      assertNotNull(obj);
      if (obj.f1 == null) {
        ++nullCount;
      } else {
        assertThat(obj.f1.length()).isLessThanOrEqualTo(3);
      }
    }
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f1: %f\n", nullCount / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCount / (double) testCount, EPSILON);
  }

  @Test
  public void testUniqueAnnotatedField() {
    final Set<String> values = new HashSet<>();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testUniqueAnnotatedField: " + i);
      final UniqueAnnotatedFieldObject obj =
          generator.nextObject(UniqueAnnotatedFieldObject.class);
      assertNotNull(obj);
      assertNotNull(obj.f1);
      assertFalse(values.contains(obj.f1));
      values.add(obj.f1);
    }
  }

  @Test
  public void testUniqueSizeNullableAnnotatedField() {
    final Set<String> values = new HashSet<>();
    int nullCount = 0;
    final int testCount = TEST_COUNT * 10;
    for (int i = 0; i < testCount; ++i) {
      System.out.println("testUniqueSizeNullableAnnotatedField: " + i);
      final UniqueSizeNullableAnnotatedFieldObject obj =
              generator.nextObject(UniqueSizeNullableAnnotatedFieldObject.class);
      System.out.println(obj);
      assertNotNull(obj);
      if (obj.f1 == null) {
        ++nullCount;
      } else {
        assertThat(obj.f1.length()).isLessThanOrEqualTo(10);
        assertFalse(values.contains(obj.f1));
        values.add(obj.f1);
      }
    }
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f1: %f\n", nullCount / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCount / (double) testCount, EPSILON);
  }

  @Test
  public void testScaleAnnotatedField() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testScaleAnnotatedField: " + i);
      final ScaleAnnotatedFieldObject obj =
              generator.nextObject(ScaleAnnotatedFieldObject.class);
      assertNotNull(obj);
      assertNotNull(obj.f1);
      assertEquals(3, obj.f1.scale());
    }
  }

  @Test
  public void testScaleUniqueNullableAnnotatedField() {
    final Set<BigDecimal> values = new HashSet<>();
    int nullCount = 0;
    final int testCount = TEST_COUNT * 10;
    for (int i = 0; i < testCount; ++i) {
      System.out.println("testScaleUniqueNullableAnnotatedField: " + i);
      final ScaleUniqueNullableAnnotatedFieldObject obj =
              generator.nextObject(ScaleUniqueNullableAnnotatedFieldObject.class);
      assertNotNull(obj);
      if (obj.f1 == null) {
        ++nullCount;
      } else {
        assertEquals(4, obj.f1.scale());
        assertFalse(values.contains(obj.f1));
        values.add(obj.f1);
      }
    }
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f1: %f\n", nullCount / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCount / (double) testCount, EPSILON);
  }

  @Test
  public void testGenerateFieldWithList() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateFieldWithList: " + i);
      final ObjectWithList obj = generator.nextObject(ObjectWithList.class);
      assertThat(obj.getIntList().size())
              .isGreaterThanOrEqualTo(8)
              .isLessThanOrEqualTo(12);
      assertThat(obj.getStringList().size())
              .isGreaterThanOrEqualTo(8)
              .isLessThanOrEqualTo(12);
    }
  }

  @Test
  public void testGenerateFieldWithSet() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateFieldWithSet: " + i);
      final ObjectWithSet obj = generator.nextObject(ObjectWithSet.class);
      assertThat(obj.getIntSet().size())
              .isGreaterThanOrEqualTo(8)
              .isLessThanOrEqualTo(12);
      assertThat(obj.getStringSet().size())
              .isGreaterThanOrEqualTo(8)
              .isLessThanOrEqualTo(12);
    }
  }

  @Test
  public void testGenerateFieldWithListNoSize() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final Integer min = range.getMin();
    final Integer max = range.getMax();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateFieldWithListNoSize: " + i);
      final ObjectWithListNoSize obj = generator.nextObject(ObjectWithListNoSize.class);
      assertThat(obj.getIntList().size())
              .isGreaterThanOrEqualTo(min)
              .isLessThanOrEqualTo(max);
      assertThat(obj.getStringList().size())
              .isGreaterThanOrEqualTo(min)
              .isLessThanOrEqualTo(max);
    }
  }

  @Test
  public void testGenerateFieldWithSetNoSize() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final Integer min = range.getMin();
    final Integer max = range.getMax();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateFieldWithSetNoSize: " + i);
      final ObjectWithSetNoSize obj = generator.nextObject(ObjectWithSetNoSize.class);
      assertThat(obj.getIntSet().size())
              .isGreaterThanOrEqualTo(min)
              .isLessThanOrEqualTo(max);
      assertThat(obj.getStringSet().size())
              .isGreaterThanOrEqualTo(min)
              .isLessThanOrEqualTo(max);
    }
  }

  @Test
  public void testGenerateList() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateList: " + i);
      final List<Integer> list = generator.nextList(Integer.class);
      System.out.println("Generate a list: " + list);
      assertThat(list.size())
              .isGreaterThanOrEqualTo(range.getMin())
              .isLessThanOrEqualTo(range.getMax());
    }
  }

  @Test
  public void testGenerateListSubclass() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateListSubclass: " + i);
      final StringList list = generator.nextObject(StringList.class);
      System.out.println("Generate a list: " + list.toString());
      assertThat(list.size())
              .isGreaterThanOrEqualTo(range.getMin())
              .isLessThanOrEqualTo(range.getMax());
    }
  }

  @Test
  public void testGenerateSet() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateSet: " + i);
      final Set<Integer> set = generator.nextSet(Integer.class);
      System.out.println("Generate a set: " + set);
      assertThat(set.size())
              .isGreaterThanOrEqualTo(range.getMin())
              .isLessThanOrEqualTo(range.getMax());
    }
  }

  @Test
  public void testGenerateSetSubclass() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateSetSubclass: " + i);
      final StringSet set = generator.nextObject(StringSet.class);
      System.out.println("Generate a set: " + set);
      assertThat(set.size())
              .isGreaterThanOrEqualTo(range.getMin())
              .isLessThanOrEqualTo(range.getMax());
    }
  }

  @Test
  public void test_issue_1515() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("test_issue_1515: " + i);
      final LocalDateTime datetime = generator.nextObject(LocalDateTime.class);
      System.out.println(datetime);
      assertEquals(0, datetime.getNano());
    }
  }

  @Test
  public void test_issue_1516() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("test_issue_1516: " + i);
      final LocalTime time = generator.nextObject(LocalTime.class);
      System.out.println(time);
      assertEquals(0, time.getNano());
    }
  }

  @Test
  public void testGenerateAbstractClass() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateAbstractClass: " + i);
      final AbstractFoo foo = generator.nextObject(AbstractFoo.class);
      System.out.println(foo);
    }
  }

  @Test
  public void testGenerateClassWithAbstractField() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateClassWithAbstractField: " + i);
      final BarBar bar = generator.nextObject(BarBar.class);
      System.out.println(bar);
    }
  }

  @Test
  public void testGenerateClassWithFinalField() {
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateClassWithFinalField: " + i);
      final DerivedClassFoo foo = generator.nextObject(DerivedClassFoo.class);
      System.out.println(foo);
      assertEquals("Foo", foo.getName());
      assertNotNull(foo.getEmail());

      final DerivedClassBar bar = generator.nextObject(DerivedClassBar.class);
      System.out.println(bar);
      assertEquals("Bar", bar.getName());
      assertNotNull(bar.getFieldWithDefaultValue());
      assertThat(bar.getFieldWithDefaultValue().length())
              .isGreaterThanOrEqualTo(3)
              .isLessThanOrEqualTo(5);
      assertEquals("YYYY", bar.getFinalFieldWithDefaultValue());
      assertNotNull(bar.getFinalFieldWithoutDefaultValue());
      assertThat(bar.getFinalFieldWithoutDefaultValue().length())
              .isGreaterThanOrEqualTo(4)
              .isLessThanOrEqualTo(10);
    }
  }

  @Test
  public void testGenerateMapSubclass() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> sizeRange = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateMapSubclass: " + i);
      final StringMap obj = generator.nextObject(StringMap.class);
      System.out.println(obj);
      assertThat(obj).isNotNull();
      assertThat(obj.size()).isGreaterThanOrEqualTo(sizeRange.getMin())
                            .isLessThanOrEqualTo(sizeRange.getMax());
    }
  }

  @Test
  public void testGenerateMapSubclass_2() {
    final Parameters parameters = generator.getParameters();
    final CloseRange<Integer> sizeRange = parameters.getCollectionSizeRange();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testGenerateMapSubclass: " + i);
      final MapSubclass obj = generator.nextObject(MapSubclass.class);
      System.out.println(obj);
      assertThat(obj).isNotNull();
      assertThat(obj.size())
              .isGreaterThanOrEqualTo(sizeRange.getMin())
              .isLessThanOrEqualTo(sizeRange.getMax());
      assertThat(obj.getAliases()).isNotNull();
      assertThat(obj.getAliases().size())
              .isGreaterThanOrEqualTo(sizeRange.getMin())
              .isLessThanOrEqualTo(sizeRange.getMax());
    }
  }

  @Test
  public void tetsGenerateComplexUniqueAnnotatedBean() {
    final Set<String> f1s = new HashSet<>();
    final Set<String> f2s = new HashSet<>();
    final Set<String> f4s = new HashSet<>();
    final Set<String> f5s = new HashSet<>();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final ComplexUniqueAnnotatedBean bean =
          generator.nextObject(ComplexUniqueAnnotatedBean.class);
      System.out.println(bean);
      final String f1 = bean.f2 + "$" + bean.f1;
      assertFalse(f1s.contains(f1));
      f1s.add(f1);
      final String f2 = bean.f2;
      if (f2 != null) {
        assertFalse(f2s.contains(f2));
      }
      f2s.add(f2);
      assertThat(bean.f3.size()).isGreaterThanOrEqualTo(3)
                                .isLessThanOrEqualTo(5);
      final String f4 = bean.f5 + "$"
          + bean.f6 + "$"
          + bean.f4;
      assertFalse(f4s.contains(f4));
      f4s.add(f4);
      final String f5 = bean.f6 + "$"
          + bean.f5;
      assertFalse(f5s.contains(f5));
      f5s.add(f5);
      assertThat(bean.f6.length()).isLessThanOrEqualTo(3);
    }
  }

  static class User {
    @Size(min = 1, max = 64)
    @Unique
    public String username;

    /**
     * 密码，通常从数据库内取出的是加盐后的哈希值。
      */
    @Size(min = 1, max = 64)
    public String password;

    @Override
    public String toString() {
      return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
          .add("username='" + username + "'")
          .add("password='" + password + "'")
          .toString();
    }
  }

  @Test
  public void test_issue_1632() {
    for (int i = 0; i < TEST_COUNT * 10; ++i) {
      final User user = generator.nextObject(User.class);
      System.out.println(user);
      assertNotNull(user.username);
      assertNotNull(user.password);
    }
  }

  @Test
  public void testIdentifierAnnotatedField() {
    final Set<String> values = new HashSet<>();
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testUniqueAnnotatedField: " + i);
      final IdentifierAnnotated obj = generator.nextObject(IdentifierAnnotated.class);
      assertNotNull(obj);
      assertNotNull(obj.f1);
      assertFalse(values.contains(obj.f1));
      values.add(obj.f1);
    }
  }

  static class Foo1804 {
    @Size(min = 1, max = 1)
    @Unique
    public String value;
  }

  static class Foo1804NotIgnoreCase {
    @Size(min = 1, max = 1)
    @Unique(ignoreCase = false)
    public String value;
  }

  @Test
  public void testUniqueFieldIgnoreCase_Fix1804() {
    final Set<String> values = new HashSet<>();
    for (int j = 0; j < TEST_COUNT; ++j) {
      values.clear();
      final RandomBeanGenerator g = new RandomBeanGenerator();
      for (int i = 0; i < 26; ++i) {
        System.out.println("testUniqueFieldIgnoreCase_Fix1804: " + i);
        final Foo1804 obj = g.nextObject(Foo1804.class);
        assertNotNull(obj);
        assertNotNull(obj.value);
        System.out.println("value = " + obj.value);
        final String key = obj.value.toUpperCase();
        assertFalse(values.contains(key));
        values.add(key);
      }
    }
  }

  @Test
  public void testUniqueFieldNotIgnoreCase_Fix1804() {
    final Set<String> values = new HashSet<>();
    for (int j = 0; j < TEST_COUNT; ++j) {
      values.clear();
      final RandomBeanGenerator g = new RandomBeanGenerator();
      for (int i = 0; i < 52; ++i) {
        System.out.println("testUniqueFieldNotIgnoreCase_Fix1804: " + i);
        final Foo1804NotIgnoreCase obj = g.nextObject(Foo1804NotIgnoreCase.class);
        assertNotNull(obj);
        assertNotNull(obj.value);
        System.out.println("value = " + obj.value);
        final String key = obj.value;
        assertFalse(values.contains(key));
        values.add(key);
      }
    }
  }

  @Test
  public void testGeneratePayload() {
    final RandomBeanGenerator g = new RandomBeanGenerator();
    final HashSet<String> keys = new HashSet<>();
    for (int i = 0; i < TEST_COUNT * TEST_COUNT; ++i) {
      final Payload p = g.nextObject(Payload.class);
      final String k = p.getIndexKey();
      assertFalse(keys.contains(k));
      keys.add(k);
    }
  }

  @Test
  public void testGenerateOrganization() {
    final RandomBeanGenerator g = new RandomBeanGenerator();
    final HashSet<String> keys = new HashSet<>();
    for (int i = 0; i < TEST_COUNT * TEST_COUNT; ++i) {
      final Organization o = g.nextObject(Organization.class);
      if (o.getPayloads() != null) {
        for (final Payload p : o.getPayloads()) {
          final String k = p.getIndexKey();
          assertFalse(keys.contains(k));
          keys.add(k);
        }
      }
    }
  }

  @Test
  public void testParameterNonNullFields() throws NoSuchFieldException {
    final Field f2 = NullableFieldObject.class.getDeclaredField("f2");
    System.out.println("Add the non-null field f2");
    generator.getParameters().addNonNullField(f2);
    final int[] nullCounts = {0, 0, 0, 0, 0};
    final int testCount = TEST_COUNT * 10;
    for (int i = 0; i < testCount; ++i) {
      final NullableFieldObject obj = generator.nextObject(NullableFieldObject.class);
      assertNotNull(obj);
      if (obj.f0 == null) {
        ++nullCounts[0];
      }
      if (obj.f1 == null) {
        ++nullCounts[1];
      }
      if (obj.f2 == null) {
        ++nullCounts[2];
      }
      if (obj.f3 == null) {
        ++nullCounts[3];
      }
      if (obj.f4 == null) {
        ++nullCounts[4];
      }
    }
    assertEquals(0, nullCounts[0]);
    assertEquals(0, nullCounts[1]);
    assertEquals(0, nullCounts[2]);
    assertEquals(0, nullCounts[3]);
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f4: %f\n", nullCounts[4] / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[4] / (double) testCount, EPSILON);
    // remove the non-null field f2
    System.out.println("Remove the non-null field f2");
    nullCounts[0] = nullCounts[1] = nullCounts[2] = nullCounts[3] = nullCounts[4] = 0;
    generator.getParameters().removeNonNullField(f2);
    for (int i = 0; i < testCount; ++i) {
      final NullableFieldObject obj = generator.nextObject(NullableFieldObject.class);
      assertNotNull(obj);
      if (obj.f0 == null) {
        ++nullCounts[0];
      }
      if (obj.f1 == null) {
        ++nullCounts[1];
      }
      if (obj.f2 == null) {
        ++nullCounts[2];
      }
      if (obj.f3 == null) {
        ++nullCounts[3];
      }
      if (obj.f4 == null) {
        ++nullCounts[4];
      }
    }
    assertEquals(0, nullCounts[0]);
    assertEquals(0, nullCounts[1]);
    assertEquals(0, nullCounts[3]);
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f2: %f\n", nullCounts[2] / (double) testCount);
    System.out.printf("Null ratio of f4: %f\n", nullCounts[4] / (double) testCount);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[2] / (double) testCount, EPSILON);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[4] / (double) testCount, EPSILON);
  }
}
