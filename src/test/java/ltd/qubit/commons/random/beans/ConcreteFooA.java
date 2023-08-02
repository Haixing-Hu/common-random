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

public class ConcreteFooA extends AbstractFoo {

  private Integer value;

  public final Integer getValue() {
    return value;
  }

  public final ConcreteFooA setValue(final Integer value) {
    this.value = value;
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
    if (!super.equals(o)) {
      return false;
    }
    final ConcreteFooA that = (ConcreteFooA) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), value);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ConcreteFooA.class.getSimpleName() + "[", "]")
            .add("value=" + value)
            .toString();
  }
}
