////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ltd.qubit.commons.random.beans.Human;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DefaultExclusionPolicyTest {

  @Mock
  private Context randomizerContext;

  private DefaultExclusionPolicy exclusionPolicy;

  @BeforeEach
  void setUp() {
    exclusionPolicy = new DefaultExclusionPolicy();
  }

  @Test
  void staticFieldsShouldBeExcluded() throws NoSuchFieldException {
    // Given
    final Field field = Human.class.getDeclaredField("SERIAL_VERSION_UID");

    // When
    final boolean actual = exclusionPolicy.shouldBeExcluded(field, randomizerContext);

    // Then
    assertThat(actual).isTrue();
  }

}
