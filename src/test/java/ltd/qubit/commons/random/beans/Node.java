////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.List;

public class Node {

  private String value;

  private Node left;

  private Node right;

  private List<Node> parents;

  public Node() {
  }

  public String getValue() {
    return this.value;
  }

  public Node getLeft() {
    return this.left;
  }

  public Node getRight() {
    return this.right;
  }

  public List<Node> getParents() {
    return this.parents;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  public void setLeft(final Node left) {
    this.left = left;
  }

  public void setRight(final Node right) {
    this.right = right;
  }

  public void setParents(final List<Node> parents) {
    this.parents = parents;
  }
}
