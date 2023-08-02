////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import jakarta.validation.constraints.Digits;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.registry.BeanValidationRandomizerRegistry;

public class MyCustomBeanValidationRandomizerRegistry extends
        BeanValidationRandomizerRegistry {

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    super.init(random, parameters);
    handlers.put(Digits.class, new MyCustomDigitsAnnotationHandler());
  }
}
