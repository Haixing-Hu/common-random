////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

  @Test
  void testRandomElementOf() {
    // Given
    final String[] elements = {"foo", "bar"};

    // When
    final String element = CollectionUtils.randomElementOf(asList(elements));

    // Then
    assertThat(element).isIn((Object[]) elements);
  }
}
