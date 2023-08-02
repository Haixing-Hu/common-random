////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.StringJoiner;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Unique;

public class UniqueSizeNullableAnnotatedFieldObject {
  @Unique
  @Size(max = 10)
  @Nullable
  public String f1;

  @Override
  public String toString() {
    return new StringJoiner(", ", UniqueSizeNullableAnnotatedFieldObject.class
            .getSimpleName() + "[", "]")
            .add("f1='" + f1 + "'")
            .toString();
  }
}
