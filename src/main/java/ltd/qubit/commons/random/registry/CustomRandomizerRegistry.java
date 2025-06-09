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
import java.util.function.Predicate;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;

import static ltd.qubit.commons.random.util.ReflectionUtils.getWrapperType;

/**
 * 用户自定义的随机化器注册表。
 *
 * @author 胡海星
 */
@Priority(-1)
public class CustomRandomizerRegistry implements RandomizerRegistry {

  private final Map<Predicate<Field>, Randomizer<?>> fieldRegistry = new HashMap<>();
  private final Map<Class<?>, Randomizer<?>> typeRegistry = new HashMap<>();

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
  public Randomizer<?> get(final Field field, final Context context) {
    for (final Predicate<Field> fieldPredicate : fieldRegistry.keySet()) {
      if (fieldPredicate.test(field)) {
        return fieldRegistry.get(fieldPredicate);
      }
    }
    return get(field.getType(), context);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * 注册一个用于特定类型的随机化器。
   *
   * @param type
   *     要为其注册随机化器的类型。
   * @param randomizer
   *     要注册的随机化器。
   * @param <T>
   *     要为其注册随机化器的类型。
   * @param <R>
   *     随机化器生成的对象类型。
   */
  public <T, R> void register(final Class<T> type, final Randomizer<R> randomizer) {
    typeRegistry.put(type, randomizer);
  }

  /**
   * 注册一个用于满足特定谓词的字段的随机化器。
   *
   * @param predicate
   *     用于选择字段的谓词。
   * @param randomizer
   *     要注册的随机化器。
   */
  public void register(final Predicate<Field> predicate, final Randomizer<?> randomizer) {
    fieldRegistry.put(predicate, randomizer);
  }
}
