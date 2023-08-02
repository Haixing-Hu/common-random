////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.ExclusionPolicy;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;
import ltd.qubit.commons.random.util.ReflectionUtils;
import ltd.qubit.commons.util.range.CloseRange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.reflect.ClassUtils.isArrayType;
import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;
import static ltd.qubit.commons.reflect.ClassUtils.isEnumType;
import static ltd.qubit.commons.reflect.ClassUtils.isIntrospectable;
import static ltd.qubit.commons.reflect.ClassUtils.isMapType;

/**
 * Extension of {@link java.util.Random} that is able to generate random Java
 * objects.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EasyRandom extends RandomEx {

  private static final long serialVersionUID = -1460004897173461959L;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final Parameters parameters;

  private final FieldPopulator fieldPopulator;

  private final ArrayPopulator arrayPopulator;

  private final CollectionPopulator collectionPopulator;

  private final MapPopulator mapPopulator;

  private final Map<Class, EnumRandomizer> enumRandomizersByType;

  private final RandomizerProvider randomizerProvider;

  private final ObjectFactory objectFactory;

  /**
   * Create a new {@link EasyRandom} instance with default parameters.
   */
  public EasyRandom() {
    this(new Parameters());
  }

  /**
   * Create a new {@link EasyRandom} instance.
   *
   * @param parameters
   *     randomization parameters
   */
  public EasyRandom(final Parameters parameters) {
    this.parameters = requireNonNull(parameters, "Parameters must not be null");
    setSeed(parameters.getSeed());
    randomizerProvider = buildRandomizerProvider(parameters);
    objectFactory = parameters.getObjectFactory();
    arrayPopulator = new ArrayPopulator(this);
    collectionPopulator = new CollectionPopulator(this);
    mapPopulator = new MapPopulator(this);
    enumRandomizersByType = new ConcurrentHashMap<>();
    fieldPopulator = new FieldPopulator(this);
  }

  private RandomizerProvider buildRandomizerProvider(
      final Parameters parameters) {
    final LinkedHashSet<RandomizerRegistry> registries = setupRegistries(
        parameters);
    final RandomizerProvider provider = parameters.getRandomizerProvider();
    final RandomizerProvider result = (provider == null
                                       ? new RegistriesRandomizerProvider()
                                       : provider);
    result.addRegistries(registries);
    parameters.setRandomizerProvider(result);
    return result;
  }

  public Logger getLogger() {
    return logger;
  }

  public final Parameters getParameters() {
    return parameters;
  }

  public final RandomizerProvider getRandomizerProvider() {
    return randomizerProvider;
  }

  /**
   * Generate a random instance of the given type.
   *
   * @param type
   *     the type for which an instance will be generated
   * @param <T>
   *     the actual type of the target object
   * @return a random instance of the given type
   * @throws ObjectCreationException
   *     when unable to create a new instance of the given type
   */
  public <T> T nextObject(final Class<T> type) {
    return doPopulateBean(type, new Context(type, parameters));
  }

  /**
   * Generate a random instance of the given type.
   *
   * @param type
   *     the type for which an instance will be generated
   * @param context
   *     the randomization context.
   * @param <T>
   *     the actual type of the target object
   * @return a random instance of the given type
   * @throws ObjectCreationException
   *     when unable to create a new instance of the given type
   */
  public <T> T nextObject(final Class<T> type, final Context context) {
    return doPopulateBean(type, context);
  }

  /**
   * Generate a stream of random instances of the given type.
   *
   * @param type
   *     the type for which instances will be generated
   * @param streamSize
   *     the number of instances to generate
   * @param <T>
   *     the actual type of the target objects
   * @return a stream of random instances of the given type
   * @throws ObjectCreationException
   *     when unable to create a new instance of the given type
   */
  public <T> Stream<T> objects(final Class<T> type, final int streamSize) {
    if (streamSize < 0) {
      throw new IllegalArgumentException("The stream size must be positive");
    }
    return Stream.generate(() -> nextObject(type)).limit(streamSize);
  }

  private <T> T doPopulateBean(final Class<T> type, final Context context) {
    logger.debug("Populate bean: class = {}, context = {}", type, context);
    final ExclusionPolicy exclusionPolicy = parameters.getExclusionPolicy();
    if (exclusionPolicy != null
        && exclusionPolicy.shouldBeExcluded(type, context)) {
      return null;
    }
    final T result;
    try {
      final Randomizer<?> randomizer = randomizerProvider.getByType(type, context);
      if (randomizer != null) {
        if (randomizer instanceof ContextAwareRandomizer) {
          ((ContextAwareRandomizer<?>) randomizer).setContext(context);
        }
        return (T) randomizer.getRandomValue();
      }
      // Collection types are randomized without introspection for internal fields
      if (!isIntrospectable(type)) {
        return randomizeNonIntrospectable(type, context);
      }
      // If the type has been already randomized, return one cached instance to
      // avoid recursion.
      if (context.hasAlreadyRandomizedType(type)) {
        return (T) context.getPopulatedBean(type);
      }
      // create a new instance of the target type
      result = objectFactory.createInstance(type, context);
      context.setRandomizedObject(result);
      // cache instance in the population context
      context.addPopulatedBean(type, result);
      // retrieve fields to be populated
      final List<Field> fields = ReflectionUtils.getPopulatableFields(type, result);
      // populate fields with random data
      fieldPopulator.populateFields(result, fields, context);
      return result;
    } catch (final Throwable e) {
      if (parameters.isIgnoreErrors()) {
        return null;
      } else {
        throw new ObjectCreationException(
            "Unable to create a random instance of type " + type, e);
      }
    }
  }

  private <T> T randomizeNonIntrospectable(final Class<T> type,
      final Context context) {
    if (isEnumType(type)) {
      if (!enumRandomizersByType.containsKey(type)) {
        enumRandomizersByType
            .put(type, new EnumRandomizer(type, parameters.getSeed()));
      }
      final Randomizer<T> randomizer = enumRandomizersByType.get(type);
      if (randomizer instanceof ContextAwareRandomizer) {
        ((ContextAwareRandomizer) randomizer).setContext(context);
      }
      return (T) enumRandomizersByType.get(type).getRandomValue();
    }
    if (isArrayType(type)) {
      return (T) arrayPopulator.populate(type, context, null);
    }
    if (isCollectionType(type)) {
      return (T) ReflectionUtils.getEmptyImplementationForCollectionInterface(type);
    }
    if (isMapType(type)) {
      return (T) ReflectionUtils.getEmptyImplementationForMapInterface(type);
    }
    return null;
  }

  private LinkedHashSet<RandomizerRegistry> setupRegistries(
      final Parameters parameters) {
    final LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<>();
    registries.add(parameters.getCustomRandomizerRegistry());
    registries.add(parameters.getExclusionRandomizerRegistry());
    registries.addAll(parameters.getUserRegistries());
    registries.addAll(loadRegistries());
    registries.forEach(registry -> registry.init(this, parameters));
    return registries;
  }

  private Collection<RandomizerRegistry> loadRegistries() {
    final List<RandomizerRegistry> registries = new ArrayList<>();
    ServiceLoader.load(RandomizerRegistry.class).forEach(registries::add);
    return registries;
  }

  public final FieldPopulator getFieldPopulator() {
    return fieldPopulator;
  }

  public final ArrayPopulator getArrayPopulator() {
    return arrayPopulator;
  }

  public final CollectionPopulator getCollectionPopulator() {
    return collectionPopulator;
  }

  public final MapPopulator getMapPopulator() {
    return mapPopulator;
  }

  public final ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  public <E> List<E> nextList(final Class<E> elementType) {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    final List<E> result = new ArrayList<>();
    for (int i = 0; i < size; ++i) {
      result.add(nextObject(elementType));
    }
    return result;
  }

  public <E> Set<E> nextSet(final Class<E> elementType) {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    final Set<E> result = new HashSet<>();
    final int maxLoops = parameters.getMaxLoops();
    for (int i = 0; i < maxLoops; ++i) {
      if (result.size() == size) {
        break;
      }
      result.add(nextObject(elementType));
    }
    return result;
  }

  public final char[] nextDigitChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextDigitChars(size);
  }

  public final char[] nextLowercaseLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLowercaseLetterChars(size);
  }

  public final char[] nextUppercaseLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextUppercaseLetterChars(size);
  }

  public final char[] nextLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLetterChars(size);
  }

  public final char[] nextLetterDigitChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLetterDigitChars(size);
  }

  public final String nextDigitString() {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> lengthCloseRange = parameters
        .getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextDigitString(length);
  }

  public final String nextLowercaseLetterString() {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> lengthCloseRange = parameters
        .getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLowercaseLetterString(length);
  }

  public final String nextUppercaseLetterString() {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> lengthCloseRange = parameters
        .getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextUppercaseLetterString(length);
  }

  public final String nextLetterString() {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> lengthCloseRange = parameters
        .getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLetterString(length);
  }

  public final String nextLetterDigitString() {
    final Parameters parameters = getParameters();
    final CloseRange<Integer> lengthCloseRange = parameters
        .getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLetterDigitString(length);
  }

  public <T> T[] nextObjectArray(final int size, final Class<T> type) {
    @SuppressWarnings("unchecked") final T[] result = (T[]) Array
        .newInstance(type, size);
    for (int i = 0; i < size; ++i) {
      result[i] = nextObject(type);
    }
    return result;
  }

  public <T> T[] nextObjectArray(final Class<T> type) {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextObjectArray(size, type);
  }
}
