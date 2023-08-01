////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

public class NullableSizeAnnotatedBean {
  @Size(max = 3)
  @Nullable
  public String f1;
}
