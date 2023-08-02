////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class BeanWithDefaultFieldValues {

  private String defaultNonNullValue = "default";
  private String defaultNonNullValueSetByConstructor;

  public BeanWithDefaultFieldValues() {
    defaultNonNullValueSetByConstructor = "defaultSetByConstructor";
  }

  public String getDefaultNonNullValue() {
    return this.defaultNonNullValue;
  }

  public String getDefaultNonNullValueSetByConstructor() {
    return this.defaultNonNullValueSetByConstructor;
  }

  public void setDefaultNonNullValue(final String defaultNonNullValue) {
    this.defaultNonNullValue = defaultNonNullValue;
  }

  public void setDefaultNonNullValueSetByConstructor(
      final String defaultNonNullValueSetByConstructor) {
    this.defaultNonNullValueSetByConstructor = defaultNonNullValueSetByConstructor;
  }
}
