////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.util.function.Supplier;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.util.ReflectionUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RandomizerProxyTest {

  private static final String FOO = "foo";

  @Test
  void theRandomizerProxyShouldBehaveLikeTheSupplier() {
    // Given
    final MySupplier supplier = new MySupplier();

    // When
    final Randomizer<?> randomizer = ReflectionUtils.asRandomizer(supplier);

    // Then
    assertThat(randomizer.getRandomValue()).isInstanceOf(String.class)
                                           .isEqualTo(FOO);
  }

  @Test
  void theRandomizerProxyShouldBehaveLikeTheSupplierDefinedWithLambda() {
    // Given
    final Supplier<String> supplier = () -> FOO;

    // When
    final Randomizer<?> randomizer = ReflectionUtils.asRandomizer(supplier);

    // Then
    assertThat(randomizer.getRandomValue()).isInstanceOf(String.class)
                                           .isEqualTo(FOO);
  }

  private static class MySupplier implements Supplier<String> {

    @Override
    public String get() {
      return FOO;
    }

  }
}
