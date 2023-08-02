////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static ltd.qubit.commons.random.FieldPredicates.named;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContextTest {

  @Mock
  private Object bean1;
  @Mock
  private Object bean2;
  @Mock
  private Parameters parameters;

  private Context context;

  @BeforeEach
  void setUp() {
    context = new Context(Object.class, parameters);
  }

  @Test
  void whenATypeHasBeenRandomized_populatedBeanShouldReturnTrueOnlyWhenTheObjectPoolIsFilled() {
    when(parameters.getObjectPoolSize()).thenReturn(Parameters.DEFAULT_OBJECT_POOL_SIZE);

    // Only one instance has been randomized => should be considered as not randomized yet
    context.addPopulatedBean(String.class, "bean" + 0);
    assertThat(context.hasAlreadyRandomizedType(String.class)).isFalse();

    // When the object pool size is filled => should be considered as already randomized
    for (int i = 1; i < Parameters.DEFAULT_OBJECT_POOL_SIZE; i++) {
      context.addPopulatedBean(String.class, "bean" + i);
    }
    assertThat(context.hasAlreadyRandomizedType(String.class)).isTrue();
  }

  @Test
  void whenATypeHasNotBeenRandomizedYet_thenHasPopulatedBeanShouldReturnFalse() {
    // Given
    context.addPopulatedBean(String.class, bean1);

    // When
    final boolean hasPopulatedBean = context.hasAlreadyRandomizedType(Integer.class);

    // Then
    assertThat(hasPopulatedBean).isFalse();
  }

  @Test
  void whenATypeHasBeenRandomized_randomizedBeanShouldBeRetrievedFromTheObjectPool() {
    when(parameters.getObjectPoolSize()).thenReturn(Parameters.DEFAULT_OBJECT_POOL_SIZE);

    // Given
    context.addPopulatedBean(String.class, bean1);
    context.addPopulatedBean(String.class, bean2);

    // When
    final Object populatedBean = context.getPopulatedBean(String.class);

    // Then
    assertThat(populatedBean).isIn(bean1, bean2);
  }

  @Test
  void stackedFieldNamesShouldBeCorrectlyEncoded() throws NoSuchFieldException {
    // Given
    final Field address = Person.class.getDeclaredField("address");
    context.pushStackItem(new ContextStackItem(null, address));
    final Field street = Address.class.getDeclaredField("street");

    // When
    final String fullFieldName = context.getFieldFullName(street);

    // Then
    assertThat(fullFieldName).isEqualTo("address.street");
  }

  @Test
  void whenCurrentStackSizeOverMaxRandomizationDepth_thenShouldExceedRandomizationDepth()
      throws NoSuchFieldException {
    // Given
    when(parameters.getRandomizationDepth()).thenReturn(1);
    final Context customContext = new Context(Object.class, parameters);
    final Field address = Person.class.getDeclaredField("address");
    customContext.pushStackItem(new ContextStackItem(bean1, address));
    customContext.pushStackItem(new ContextStackItem(bean2, address));

    // When
    final boolean hasExceededRandomizationDepth = customContext.hasExceededRandomizationDepth();

    // Then
    assertThat(hasExceededRandomizationDepth).isTrue();
  }

  @Test
  void whenCurrentStackSizeLessMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth()
      throws NoSuchFieldException {
    // Given
    when(parameters.getRandomizationDepth()).thenReturn(2);
    final Context customContext = new Context(Object.class, parameters);
    final Field address = Person.class.getDeclaredField("address");
    customContext.pushStackItem(new ContextStackItem(bean1, address));

    // When
    final boolean hasExceededRandomizationDepth = customContext.hasExceededRandomizationDepth();

    // Then
    assertThat(hasExceededRandomizationDepth).isFalse();
  }

  @Test
  void whenCurrentStackSizeEqualMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth()
      throws NoSuchFieldException {
    // Given
    when(parameters.getRandomizationDepth()).thenReturn(2);
    final Context customContext = new Context(Object.class, parameters);
    final Field address = Person.class.getDeclaredField("address");
    customContext.pushStackItem(new ContextStackItem(bean1, address));
    customContext.pushStackItem(new ContextStackItem(bean2, address));

    // When
    final boolean hasExceededRandomizationDepth = customContext.hasExceededRandomizationDepth();

    // Then
    assertThat(hasExceededRandomizationDepth).isFalse();
  }

  @Test
  void testRandomizerContext() {
    // given
    final MyRandomizer randomizer = new MyRandomizer();
    final Parameters parameters = new Parameters()
            .randomize(D.class, randomizer)
            .randomize(FieldPredicates.isAnnotatedWith(ExampleAnnotation.class), new ERandomizer())
            .excludeField(named("excluded"));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final A a = easyRandom.nextObject(A.class);

    // then
    assertThat(a).isNotNull();
    assertThat(a.excluded).isNull();
    assertThat(a.bb).isNotNull();
    assertThat(a.bb.cc).isNotNull();
    assertThat(a.bb.ee).isNotNull();
    assertThat(a.bb.cc.dd).isNotNull();
    assertThat(a.bb.cc.dd.name).isEqualTo("foo");
    assertThat(a.bb.ee.name).isEqualTo("bar");
  }

  static class MyRandomizer implements ContextAwareRandomizer<D> {

    private Context context;

    @Override
    public void setContext(final Context context) {
      this.context = context;
    }

    @Override
    public D getRandomValue() {
      // At this level, the context should be as follows:
      assertThat(context.getCurrentFieldPath()).isEqualTo("bb.cc.dd");
      assertThat(context.getCurrentRandomizationDepth()).isEqualTo(3);
      assertThat(context.getTargetType()).isEqualTo(A.class);
      assertThat(context.getRootObject()).isInstanceOf(A.class);
      assertThat(context.getCurrentObject()).isInstanceOf(C.class);

      final D d = new D();
      d.setName("foo");
      return d;
    }
  }

  static class ERandomizer implements ContextAwareRandomizer<E> {

    private Context context;

    @Override
    public void setContext(final Context context) {
      this.context = context;
    }

    @Override
    public E getRandomValue() {
      // At this level, the context should be as follows:
      assertThat(context.getCurrentFieldPath()).isEqualTo("bb.ee");
      assertThat(context.getCurrentRandomizationDepth()).isEqualTo(2);
      assertThat(context.getTargetType()).isEqualTo(A.class);
      assertThat(context.getRootObject()).isInstanceOf(A.class);
      assertThat(context.getCurrentObject()).isInstanceOf(B.class);

      final E e = new E();
      final String currentField = context.getCurrentFieldPath();
      final Object currentObject = context.getCurrentObject();
      try {
        final String substring = currentField.substring(currentField.lastIndexOf(".") + 1);
        e.name = currentObject.getClass()
                              .getDeclaredField(substring)
                              .getAnnotation(ExampleAnnotation.class)
                              .value();
      } catch (final NoSuchFieldException ex) {
        e.name = "default";
      }
      return e;
    }
  }

  static class A {
    private B bb;
    private String excluded;

    public A() {
    }

    public B getBb() {
      return this.bb;
    }

    public String getExcluded() {
      return this.excluded;
    }

    public void setBb(final B bb) {
      this.bb = bb;
    }

    public void setExcluded(final String excluded) {
      this.excluded = excluded;
    }
  }

  static class B {
    private C cc;

    @ExampleAnnotation("bar")
    private E ee;

    public B() {
    }

    public C getCc() {
      return this.cc;
    }

    public E getEe() {
      return this.ee;
    }

    public void setCc(final C cc) {
      this.cc = cc;
    }

    public void setEe(final E ee) {
      this.ee = ee;
    }
  }

  static class C {
    private D dd;

    public C() {
    }

    public D getDd() {
      return this.dd;
    }

    public void setDd(final D dd) {
      this.dd = dd;
    }
  }

  static class D {
    private String name;

    public D() {
    }

    public String getName() {
      return this.name;
    }

    public void setName(final String name) {
      this.name = name;
    }
  }

  static class E {
    private String name;

    public E() {
    }

    public String getName() {
      return this.name;
    }

    public void setName(final String name) {
      this.name = name;
    }
  }

  @Target({FIELD})
  @Retention(RUNTIME)
  @Documented
  public @interface ExampleAnnotation {
    String value();
  }

}
