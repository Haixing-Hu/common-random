////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.io.Serializable;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示编码。
 *
 * @author 胡海星
 */
public class Code implements Serializable, WithApp, Emptyful, Normalizable, Assignable<Code> {

  private static final long serialVersionUID = -6973878003458144924L;

  /**
   * 所属APP。
   */
  private StatefulInfo app;

  /**
   * 编码依据的规范标准。
   */
  @Size(min = 1, max = 128)
  @Nullable
  private String standard;

  /**
   * 具体编码值。
   */
  @Size(min = 1, max = 128)
  private String code;

  public Code() {
    // empty
  }

  public Code(final Code other) {
    assign(other);
  }

  @Override
  public void assign(final Code other) {
    Argument.requireNonNull("other", other);
    app = Assignment.clone(other.app);
    standard = other.standard;
    code = other.code;
  }

  @Override
  public Code clone() {
    return new Code(this);
  }

  public final StatefulInfo getApp() {
    return app;
  }

  public final void setApp(final StatefulInfo app) {
    this.app = app;
  }

  @Nullable
  public final String getStandard() {
    return standard;
  }

  public final void setStandard(@Nullable final String standard) {
    this.standard = standard;
  }

  public final String getCode() {
    return code;
  }

  public final void setCode(final String code) {
    this.code = code;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Code other = (Code) o;
    return Equality.equals(app, other.app)
        && Equality.equals(standard, other.standard)
        && Equality.equals(code, other.code);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, standard);
    result = Hash.combine(result, multiplier, code);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("app", app)
        .append("standard", standard)
        .append("code", code)
        .toString();
  }
}
