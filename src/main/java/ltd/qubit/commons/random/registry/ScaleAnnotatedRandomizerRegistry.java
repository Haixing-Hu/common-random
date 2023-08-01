////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.reflect.Field;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.handlers.ScaleAnnotationHandler;

/**
 * A registry of randomizers to support fields annotated with the {@link Scale}
 * annotation.
 *
 * @author Haixing Hu
 */
@Priority(2)
public class ScaleAnnotatedRandomizerRegistry implements RandomizerRegistry {

  private ScaleAnnotationHandler handler;

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    final long seed = parameters.getSeed();
    handler = new ScaleAnnotationHandler(seed);
  }

  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    return handler.getRandomizer(field, context);
  }

  @Override
  public Randomizer<?> get(final Class<?> fieldType, final Context context) {
    return null;
  }

}
