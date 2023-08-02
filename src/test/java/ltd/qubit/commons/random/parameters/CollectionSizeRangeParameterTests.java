////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.util.ArrayList;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionSizeRangeParameterTests {

  //  stop checkstyle: MagicNumberCheck
  @Test
  void shouldNotAllowNegativeMinCollectionSize() {
    assertThatThrownBy(() -> new Parameters().collectionSizeRange(-1, 10))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void shouldNotAllowMinCollectionSizeGreaterThanMaxCollectionSize() {
    assertThatThrownBy(() -> new Parameters().collectionSizeRange(2, 1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void generatedCollectionSizeShouldBeInSpecifiedRange() {
    final Parameters parameters = new Parameters().collectionSizeRange(0, 10);
    assertThat(new EasyRandom(parameters).nextObject(ArrayList.class)
                                         .size()).isBetween(0, 10);
  }

  @Test
  // https://github.com/j-easy/easy-random/issues/191
  void collectionSizeRangeShouldWorkForArrays() {
    final Parameters parameters = new Parameters().collectionSizeRange(0, 10);

    final String[] strArr = new EasyRandom(parameters).nextObject(String[].class);

    assertThat(strArr.length).isLessThanOrEqualTo(10);
  }
  //  resume checkstyle: MagicNumberCheck
}
