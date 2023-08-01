////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import ltd.qubit.commons.random.api.Randomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.String.valueOf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StringDelegatingRandomizerTest {

  @Mock
  private Randomizer<Object> delegate;
  @Mock
  private Object object;

  private StringDelegatingRandomizer stringDelegatingRandomizer;

  @BeforeEach
  void setUp() {
    stringDelegatingRandomizer = new StringDelegatingRandomizer(delegate);
    when(delegate.getRandomValue()).thenReturn(object);
  }

  @Test
  void generatedValueShouldTheSameAs() {
    final String actual = stringDelegatingRandomizer.getRandomValue();

    assertThat(actual).isEqualTo(valueOf(object));
  }
}
