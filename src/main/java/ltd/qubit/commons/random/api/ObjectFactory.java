////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.ObjectCreationException;

/**
 * Strategy interface for object creation.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @since 4.0
 */
public interface ObjectFactory {

  /**
   * Create a new instance of {@code type} in the given randomization context.
   *
   * @param type
   *         to create
   * @param context
   *         current randomization context
   * @param <T>
   *         generic type
   * @return new instance of the given type
   * @throws ObjectCreationException
   *         when unable to create an instance of the given type
   */
  <T> T createInstance(final Class<T> type, final Context context)
          throws ObjectCreationException;

}
