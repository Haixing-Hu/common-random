////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
