////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

public class NullableSizeAnnotatedFieldObject {
  @Size(max = 3)
  @Nullable
  public String f1;
}
