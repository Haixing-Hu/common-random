////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.util;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型将 RESTful API 的返回结果封装为一个对象，从而可以使其正确编码为JSON对象。
 *
 * @param <T>
 *     返回结果的类型，通常是{@link String}，{@link Boolean}，{@link Number}等简单
 *     类型。
 * @author 胡海星
 */
public class Result<T> {

  /**
   * 唯一标识。
   */
  private T value;

  public Result() {}

  public Result(final T value) {
    this.value = value;
  }

  public final T getValue() {
    return value;
  }

  public final Result<T> setValue(final T value) {
    this.value = value;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    @SuppressWarnings("unchecked")final
    Result<T> other = (Result<T>) o;
    return Equality.equals(value, other.value);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, result);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("value", value)
            .toString();
  }
}
