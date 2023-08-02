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

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.beans.Foo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.singleton;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class RegistriesRandomizerProviderTest {

  @Mock
  private RandomizerRegistry randomizerRegistry;
  @SuppressWarnings("rawtypes")
  @Mock
  private Randomizer randomizer;
  @Mock
  private Context context;

  private RegistriesRandomizerProvider randomizerProvider;

  @BeforeEach
  void setUp() {
    randomizerProvider = new RegistriesRandomizerProvider();
    randomizerProvider.addRegistries(singleton(randomizerRegistry));
  }

  @Test
  void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenField()
          throws NoSuchFieldException {
    // Given
    final Field field = Foo.class.getDeclaredField("bar");
    when(randomizerRegistry.get(field, context)).thenReturn(randomizer);
    // When
    final Randomizer<?> actual = randomizerProvider.getByField(field, context);
    // Then
    assertThat(actual).isEqualTo(randomizer);
  }

  @Test
  void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenType() {
    // Given
    final Class<String> type = String.class;
    when(randomizerRegistry.get(type, context)).thenReturn(randomizer);
    // When
    final Randomizer<?> actual = randomizerProvider.getByType(type, context);
    // Then
    assertThat(actual).isEqualTo(randomizer);
  }
}
