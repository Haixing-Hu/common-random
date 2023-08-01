////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

public class ConcreteFooB extends AbstractFoo {

  private String remark;

  public final String getRemark() {
    return remark;
  }

  public final ConcreteFooB setRemark(final String remark) {
    this.remark = remark;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final ConcreteFooB that = (ConcreteFooB) o;
    return Objects.equals(remark, that.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), remark);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ConcreteFooB.class.getSimpleName() + "[", "]")
            .add("remark=" + remark)
            .toString();
  }
}
