////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import ltd.qubit.commons.random.api.Randomizer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MapRandomizerTest {

  //  stop checkstyle: MagicNumberCheck
  @Mock
  private Randomizer<Integer> keyRandomizer;
  @Mock
  private Randomizer<String> valueRandomizer;

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void generatedMapShouldNotBeEmpty() {
    assertThat(new MapRandomizer(keyRandomizer, valueRandomizer).getRandomValue())
            .isNotEmpty();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void generatedMapSizeShouldBeEqualToTheSpecifiedSize() {
    when(keyRandomizer.getRandomValue()).thenReturn(1, 2, 3);
    assertThat(new MapRandomizer(keyRandomizer, valueRandomizer, 3).getRandomValue())
            .hasSize(3);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void specifiedSizeCanBeZero() {
    assertThat(new MapRandomizer(keyRandomizer, valueRandomizer, 0).getRandomValue())
            .isEmpty();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void specifiedSizeShouldBePositive() {
    assertThatThrownBy(() -> new MapRandomizer(keyRandomizer, valueRandomizer, -3))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void nullKeyRandomizer() {
    assertThatThrownBy(() -> new MapRandomizer(null, valueRandomizer, 3))
            .isInstanceOf(IllegalArgumentException.class);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void nullValueRandomizer() {
    assertThatThrownBy(() -> new MapRandomizer(keyRandomizer, null, 3))
            .isInstanceOf(IllegalArgumentException.class);
  }
  //  resume checkstyle: MagicNumberCheck
}
