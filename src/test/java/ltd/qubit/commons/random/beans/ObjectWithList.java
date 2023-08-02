////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import jakarta.validation.constraints.Size;

public class ObjectWithList {

  @Size(min = 8, max = 12)
  private List<Integer> intList;

  @Size(min = 8, max = 12)
  private StringList stringList;

  public final List<Integer> getIntList() {
    return intList;
  }

  public final ObjectWithList setIntList(final List<Integer> intList) {
    this.intList = intList;
    return this;
  }

  public final StringList getStringList() {
    return stringList;
  }

  public final ObjectWithList setStringList(final StringList stringList) {
    this.stringList = stringList;
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
    final ObjectWithList that = (ObjectWithList) o;
    return Objects.equals(intList, that.intList)
        && Objects.equals(stringList, that.stringList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intList, stringList);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ObjectWithList.class.getSimpleName() + "[", "]")
            .add("intList=" + intList)
            .add("stringList=" + stringList)
            .toString();
  }
}
