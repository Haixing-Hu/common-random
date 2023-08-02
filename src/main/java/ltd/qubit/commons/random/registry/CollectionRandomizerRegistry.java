////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.reflect.Field;
import java.util.Collection;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.random.CollectionPopulator;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.random.util.RandomUtils.populateRemainedFields;
import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * A randomizer registry for generating randomized collections.
 *
 * @author Haixing Hu
 */
public class CollectionRandomizerRegistry implements RandomizerRegistry {

  private EasyRandom random;
  private Parameters parameters;

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    this.random = requireNonNull(random, "random cannot be null");
    this.parameters = requireNonNull(parameters, "parameters cannot be null");
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    if (isAnnotationPresent(field, Size.class)) {
      // if the field is annotated with Size,
      // randomize it with BeanValidationRandomizerRegistry
      return null;
    }
    final Class<?> fieldType = field.getType();
    if (! isCollectionType(fieldType)) {
      return null;
    }
    final CollectionPopulator populator = random.getCollectionPopulator();
    final EasyRandom theRandom = this.random;
    return new AbstractContextAwareRandomizer() {
      @Override
      public Object getRandomValue() {
        final Class<?> fieldType = field.getType();
        if (super.context == null) {
          super.context = new Context(fieldType, parameters);
        }
        final Collection<?> col = populator.populate(field, super.context, null);
        populateRemainedFields(theRandom, super.context, fieldType, col);
        return col;
      }
    };
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Randomizer<?> get(final Class<?> type, final Context context) {
    if (! isCollectionType(type)) {
      return null;
    }
    final CollectionPopulator populator = random.getCollectionPopulator();
    final EasyRandom theRandom = this.random;
    return new AbstractContextAwareRandomizer() {
      @Override
      public Object getRandomValue() {
        if (super.context == null) {
          super.context = new Context(type, parameters);
        }
        final Collection<?> col = populator.populate(type, super.context, null);
        populateRemainedFields(theRandom, super.context, type, col);
        return col;
      }
    };
  }
}
