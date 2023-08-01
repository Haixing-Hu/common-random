////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class ClassUsingAbstractEnum {

  private AbstractEnum testEnum;

  public ClassUsingAbstractEnum() {
  }

  public AbstractEnum getTestEnum() {
    return this.testEnum;
  }

  public void setTestEnum(final AbstractEnum testEnum) {
    this.testEnum = testEnum;
  }
}
