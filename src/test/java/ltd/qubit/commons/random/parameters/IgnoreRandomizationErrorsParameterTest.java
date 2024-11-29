////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.util.concurrent.Callable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.Parameters;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IgnoreRandomizationErrorsParameterTest {

  private EasyRandom easyRandom;

  @Test
  void whenIgnoreRandomizationErrorsIsActivated_thenShouldReturnNull() {
    final Parameters parameters = new Parameters().ignoreRandomizationErrors(true);
    easyRandom = new EasyRandom(parameters);

    final Foo foo = easyRandom.nextObject(Foo.class);

    Assertions.assertThat(foo).isNotNull();
    Assertions.assertThat(foo.getName()).isNotNull();
    Assertions.assertThat(foo.getCallable()).isNull();
  }

  @Test
  void whenIgnoreRandomizationErrorsIsDeactivated_thenShouldThrowObjectGenerationException() {
    final Parameters parameters = new Parameters().ignoreRandomizationErrors(false);
    easyRandom = new EasyRandom(parameters);

    assertThatThrownBy(() -> easyRandom.nextObject(Foo.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  static class Foo {
    private String name;
    private Callable<String> callable;

    public Foo() {
    }

    public String getName() {
      return this.name;
    }

    public Callable<String> getCallable() {
      return this.callable;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setCallable(final Callable<String> callable) {
      this.callable = callable;
    }
  }
}
