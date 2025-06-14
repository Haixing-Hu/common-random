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

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.handlers.AnnotationHandler;
import ltd.qubit.commons.random.handlers.NullableAnnotationHandler;

/**
 * 一个随机化器注册表，用于支持使用 {@link Nullable} 注解的字段。
 *
 * @author 胡海星
 */
@Priority(Integer.MAX_VALUE - 2)
public class NullableAnnotatedRandomizerRegistry implements RandomizerRegistry {

  private AnnotationHandler handler;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    final long seed = parameters.getSeed();
    handler = new NullableAnnotationHandler(seed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    return handler.getRandomizer(field, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Class<?> fieldType, final Context context) {
    return null;
  }

}
