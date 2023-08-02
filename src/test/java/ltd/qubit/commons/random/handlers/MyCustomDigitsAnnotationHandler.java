////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.BigDecimalRandomizer;

public class MyCustomDigitsAnnotationHandler implements AnnotationHandler {

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    // `@Digits` is applicable to several types (see its javadoc)
    // for this test, just assuming the field is a BigDecimal
    return new BigDecimalRandomizer(Integer.valueOf(2));
  }

}
