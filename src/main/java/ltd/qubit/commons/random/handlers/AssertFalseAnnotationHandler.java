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

import jakarta.validation.constraints.AssertFalse;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.ConstantRandomizer;

/**
 * The annotation handler for the {@link AssertFalse} annotation.
 *
 * @author Haixing Hu
 */
public class AssertFalseAnnotationHandler implements AnnotationHandler {

  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new ConstantRandomizer<>(false);
  }
}
