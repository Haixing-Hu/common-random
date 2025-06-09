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
import java.util.Map;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.MapPopulator;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.lang.ClassUtils.isMapType;
import static ltd.qubit.commons.random.util.RandomUtils.populateRemainedFields;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * 一个用于生成随机映射表的随机化器注册表。
 *
 * @author 胡海星
 */
@Priority(-3)
public class MapRandomizerRegistry implements RandomizerRegistry {

  private EasyRandom random;
  private Parameters parameters;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    this.random = requireNonNull(random, "random cannot be null");
    this.parameters = requireNonNull(parameters, "parameters cannot be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public Randomizer<?> get(final Field field, final Context context) {
    if (isAnnotationPresent(field, Size.class)) {
      // if the field is annotated with Size,
      // randomize it with BeanValidationRandomizerRegistry
      return null;
    }
    final Class<?> fieldType = field.getType();
    if (! isMapType(fieldType)) {
      return null;
    }
    final MapPopulator populator = random.getMapPopulator();
    final EasyRandom theRandom = this.random;
    return new AbstractContextAwareRandomizer() {
      @Override
      public Object getRandomValue() {
        final Class<?> fieldType = field.getType();
        if (super.context == null) {
          super.context = new Context(fieldType, parameters);
        }
        final Map<?, ?> map = populator.populate(field, super.context, null);
        populateRemainedFields(theRandom, super.context, fieldType, map);
        return map;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public Randomizer<?> get(final Class<?> type, final Context context) {
    if (! isMapType(type)) {
      return null;
    }
    final MapPopulator populator = random.getMapPopulator();
    final EasyRandom theRandom = this.random;
    return new AbstractContextAwareRandomizer() {
      @Override
      public Object getRandomValue() {
        if (super.context == null) {
          super.context = new Context(type, parameters);
        }
        final Map<?, ?> map = populator.populate(type, super.context, null);
        populateRemainedFields(theRandom, super.context, type, map);
        return map;
      }
    };
  }
}
