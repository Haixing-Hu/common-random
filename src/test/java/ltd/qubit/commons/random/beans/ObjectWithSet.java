////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import javax.validation.constraints.Size;

public class ObjectWithSet {

  @Size(min = 8, max = 12)
  private Set<Integer> intSet;


  @Size(min = 8, max = 12)
  private StringSet stringSet;

  public final Set<Integer> getIntSet() {
    return intSet;
  }

  public final ObjectWithSet setIntSet(final Set<Integer> intSet) {
    this.intSet = intSet;
    return this;
  }

  public final StringSet getStringSet() {
    return stringSet;
  }

  public final ObjectWithSet setStringSet(
          final StringSet stringSet) {
    this.stringSet = stringSet;
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
    final ObjectWithSet that = (ObjectWithSet) o;
    return Objects.equals(intSet, that.intSet)
        && Objects.equals(stringSet, that.stringSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intSet, stringSet);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ObjectWithSet.class.getSimpleName() + "[", "]")
            .add("intSet=" + intSet)
            .add("stringSet=" + stringSet)
            .toString();
  }
}
