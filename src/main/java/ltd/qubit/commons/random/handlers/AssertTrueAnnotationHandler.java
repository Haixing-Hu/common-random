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

import jakarta.validation.constraints.AssertTrue;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.ConstantRandomizer;

/**
 * The annotation handler for the {@link AssertTrue} annotation.
 *
 * @author Haixing Hu
 */
public class AssertTrueAnnotationHandler implements AnnotationHandler {

  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new ConstantRandomizer<>(true);
  }
}
