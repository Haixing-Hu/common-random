////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.BeanWithDefaultFieldValues;

import static org.assertj.core.api.Assertions.assertThat;

class OverrideDefaultInitializationParameterTest {

  @Test
  void whenOverrideDefaultInitializationParameterIsFalse_thenShouldKeepDefaultFieldValues() {
    // Given
    final Parameters parameters = new Parameters().overrideDefaultInitialization(false);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // When
    final BeanWithDefaultFieldValues bean = easyRandom
        .nextObject(BeanWithDefaultFieldValues.class);

    // Then
    assertThat(bean.getDefaultNonNullValue()).isEqualTo("default");
    assertThat(bean.getDefaultNonNullValueSetByConstructor())
        .isEqualTo("defaultSetByConstructor");
  }

  @Test
  void whenOverrideDefaultInitializationParameterIsTrue_thenShouldRandomizeFields() {
    // Given
    final Parameters parameters = new Parameters().overrideDefaultInitialization(true);
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // When
    final BeanWithDefaultFieldValues bean =
        easyRandom.nextObject(BeanWithDefaultFieldValues.class);

    // Then
    assertThat(bean.getDefaultNonNullValue()).isNotEqualTo("default")
                                             .isNotNull();
    assertThat(bean.getDefaultNonNullValueSetByConstructor())
        .isNotEqualTo("defaultSetByConstructor")
        .isNotNull();
  }

  @Test
  void shouldNotOverrideDefaultFieldValuesByDefault() {
    // When
    final BeanWithDefaultFieldValues bean = new EasyRandom()
        .nextObject(BeanWithDefaultFieldValues.class);

    // Then
    assertThat(bean.getDefaultNonNullValue()).isEqualTo("default");
    assertThat(bean.getDefaultNonNullValueSetByConstructor())
        .isEqualTo("defaultSetByConstructor");
  }
}
