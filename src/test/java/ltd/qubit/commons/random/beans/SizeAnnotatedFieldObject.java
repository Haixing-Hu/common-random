////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class SizeAnnotatedFieldObject {

  @Min(10)
  @Max(100)
  public int f1;

  @Min(10)
  @Max(100)
  public Integer f1a;

  @Size(min = 3, max = 6)
  public String f2;

  @Size(max = 3)
  public String f2b;

  @Size(min = 1, max = 5)
  public Integer[] f3;

  @Size(min = 1, max = 5)
  public int[] f3c;

  @Size(min = 2, max = 4)
  public List<NullableFieldObject> f4;

  @Size(min = 4, max = 6)
  public Set<NullableFieldObject> f5;

  @Override
  public String toString() {
    return new StringJoiner(", ", SizeAnnotatedFieldObject.class.getSimpleName() + "[", "]")
            .add("f1=" + f1)
            .add("f2='" + f2 + "'")
            .add("f3=" + Arrays.toString(f3))
            .add("f4=" + f4)
            .add("f5=" + f5)
            .toString();
  }
}
