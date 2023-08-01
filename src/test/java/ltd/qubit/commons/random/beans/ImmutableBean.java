////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.List;

public class ImmutableBean {

  private final String finalValue;

  private final List<String> finalCollection;

  public ImmutableBean(final String finalValue, final List<String> finalCollection) {
    this.finalValue = finalValue;
    this.finalCollection = finalCollection;
  }

  public String getFinalValue() {
    return finalValue;
  }

  public List<String> getFinalCollection() {
    return finalCollection;
  }
}
