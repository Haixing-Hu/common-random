////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;

import ltd.qubit.commons.random.beans.Bar;
import ltd.qubit.commons.random.beans.CustomList;
import ltd.qubit.commons.random.beans.CustomMap;
import ltd.qubit.commons.random.beans.Foo;
import ltd.qubit.commons.random.beans.Gender;
import ltd.qubit.commons.random.beans.Human;
import ltd.qubit.commons.random.beans.Mammal;
import ltd.qubit.commons.random.beans.MammalImpl;
import ltd.qubit.commons.random.beans.SocialPerson;
import ltd.qubit.commons.reflect.FieldUtils;

import org.junit.jupiter.api.Test;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static ltd.qubit.commons.random.util.ReflectionUtils.fieldHasDefaultValue;
import static ltd.qubit.commons.random.util.ReflectionUtils.getDeclaredFields;
import static ltd.qubit.commons.random.util.ReflectionUtils.getEmptyCollectionForType;
import static ltd.qubit.commons.random.util.ReflectionUtils.getEmptyImplementationForCollectionInterface;
import static ltd.qubit.commons.random.util.ReflectionUtils.getEmptyImplementationForMapInterface;
import static ltd.qubit.commons.random.util.ReflectionUtils.getInheritedFields;
import static ltd.qubit.commons.random.util.ReflectionUtils.getPopulatableFields;
import static ltd.qubit.commons.random.util.ReflectionUtils.getWrapperType;
import static ltd.qubit.commons.random.util.ReflectionUtils.isPrimitiveFieldWithDefaultValue;
import static ltd.qubit.commons.reflect.ClassUtils.isAbstract;
import static ltd.qubit.commons.reflect.ClassUtils.isArrayType;
import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;
import static ltd.qubit.commons.reflect.ClassUtils.isEnumType;
import static ltd.qubit.commons.reflect.ClassUtils.isInterface;
import static ltd.qubit.commons.reflect.ClassUtils.isJdkBuiltIn;
import static ltd.qubit.commons.reflect.ClassUtils.isMapType;
import static ltd.qubit.commons.reflect.ClassUtils.isPublic;
import static ltd.qubit.commons.reflect.FieldUtils.getReadMethod;
import static ltd.qubit.commons.reflect.FieldUtils.isStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionUtilsTest {

  // stop checkstyle: MagicNumberCheck
  private static final int INITIAL_CAPACITY = 10;

  private List<Field> filterIgnoredFields(final List<Field> fields) {
    final List<Field> result = new ArrayList<>();
    for (final Field field : fields) {
      final String name = field.getName();
      boolean ignored = false;
      for (final String prefix : FieldUtils.IGNORED_FIELD_PREFIXES) {
        if (name.startsWith(prefix)) {
          ignored = true;
          break;
        }
      }
      if (!ignored) {
        result.add(field);
      }
    }
    return result;
  }

  @Test
  void testGetDeclaredFields() {
    final BigDecimal javaVersion = new BigDecimal(System.getProperty("java.specification.version"));
    System.out.println("Java version: " + javaVersion);
    final List<Field> fields = filterIgnoredFields(getDeclaredFields(
        SocialPerson.class));
    if (javaVersion.compareTo(new BigDecimal("17")) >= 0) {
      assertThat(fields).hasSize(21);
    } else if (javaVersion.compareTo(new BigDecimal("15")) >= 0) {
      assertThat(fields).hasSize(23);
    } else if (javaVersion.compareTo(new BigDecimal("14")) >= 0) {
      assertThat(fields).hasSize(22);
    } else if (javaVersion.compareTo(new BigDecimal("12")) >= 0) {
      assertThat(fields).hasSize(21);
    } else if (javaVersion.compareTo(new BigDecimal("9")) >= 0) {
      assertThat(fields).hasSize(22);
    } else {
      assertThat(fields).hasSize(20);
    }
  }

  @Test
  void testGetInheritedFields() {
    final List<Field> fields = filterIgnoredFields(getInheritedFields(SocialPerson.class));
    assertThat(fields).hasSize(11);
  }

  @Test
  void testIsStatic() throws Exception {
    assertThat(isStatic(Human.class.getField("SERIAL_VERSION_UID")))
            .isTrue();
  }

  @Test
  void testIsInterface() {
    assertThat(isInterface(List.class)).isTrue();
    assertThat(isInterface(Mammal.class)).isTrue();

    assertThat(isInterface(MammalImpl.class)).isFalse();
    assertThat(isInterface(ArrayList.class)).isFalse();
  }

  @Test
  void testIsAbstract() {
    assertThat(isAbstract(Foo.class)).isFalse();

    assertThat(isAbstract(Bar.class)).isTrue();
  }

  @Test
  void testIsPublic() {
    assertThat(isPublic(Foo.class)).isTrue();
    assertThat(isPublic(Dummy.class)).isFalse();
  }

  @Test
  void testIsArrayType() {
    assertThat(isArrayType(int[].class)).isTrue();
    assertThat(isArrayType(Foo.class)).isFalse();
  }

  @Test
  void testIsEnumType() {
    assertThat(isEnumType(Gender.class)).isTrue();
    assertThat(isEnumType(Foo.class)).isFalse();
  }

  @Test
  void testIsCollectionType() {
    assertThat(isCollectionType(CustomList.class)).isTrue();
    assertThat(isCollectionType(Foo.class)).isFalse();
  }

  @Test
  void testIsMapType() {
    assertThat(isMapType(CustomMap.class)).isTrue();
    assertThat(isMapType(Foo.class)).isFalse();
  }

  @Test
  void testIsJdkBuiltIn() {
    assertThat(isJdkBuiltIn(ArrayList.class)).isTrue();
    assertThat(isJdkBuiltIn(CustomList.class)).isFalse();
  }

  @Test
  void getWrapperTypeTest() {
    assertThat(getWrapperType(Byte.TYPE)).isEqualTo(Byte.class);
    assertThat(getWrapperType(Short.TYPE)).isEqualTo(Short.class);
    assertThat(getWrapperType(Integer.TYPE)).isEqualTo(Integer.class);
    assertThat(getWrapperType(Long.TYPE)).isEqualTo(Long.class);
    assertThat(getWrapperType(Double.TYPE)).isEqualTo(Double.class);
    assertThat(getWrapperType(Float.TYPE)).isEqualTo(Float.class);
    assertThat(getWrapperType(Boolean.TYPE)).isEqualTo(Boolean.class);
    assertThat(getWrapperType(Character.TYPE)).isEqualTo(Character.class);
    assertThat(getWrapperType(String.class)).isEqualTo(String.class);
  }

  @Test
  void testIsPrimitiveFieldWithDefaultValue() throws Exception {
    final Class<PrimitiveFieldsWithDefaultValuesBean> defaultValueClass
        = PrimitiveFieldsWithDefaultValuesBean.class;
    final PrimitiveFieldsWithDefaultValuesBean defaultValueBean =
        new PrimitiveFieldsWithDefaultValuesBean();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("bool"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("bb"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("ss"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("ii"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("ll"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("ff"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("dd"))).isTrue();
    assertThat(isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass
            .getField("cc"))).isTrue();

    final Class<PrimitiveFieldsWithNonDefaultValuesBean> nonDefaultValueClass =
        PrimitiveFieldsWithNonDefaultValuesBean.class;
    final PrimitiveFieldsWithNonDefaultValuesBean nonDefaultValueBean =
        new PrimitiveFieldsWithNonDefaultValuesBean();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("bool"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("bb"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("ss"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("ii"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("ll"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("ff"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("dd"))).isFalse();
    assertThat(isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass
            .getField("cc"))).isFalse();
  }

  @Test
  void testGetReadMethod() throws NoSuchFieldException {
    assertNull(getReadMethod(PrimitiveFieldsWithDefaultValuesBean.class.getDeclaredField(
        "bb")));
    final Method readMethod = getReadMethod(Foo.class.getDeclaredField("bar"));
    assertNotNull(readMethod);
    assertThat(readMethod.getName()).isEqualTo("getBar");
  }

  @Test
  void testGetEmptyImplementationForCollectionInterface() {
    final Collection<?> collection = getEmptyImplementationForCollectionInterface(List.class);

    assertThat(collection).isInstanceOf(ArrayList.class).isEmpty();
  }

  @Test
  void createEmptyCollectionForArrayBlockingQueue() {
    final Collection<?> collection =
        getEmptyCollectionForType(ArrayBlockingQueue.class, INITIAL_CAPACITY);

    assertThat(collection).isInstanceOf(ArrayBlockingQueue.class).isEmpty();
    assertThat(((ArrayBlockingQueue<?>) collection)
        .remainingCapacity()).isEqualTo(INITIAL_CAPACITY);
  }

  @Test
  void synchronousQueueShouldBeRejected() {
    assertThatThrownBy(() -> getEmptyCollectionForType(SynchronousQueue.class, INITIAL_CAPACITY))
            .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void delayQueueShouldBeRejected() {
    assertThatThrownBy(() -> getEmptyCollectionForType(DelayQueue.class, INITIAL_CAPACITY))
            .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void testGetEmptyImplementationForMapInterface() {
    final Map<?, ?> map = getEmptyImplementationForMapInterface(SortedMap.class);
    assertThat(map).isInstanceOf(TreeMap.class).isEmpty();
  }

  @Test
  void testFieldHasDefaultValue() throws Exception {
    final PrimitiveFieldsWithDefaultValuesBean obj1 =
        new PrimitiveFieldsWithDefaultValuesBean();
    for (final Field field :
        getPopulatableFields(PrimitiveFieldsWithDefaultValuesBean.class, obj1)) {
      assertTrue(fieldHasDefaultValue(obj1, field), "Field: " + field.getName());
    }
    final PrimitiveFieldsWithNonDefaultValuesBean obj2 =
        new PrimitiveFieldsWithNonDefaultValuesBean();
    for (final Field field :
        getPopulatableFields(PrimitiveFieldsWithNonDefaultValuesBean.class, obj2)) {
      assertFalse(fieldHasDefaultValue(obj2, field), "Field: " + field.getName());
    }
    final AnnotatedBean obj3 = new AnnotatedBean();
    obj3.setFieldAnnotation("xxx");
    obj3.setMethodAnnotation("");
    assertFalse(fieldHasDefaultValue(obj3,
        AnnotatedBean.class.getDeclaredField("fieldAnnotation")));

    assertFalse(fieldHasDefaultValue(obj3,
        AnnotatedBean.class.getDeclaredField("methodAnnotation")));
    assertTrue(fieldHasDefaultValue(obj3,
        AnnotatedBean.class.getDeclaredField("noAnnotation")));
  }

  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface NotNull {
    //  empty
  }

  public static class AnnotatedBean {
    @NotNull
    private String fieldAnnotation;
    private String methodAnnotation;
    private String noAnnotation;

    public String getFieldAnnotation() {
      return fieldAnnotation;
    }

    public void setFieldAnnotation(final String fieldAnnotation) {
      this.fieldAnnotation = fieldAnnotation;
    }

    @NotNull
    public String getMethodAnnotation() {
      return methodAnnotation;
    }

    public void setMethodAnnotation(final String methodAnnotation) {
      this.methodAnnotation = methodAnnotation;
    }

    public String getNoAnnotation() {
      return noAnnotation;
    }

    public void setNoAnnotation(final String noAnnotation) {
      this.noAnnotation = noAnnotation;
    }
  }

  @SuppressWarnings("unused")
  private class PrimitiveFieldsWithDefaultValuesBean {
    public boolean bool;
    public byte bb;
    public short ss;
    public int ii;
    public long ll;
    public float ff;
    public double dd;
    public char cc;
  }

  @SuppressWarnings("unused")
  private class PrimitiveFieldsWithNonDefaultValuesBean {
    public boolean bool = true;
    public byte bb = (byte) 1;
    public short ss = (short) 1;
    public int ii = 1;
    public long ll = 1L;
    public float ff = 1.0F;
    public double dd = 1.0D;
    public char cc = 'a';
  }

  private class Dummy {}

  // resume checkstyle: MagicNumberCheck
}
