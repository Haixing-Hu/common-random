////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

import jakarta.validation.constraints.Size;

public class DerivedClassBar extends BaseClassWithFinalField {

  @Size(min = 3, max = 5)
  private String fieldWithDefaultValue = "X";

  private final String finalFieldWithDefaultValue = "YYYY";

  @Size(min = 4, max = 10)
  private final String finalFieldWithoutDefaultValue = null;

  public DerivedClassBar() {
    super("Bar");
  }

  public final String getFieldWithDefaultValue() {
    return fieldWithDefaultValue;
  }

  public final DerivedClassBar setFieldWithDefaultValue(
          final String fieldWithDefaultValue) {
    this.fieldWithDefaultValue = fieldWithDefaultValue;
    return this;
  }

  public final String getFinalFieldWithDefaultValue() {
    return finalFieldWithDefaultValue;
  }

  public final String getFinalFieldWithoutDefaultValue() {
    return finalFieldWithoutDefaultValue;
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
    final DerivedClassBar that = (DerivedClassBar) o;
    return Objects.equals(fieldWithDefaultValue, that.fieldWithDefaultValue)
        && Objects.equals(finalFieldWithDefaultValue, that.finalFieldWithDefaultValue)
        && Objects.equals(finalFieldWithoutDefaultValue, that.finalFieldWithoutDefaultValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fieldWithDefaultValue,
        finalFieldWithDefaultValue, finalFieldWithoutDefaultValue);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DerivedClassBar.class.getSimpleName() + "[", "]")
            .add("name='" + getName() + "'")
            .add("fieldWithDefaultValue='" + fieldWithDefaultValue + "'")
            .add("finalFieldWithDefaultValue='" + finalFieldWithDefaultValue + "'")
            .add("finalFieldWithoutDefaultValue='" + finalFieldWithoutDefaultValue + "'")
            .toString();
  }
}
