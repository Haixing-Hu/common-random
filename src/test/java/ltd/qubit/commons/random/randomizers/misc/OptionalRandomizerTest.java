////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.api.Randomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionalRandomizerTest {

  private static final String NAME = "foo";

  @Mock
  private Randomizer<String> randomizer;

  private OptionalRandomizer<String> optionalRandomizer;

  //  stop checkstyle: MagicNumberCheck
  @BeforeEach
  void setUp() {
    when(randomizer.getRandomValue()).thenReturn(NAME);
    optionalRandomizer = new OptionalRandomizer<>(randomizer, 100);
  }

  @Test
  void whenOptionalPercentIsOneHundredThenShouldGenerateValue() {
    assertThat(optionalRandomizer.getRandomValue())
            .isEqualTo(NAME);
  }
  //  resume checkstyle: MagicNumberCheck
}
