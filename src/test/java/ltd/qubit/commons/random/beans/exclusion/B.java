////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.exclusion;

public class B {

  private A a1;
  private A a2;

  public B() {
  }

  public A getA1() {
    return this.a1;
  }

  public A getA2() {
    return this.a2;
  }

  public void setA1(final A a1) {
    this.a1 = a1;
  }

  public void setA2(final A a2) {
    this.a2 = a2;
  }
}
