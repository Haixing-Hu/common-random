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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;

import static ltd.qubit.commons.random.util.ReflectionUtils.getWrapperType;

/**
 * Registry of user defined randomizers.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
@Priority(-1)
public class CustomRandomizerRegistry implements RandomizerRegistry {

  private final Map<Predicate<Field>, Randomizer<?>> fieldRegistry = new HashMap<>();
  private final Map<Class<?>, Randomizer<?>> typeRegistry = new HashMap<>();

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    // no op
  }

  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    for (final Predicate<Field> fieldPredicate : fieldRegistry.keySet()) {
      if (fieldPredicate.test(field)) {
        return fieldRegistry.get(fieldPredicate);
      }
    }
    return get(field.getType(), context);
  }

  @Override
  public Randomizer<?> get(final Class<?> type, final Context context) {
    // issue 241: primitive type were ignored: try to get randomizer by
    // primitive type, if not, then try by wrapper type
    Randomizer<?> randomizer = typeRegistry.get(type);
    if (randomizer == null) {
      final Class<?> wrapperType = type.isPrimitive() ? getWrapperType(type) : type;
      randomizer = typeRegistry.get(wrapperType);
    }
    return randomizer;
  }

  public <T, R> void register(final Class<T> type, final Randomizer<R> randomizer) {
    typeRegistry.put(type, randomizer);
  }

  public void register(final Predicate<Field> predicate, final Randomizer<?> randomizer) {
    fieldRegistry.put(predicate, randomizer);
  }
}
