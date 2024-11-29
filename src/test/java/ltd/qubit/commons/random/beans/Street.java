////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

public class Street {

  private int number;

  private byte type;

  private String name;

  public Street() {}

  public int getNumber() {
    return this.number;
  }

  public byte getType() {
    return this.type;
  }

  public String getName() {
    return this.name;
  }

  public void setNumber(final int number) {
    this.number = number;
  }

  public void setType(final byte type) {
    this.type = type;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Street street = (Street) o;
    return number == street.number
        && type == street.type
        && Objects.equals(name, street.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, type, name);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Street.class.getSimpleName() + "[", "]")
            .add("number=" + number)
            .add("type=" + type)
            .add("name='" + name + "'")
            .toString();
  }
}
