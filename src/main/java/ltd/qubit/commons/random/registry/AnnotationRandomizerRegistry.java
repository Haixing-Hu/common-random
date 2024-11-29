////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.annotation.Randomizer;
import ltd.qubit.commons.random.annotation.RandomizerArgument;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.util.ReflectionUtils;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * A {@link RandomizerRegistry} for fields annotated with {@link ltd.qubit.commons.random.api.Randomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(-1)
public class AnnotationRandomizerRegistry implements RandomizerRegistry {

  private final Map<Field, ltd.qubit.commons.random.api.Randomizer<?>> registry = new HashMap<>();

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    // no op
  }

  @Override
  public ltd.qubit.commons.random.api.Randomizer<?> get(final Field field, final Context context) {
    if (isAnnotationPresent(field, Randomizer.class)) {
      ltd.qubit.commons.random.api.Randomizer<?> randomizer = registry.get(field);
      if (randomizer == null) {
        final Randomizer annotation =
                getAnnotation(field, Randomizer.class);
        final Class<?> type = annotation.value();
        final RandomizerArgument[] arguments = annotation.args();
        randomizer = ReflectionUtils.newInstance(type, arguments);
        registry.put(field, randomizer);
      }
      return randomizer;
    }
    return null;
  }

  @Override
  public ltd.qubit.commons.random.api.Randomizer<?> get(final Class<?> clazz, final Context context) {
    return null;
  }
}
