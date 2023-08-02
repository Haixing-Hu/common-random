////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.visibility;

import java.util.function.Supplier;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.util.ReflectionUtils.asRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

class VisibilityTest {

  @Test
  void canPassSupplierLambdaFromOtherPackage() {
    final Supplier<String> supplier = () -> "test";
    final Parameters parameters = new Parameters()
            .randomize(String.class, asRandomizer(supplier));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    final String value = easyRandom.nextObject(String.class);

    assertThat(value).isEqualTo("test");
  }
}
