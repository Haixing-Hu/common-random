////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.exclusion;

import java.util.List;

public class C {

  private B b1;
  private B b2;
  private List<B> b3;

  public C() {
  }

  public B getB1() {
    return this.b1;
  }

  public B getB2() {
    return this.b2;
  }

  public List<B> getB3() {
    return this.b3;
  }

  public void setB1(final B b1) {
    this.b1 = b1;
  }

  public void setB2(final B b2) {
    this.b2 = b2;
  }

  public void setB3(final List<B> b3) {
    this.b3 = b3;
  }
}
