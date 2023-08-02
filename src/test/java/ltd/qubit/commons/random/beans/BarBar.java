////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

public class BarBar {

  private String value;
  private AbstractFoo foo;

  public final String getValue() {
    return value;
  }

  public final BarBar setValue(final String value) {
    this.value = value;
    return this;
  }

  public final AbstractFoo getFoo() {
    return foo;
  }

  public final BarBar setFoo(final AbstractFoo foo) {
    this.foo = foo;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BarBar bar = (BarBar) o;
    return Objects.equals(value, bar.value)
        && Objects.equals(foo, bar.foo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, foo);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BarBar.class.getSimpleName() + "[", "]")
            .add("value='" + value + "'")
            .add("foo=" + foo)
            .toString();
  }
}
