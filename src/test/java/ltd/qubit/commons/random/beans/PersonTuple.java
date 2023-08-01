////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class PersonTuple {
  public Person left;
  public Person right;

  public PersonTuple() {
  }

  public Person getLeft() {
    return this.left;
  }

  public Person getRight() {
    return this.right;
  }

  public void setLeft(final Person left) {
    this.left = left;
  }

  public void setRight(final Person right) {
    this.right = right;
  }
}
