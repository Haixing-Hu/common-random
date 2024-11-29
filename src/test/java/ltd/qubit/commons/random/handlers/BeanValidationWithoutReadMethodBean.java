////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import jakarta.validation.constraints.NotNull;

public class BeanValidationWithoutReadMethodBean {

  @NotNull
  private String fieldWithoutReadMethod;

  public void setFieldWithoutReadMethod(final String fieldWithoutReadMethod) {
    this.fieldWithoutReadMethod = fieldWithoutReadMethod;
  }
}
