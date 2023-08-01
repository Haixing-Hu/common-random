////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Date;

@SuppressWarnings("rawtypes")
public class AbstractBean {

  private Comparable c1;
  private Comparable<Date> c2;

  public AbstractBean() {
  }

  public Comparable getC1() {
    return this.c1;
  }

  public Comparable<Date> getC2() {
    return this.c2;
  }

  public void setC1(final Comparable c1) {
    this.c1 = c1;
  }

  public void setC2(final Comparable<Date> c2) {
    this.c2 = c2;
  }

}
