////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import java.lang.reflect.Field;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

/**
 * Interface for a registry of {@link Randomizer}s.
 *
 * @author Rémi Alvergnat, Haixing Hu
 */
public interface RandomizerRegistry {

  /**
   * Initialize the registry.
   *
   * @param random
   *         the {@link EasyRandom} instance being configured
   * @param parameters
   *         the {@link Parameters} instance being configured.
   */
  void init(EasyRandom random, Parameters parameters);

  /**
   * Retrieves a randomizer for the given field.
   *
   * @param field
   *         the field for which a randomizer was registered
   * @param context
   *         the current context.
   * @return the randomizer registered for the given field
   */
  Randomizer<?> get(Field field, Context context);

  /**
   * Retrieves a randomizer for a given type.
   *
   * @param type
   *         the type for which a randomizer was registered.
   * @param context
   *         the current context.
   * @return the randomizer registered for the given type.
   */
  Randomizer<?> get(Class<?> type, Context context);
}
