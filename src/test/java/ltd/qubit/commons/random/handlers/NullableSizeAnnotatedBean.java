////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

public class NullableSizeAnnotatedBean {
  @Size(max = 3)
  @Nullable
  public String f1;
}
