////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.math.BigDecimal;

import ltd.qubit.commons.annotation.Scale;

public class ScaleAnnotatedFieldObject {
  @Scale(3)
  public BigDecimal f1;
}
