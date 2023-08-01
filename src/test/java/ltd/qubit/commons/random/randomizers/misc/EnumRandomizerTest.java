////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.randomizers.misc.EnumRandomizerTest.Gender.FEMALE;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnumRandomizerTest extends
        AbstractRandomizerTest<EnumRandomizerTest.Gender> {

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void generatedValueShouldBeOfTheSpecifiedEnum() {
    assertThat(new EnumRandomizer(Gender.class).getRandomValue())
        .isIn((Object[]) Gender.values());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
    assertThat(new EnumRandomizer(Gender.class, SEED).getRandomValue())
        .isEqualTo(FEMALE);
  }

  public enum Gender {
    MALE,
    FEMALE
  }

  @Test
  void should_return_a_value_different_from_the_excluded_one() {
    final Gender valueToExclude = Gender.MALE;
    final Gender randomElement = new EnumRandomizer<>(Gender.class, valueToExclude)
        .getRandomValue();
    assertThat(randomElement).isNotNull();
    assertThat(randomElement).isNotEqualTo(valueToExclude);
  }

  @Test
  void should_throw_an_exception_when_all_values_are_excluded() {
    assertThatThrownBy(() -> new EnumRandomizer<>(Gender.class, Gender.values()))
            .isInstanceOf(IllegalArgumentException.class);
  }

  public enum Empty {}

  @Test
  public void should_return_null_for_empty_enum() {
    final Empty randomElement = new EnumRandomizer<>(Empty.class).getRandomValue();
    assertThat(randomElement).isNull();
  }
}
