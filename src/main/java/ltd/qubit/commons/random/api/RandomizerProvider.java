////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import java.lang.reflect.Field;
import java.util.Set;

import ltd.qubit.commons.random.Context;

/**
 * Strategy interface to provide randomizers for field/type based on the current
 * context. Implementations may (or may not) use registries to provide
 * randomizers.
 *
 * <p>The added value of this interface compared to a simple {@link
 * RandomizerRegistry} is that it gives access to the current context and allows
 * fine grained randomizer selection based on that context.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public interface RandomizerProvider extends Cloneable {

  /**
   * Return a randomizer for the given field in the current context.
   *
   * @param field
   *         the field for which a randomizer should be returned
   * @param context
   *         current randomization context
   * @return a randomizer for the given field in the current context.
   */
  default Randomizer<?> getByField(Field field, Context context) {
    return null;
  }

  /**
   * Return a randomizer for the given type in the current context.
   *
   * @param type
   *         for which a randomizer should be returned
   * @param context
   *         current randomization context
   * @param <T>
   *         generic type
   * @return a randomizer for the given type in the current context.
   */
  default <T> Randomizer<T> getByType(Class<T> type, Context context) {
    return null;
  }

  /**
   * Add randomizer registries.
   *
   * @param registries
   *         the registries to add.
   * @return this object.
   */
  RandomizerProvider addRegistries(Set<RandomizerRegistry> registries);

  /**
   * Remove the specified randomizer registry .
   *
   * @param registryClass
   *         the class of the specified registry to be removed.
   * @return this object.
   */
  RandomizerProvider removeRegistry(Class<? extends RandomizerRegistry> registryClass);

  /**
   * Clone this randomizer registries.
   *
   * @return the cloned copy of this object.
   */
  RandomizerProvider clone();

}
