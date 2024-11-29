////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;

import jakarta.validation.constraints.Null;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.NullRandomizer;

/**
 * The annotation handler for the {@link Null} annotation.
 *
 * @author Haixing Hu
 */
public class NullAnnotationHandler implements AnnotationHandler {

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new NullRandomizer();
  }
}
