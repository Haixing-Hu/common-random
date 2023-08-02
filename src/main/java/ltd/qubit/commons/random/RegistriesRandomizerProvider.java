////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;

import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * Central class to get registered randomizers by Field or by Type.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public class RegistriesRandomizerProvider implements RandomizerProvider {

  private final List<RandomizerRegistry> registries;
  private final Comparator<Object> priorityComparator;
  private final Map<Field, Optional<Randomizer<?>>> byFieldCache;
  private final Map<Class<?>, Optional<Randomizer<?>>> byTypeCache;

  public RegistriesRandomizerProvider() {
    registries = new ArrayList<>();
    priorityComparator = new PriorityComparator();
    byFieldCache = new ConcurrentHashMap<>();
    byTypeCache = new ConcurrentHashMap<>();
  }

  public RegistriesRandomizerProvider clone() {
    final RegistriesRandomizerProvider result = new RegistriesRandomizerProvider();
    result.registries.addAll(this.registries);
    return result;
  }

  @Override
  public Randomizer<?> getByField(final Field field, final Context context) {
    // Field with @Nullable annotation may have different Randomizer with 50% probability.
    // So we should deal with it specially without caching
    if (isAnnotationPresent(field, Nullable.class)) {
      return getRandomizer(new ByFieldProvider(field), context);
    } else {
      Optional<Randomizer<?>> result = byFieldCache.get(field);
      if (result == null) {
        result = Optional.ofNullable(getRandomizer(new ByFieldProvider(field), context));
        byFieldCache.put(field, result);
      }
      return result.orElse(null);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> Randomizer<T> getByType(final Class<T> type, final Context context) {
    Optional<Randomizer<?>> result = byTypeCache.get(type);
    if (result == null) {
      result = Optional.ofNullable(getRandomizer(new ByTypeProvider(type), context));
      byTypeCache.put(type, result);
    }
    return (Randomizer<T>) result.orElse(null);
  }

  @Override
  public RegistriesRandomizerProvider addRegistries(final Set<RandomizerRegistry> registries) {
    this.registries.addAll(registries);
    this.registries.sort(priorityComparator);
    this.byFieldCache.clear();
    this.byTypeCache.clear();
    return this;
  }

  @Override
  public RegistriesRandomizerProvider removeRegistry(
      final Class<? extends RandomizerRegistry> registryClass) {
    this.registries.removeIf((registry) -> registry.getClass() == registryClass);
    this.byFieldCache.clear();
    this.byTypeCache.clear();
    return this;
  }

  private Randomizer<?> getRandomizer(final Provider provider, final Context context) {
    final List<Randomizer<?>> result = new ArrayList<>();
    for (final RandomizerRegistry registry : registries) {
      final Randomizer<?> randomizer = provider.getRandomizer(registry, context);
      if (randomizer != null) {
        result.add(randomizer);
      }
    }
    if (result.isEmpty()) {
      return null;
    } else {
      result.sort(priorityComparator);
      return result.get(0);
    }
  }

  @FunctionalInterface
  private interface Provider {
    Randomizer<?> getRandomizer(RandomizerRegistry registry, Context context);
  }

  private static class ByTypeProvider implements Provider {

    private final Class<?> type;

    public ByTypeProvider(final Class<?> type) {
      this.type = type;
    }

    @Override
    public Randomizer<?> getRandomizer(final RandomizerRegistry registry,
        final Context context) {
      return registry.get(type, context);
    }
  }

  private static class ByFieldProvider implements Provider {

    private final Field field;

    public ByFieldProvider(final Field field) {
      this.field = field;
    }

    @Override
    public Randomizer<?> getRandomizer(final RandomizerRegistry registry,
        final Context context) {
      return registry.get(field, context);
    }
  }
}
