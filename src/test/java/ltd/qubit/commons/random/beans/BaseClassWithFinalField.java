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

public class BaseClassWithFinalField {

  private final String name;

  public BaseClassWithFinalField(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BaseClassWithFinalField that = (BaseClassWithFinalField) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BaseClassWithFinalField.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .toString();
  }
}
