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
 * 一个 {@link RandomizerRegistry}，用于处理使用 {@link Randomizer} 注解的字段。
 *
 * @author 胡海星
 */
@Priority(Integer.MAX_VALUE - 1)
public class RandomizerAnnotatedRegistry implements RandomizerRegistry {

  private final Map<Field, ltd.qubit.commons.random.api.Randomizer<?>> registry = new HashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    // no op
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ltd.qubit.commons.random.api.Randomizer<?> get(final Field field, final Context context) {
    if (isAnnotationPresent(field, Randomizer.class)) {
      ltd.qubit.commons.random.api.Randomizer<?> randomizer = registry.get(field);
      if (randomizer == null) {
        final Randomizer annotation = getAnnotation(field, Randomizer.class);
        final Class<?> type = annotation.value();
        final RandomizerArgument[] arguments = annotation.args();
        randomizer = ReflectionUtils.newInstance(type, arguments);
        registry.put(field, randomizer);
      }
      return randomizer;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ltd.qubit.commons.random.api.Randomizer<?> get(final Class<?> clazz, final Context context) {
    return null;
  }
}
