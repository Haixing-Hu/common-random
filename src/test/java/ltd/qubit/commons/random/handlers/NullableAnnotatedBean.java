////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.util.Date;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

public class NullableAnnotatedBean {

  public Integer f0;

  @NotNull
  public String f1;

  @Nullable
  public String f2;

  @NotNull
  public Date f3;

  @Nullable
  public Date f4;
}
