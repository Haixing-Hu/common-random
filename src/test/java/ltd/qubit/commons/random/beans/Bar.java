////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public abstract class Bar {

  private Integer number;

  public Bar() {
  }

  public abstract String getName();

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(final Integer number) {
    this.number = number;
  }
}
