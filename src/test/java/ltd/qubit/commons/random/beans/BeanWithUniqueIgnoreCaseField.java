////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class BeanWithUniqueIgnoreCaseField {

  @Unique
  private String code;

  public BeanWithUniqueIgnoreCaseField() {
    // empty
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final BeanWithUniqueIgnoreCaseField other = (BeanWithUniqueIgnoreCaseField) o;
    return Equality.equals(code, other.code);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, code);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("code", code).toString();
  }
}
