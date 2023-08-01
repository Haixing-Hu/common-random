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
import java.util.StringJoiner;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.annotation.Unique;

public class ComplexUniqueAnnotatedBean {

  @Unique(respectTo = "f2")
  public String f1;

  @Unique
  @Nullable
  public String f2;

  @Size(min = 3, max = 5)
  public List<String> f3;

  @Unique(respectTo = {"f5", "f6"})
  public String f4;

  @Unique(respectTo = "f6")
  public String f5;

  @Size(max = 3)
  public String f6;

  @Override
  public java.lang.String toString() {
    return new StringJoiner(", ", ComplexUniqueAnnotatedBean.class.getSimpleName() + "[", "]")
        .add("f1=" + f1)
        .add("f2=" + f2)
        .add("f3=" + f3)
        .toString();
  }
}
