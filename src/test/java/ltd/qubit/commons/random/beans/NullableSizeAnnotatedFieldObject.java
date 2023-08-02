////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

public class NullableSizeAnnotatedFieldObject {
  @Size(max = 3)
  @Nullable
  public String f1;
}
