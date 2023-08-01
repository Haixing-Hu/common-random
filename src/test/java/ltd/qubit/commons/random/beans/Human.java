////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class Human implements Mammal {

  public static final long SERIAL_VERSION_UID = 593716507559065802L;

  protected final Long id = null;

  protected String name;

  public Human() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
