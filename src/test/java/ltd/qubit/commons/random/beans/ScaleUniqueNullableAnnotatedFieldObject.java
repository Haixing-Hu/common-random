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

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.annotation.Unique;

public class ScaleUniqueNullableAnnotatedFieldObject {
  @Nullable
  @Unique
  @Scale(4)
  public BigDecimal f1;
}
