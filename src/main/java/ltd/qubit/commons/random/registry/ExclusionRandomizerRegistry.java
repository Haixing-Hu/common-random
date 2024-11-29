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
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import ltd.qubit.commons.annotation.Exclude;
import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.FieldPredicates;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.TypePredicates;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.misc.SkipRandomizer;

/**
 * A {@link RandomizerRegistry} to exclude fields using a {@link Predicate}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(0)
public class ExclusionRandomizerRegistry implements RandomizerRegistry {

  private final Set<Predicate<Field>> fieldPredicates = new HashSet<>();
  private final Set<Predicate<Class<?>>> typePredicates = new HashSet<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    fieldPredicates.add(FieldPredicates.isAnnotatedWith(Exclude.class));
    typePredicates.add(TypePredicates.isAnnotatedWith(Exclude.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    for (final Predicate<Field> fieldPredicate : fieldPredicates) {
      if (fieldPredicate.test(field)) {
        return new SkipRandomizer();
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Class<?> clazz, final Context context) {
    for (final Predicate<Class<?>> typePredicate : typePredicates) {
      if (typePredicate.test(clazz)) {
        return new SkipRandomizer();
      }
    }
    return null;
  }

  /**
   * Add a field predicate.
   *
   * @param predicate
   *         to add
   */
  public void addFieldPredicate(final Predicate<Field> predicate) {
    fieldPredicates.add(predicate);
  }

  /**
   * Add a type predicate.
   *
   * @param predicate
   *         to add
   */
  public void addTypePredicate(final Predicate<Class<?>> predicate) {
    typePredicates.add(predicate);
  }

}
