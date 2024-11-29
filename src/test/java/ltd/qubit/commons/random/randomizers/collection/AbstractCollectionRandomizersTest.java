////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.Collection;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ltd.qubit.commons.random.api.Randomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractCollectionRandomizersTest {

  private static final int collectionSize = 3;

  static Object[] generateCollectionRandomizers() {
    final Randomizer<String> elementRandomizer = elementRandomizer();
    return new Object[]{
        new ListRandomizer<>(elementRandomizer),
        new QueueRandomizer<>(elementRandomizer),
        new SetRandomizer<>(elementRandomizer)};
  }

  @ParameterizedTest
  @MethodSource("generateCollectionRandomizers")
  <T> void generatedCollectionShouldNotBeNull(
      final Randomizer<Collection<T>> collectionRandomizer) {
    // when
    final Collection<T> randomCollection = collectionRandomizer.getRandomValue();

    then(randomCollection).isNotNull();
  }

  static Object[] generateCollectionRandomizersWithSpecificSize() {
    return new Object[]{
        new ListRandomizer<>(elementRandomizer(), collectionSize),
        new QueueRandomizer<>(elementRandomizer(), collectionSize),
        new SetRandomizer<>(elementRandomizer(), collectionSize)};
  }

  @ParameterizedTest
  @MethodSource("generateCollectionRandomizersWithSpecificSize")
  <T> void generatedCollectionSizeShouldBeEqualToTheSpecifiedSize(
      final Randomizer<Collection<T>> collectionRandomizer) {
    // when
    final Collection<T> randomCollection = collectionRandomizer.getRandomValue();

    then(randomCollection).hasSize(collectionSize);
  }

  static Object[] generateCollectionRandomizersForEmptyCollections() {
    return new Object[]{
        new ListRandomizer<>(elementRandomizer(), 0),
        new QueueRandomizer<>(elementRandomizer(), 0),
        new SetRandomizer<>(elementRandomizer(), 0)};
  }

  @ParameterizedTest
  @MethodSource("generateCollectionRandomizersForEmptyCollections")
  <T> void shouldAllowGeneratingEmptyCollections(
      final Randomizer<Collection<T>> collectionRandomizer) {
    // when
    final Collection<T> randomCollection = collectionRandomizer.getRandomValue();

    then(randomCollection).isEmpty();
  }

  static Object[] generateCollectionRandomizersWithIllegalSize() {
    final Randomizer<String> elementRandomizer = elementRandomizer();
    final int illegalSize = -1;
    return new Object[]{
        (ThrowingCallable) () -> new ListRandomizer<>(elementRandomizer,
            illegalSize),
        (ThrowingCallable) () -> new QueueRandomizer<>(elementRandomizer,
            illegalSize),
        (ThrowingCallable) () -> new SetRandomizer<>(elementRandomizer,
            illegalSize)};
  }

  @ParameterizedTest
  @MethodSource("generateCollectionRandomizersWithIllegalSize")
  void specifiedSizeShouldBePositive(final ThrowingCallable callable) {
    // when
    final Throwable expectedException = catchThrowable(callable);

    // then
    assertThat(expectedException).isInstanceOf(IllegalArgumentException.class);
  }

  @SuppressWarnings("unchecked")
  static Randomizer<String> elementRandomizer() {
    final Randomizer<String> elementRandomizer = mock(Randomizer.class);
    when(elementRandomizer.getRandomValue()).thenReturn("a", "b", "c");
    return elementRandomizer;
  }
}
