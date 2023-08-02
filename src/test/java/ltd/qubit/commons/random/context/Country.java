////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

public class Country {
  private String name;

  public Country(final String name) {
    this.name = name;
  }

  public Country() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
