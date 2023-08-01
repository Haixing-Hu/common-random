////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import javax.validation.constraints.NotNull;

public class BeanValidationWithoutReadMethodBean {

  @NotNull
  private String fieldWithoutReadMethod;

  public void setFieldWithoutReadMethod(final String fieldWithoutReadMethod) {
    this.fieldWithoutReadMethod = fieldWithoutReadMethod;
  }
}
