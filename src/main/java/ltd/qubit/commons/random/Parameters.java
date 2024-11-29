////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.api.ExclusionPolicy;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.registry.CustomRandomizerRegistry;
import ltd.qubit.commons.random.registry.ExclusionRandomizerRegistry;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.range.CloseRange;
import ltd.qubit.commons.util.range.UnmodifiableCloseRange;

import static java.lang.String.format;
import static java.time.ZonedDateTime.of;
import static java.util.Objects.requireNonNull;

/**
 * Parameters of an {@link EasyRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Parameters {

  /**
   * Default seed.
   */
  public static final long DEFAULT_SEED = 123L;

  /**
   * Default charset for Strings.
   */
  public static final Charset DEFAULT_CHARSET = StandardCharsets.US_ASCII;

  /**
   * Default collection size range.
   */
  public static final CloseRange<Integer> DEFAULT_COLLECTION_SIZE_RANGE =
      new UnmodifiableCloseRange<>(1, 5);

  /**
   * Number of different objects to generate for a type.
   * <p>
   * <b>NOTE</b>: This is the maximum number of objects to generate for a type.
   * It must be larger enough, to avoid duplicated keys when generating unique
   * values in a collection.
   * <p>
   * For example, if you have a {@code List<Foo>} field, each {@code Foo} object
   * has a unique field {@code Foo.key}. When generating the {@code List<Foo>}
   * field with a loop, if the size of the list exceeds the object pool size,
   * the generated {@code Foo} objects in the list may have duplicated keys.
   */
  public static final int DEFAULT_OBJECT_POOL_SIZE = Integer.MAX_VALUE;

  /**
   * Default value for randomization depth, which mean, that randomization depth
   * is limited.
   */
  public static final int DEFAULT_RANDOMIZATION_DEPTH = 20;

  /**
   * Default string length size.
   */
  public static final CloseRange<Integer> DEFAULT_STRING_LENGTH_RANGE =
      new UnmodifiableCloseRange<>(1, 32);

  /**
   * Default date range in which dates will be generated: [now - 10 years, now +
   * 10 years].
   */
  public static final int DEFAULT_DATE_RANGE = 10;

  /**
   * Reference date around which random dates will be generated.
   */
  private static final ZonedDateTime REFERENCE_DATE =
      of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

  /**
   * Default dates range.
   */
  public static final CloseRange<ZonedDateTime> DEFAULT_DATES_RANGE =
      new UnmodifiableCloseRange<>(
          REFERENCE_DATE.minusYears(DEFAULT_DATE_RANGE),
          REFERENCE_DATE.plusYears(DEFAULT_DATE_RANGE));

  /**
   * Default maximum number of loops for generating unique values in sets.
   */
  public static final int DEFAULT_MAX_LOOPS = 10000;

  private long seed;
  private int objectPoolSize;
  private int randomizationDepth;
  private int maxLoops;
  private Charset charset;
  private boolean scanClasspathForConcreteTypes;
  private boolean excludeTransient;
  private boolean overrideDefaultInitialization;
  private boolean overrideFinal;
  private boolean overrideFinalDefaultInitialization;
  private boolean ignoreErrors;
  private boolean bypassSetters;
  private CloseRange<Integer> collectionSizeRange;
  private CloseRange<Integer> stringLengthRange;
  private CloseRange<LocalDate> dateRange;
  private CloseRange<LocalTime> timeRange;
  private ExclusionPolicy exclusionPolicy;
  private ObjectFactory objectFactory;
  private RandomizerProvider randomizerProvider;

  // internal params
  private final CustomRandomizerRegistry customRandomizerRegistry;
  private final ExclusionRandomizerRegistry exclusionRandomizerRegistry;
  private final Set<RandomizerRegistry> userRegistries;
  private final Set<Predicate<Field>> fieldExclusionPredicates;
  private final Set<Predicate<Class<?>>> typeExclusionPredicates;
  private final Set<Field> nonNullFields;

  /**
   * Create a new {@link Parameters} with default values.
   */
  public Parameters() {
    seed = DEFAULT_SEED;
    objectPoolSize = DEFAULT_OBJECT_POOL_SIZE;
    randomizationDepth = DEFAULT_RANDOMIZATION_DEPTH;
    maxLoops = DEFAULT_MAX_LOOPS;
    charset = DEFAULT_CHARSET;
    scanClasspathForConcreteTypes = false;
    excludeTransient = false;
    overrideDefaultInitialization = false;
    overrideFinal = true;
    overrideFinalDefaultInitialization = false;
    ignoreErrors = false;
    bypassSetters = false;
    dateRange = new CloseRange<>(
        DEFAULT_DATES_RANGE.getMin().toLocalDate(),
        DEFAULT_DATES_RANGE.getMax().toLocalDate());
    timeRange = new CloseRange<>(LocalTime.MIN, LocalTime.MAX);
    collectionSizeRange = DEFAULT_COLLECTION_SIZE_RANGE;
    stringLengthRange = DEFAULT_STRING_LENGTH_RANGE;
    customRandomizerRegistry = new CustomRandomizerRegistry();
    exclusionRandomizerRegistry = new ExclusionRandomizerRegistry();
    userRegistries = new LinkedHashSet<>();
    fieldExclusionPredicates = new HashSet<>();
    typeExclusionPredicates = new HashSet<>();
    exclusionPolicy = new DefaultExclusionPolicy();
    objectFactory = new ObjenesisObjectFactory();
    nonNullFields = new HashSet<>();
  }

  public Parameters(final Parameters parameters) {
    this.seed = parameters.seed;
    this.objectPoolSize = parameters.objectPoolSize;
    this.randomizationDepth = parameters.randomizationDepth;
    this.maxLoops = parameters.maxLoops;
    this.charset = parameters.charset;
    this.scanClasspathForConcreteTypes = parameters.scanClasspathForConcreteTypes;
    this.excludeTransient = parameters.excludeTransient;
    this.overrideDefaultInitialization = parameters.overrideDefaultInitialization;
    this.overrideFinal = parameters.overrideFinal;
    this.overrideFinalDefaultInitialization = parameters.overrideFinalDefaultInitialization;
    this.ignoreErrors = parameters.ignoreErrors;
    this.bypassSetters = parameters.bypassSetters;
    this.collectionSizeRange = Assignment.clone(parameters.collectionSizeRange);
    this.stringLengthRange = Assignment.clone(parameters.stringLengthRange);
    this.dateRange = Assignment.clone(parameters.dateRange);
    this.timeRange = Assignment.clone(parameters.timeRange);
    this.exclusionPolicy = parameters.exclusionPolicy;
    this.objectFactory = parameters.objectFactory;
    this.randomizerProvider = parameters.randomizerProvider;
    this.customRandomizerRegistry = parameters.customRandomizerRegistry;
    this.exclusionRandomizerRegistry = parameters.exclusionRandomizerRegistry;
    this.userRegistries = parameters.userRegistries;
    this.fieldExclusionPredicates = parameters.fieldExclusionPredicates;
    this.typeExclusionPredicates = parameters.typeExclusionPredicates;
    this.nonNullFields = new HashSet<>(parameters.nonNullFields);
  }

  public CloseRange<Integer> getCollectionSizeRange() {
    return collectionSizeRange;
  }

  public Parameters setCollectionSizeRange(final CloseRange<Integer> collectionSizeRange) {
    this.collectionSizeRange = collectionSizeRange;
    return this;
  }

  public CloseRange<LocalDate> getDateRange() {
    return dateRange;
  }

  public Parameters setDateRange(final CloseRange<LocalDate> dateRange) {
    this.dateRange = dateRange;
    return this;
  }

  public CloseRange<LocalTime> getTimeRange() {
    return timeRange;
  }

  public Parameters setTimeRange(final CloseRange<LocalTime> timeRange) {
    this.timeRange = timeRange;
    return this;
  }

  public CloseRange<Integer> getStringLengthRange() {
    return stringLengthRange;
  }

  public Parameters setStringLengthRange(final CloseRange<Integer> stringLengthRange) {
    this.stringLengthRange = stringLengthRange;
    return this;
  }

  public long getSeed() {
    return seed;
  }

  public Parameters setSeed(final long seed) {
    this.seed = seed;
    return this;
  }

  public int getObjectPoolSize() {
    return objectPoolSize;
  }

  /**
   * Set the size of the object pool.
   * <p>
   * <b>NOTE</b>: This is the maximum number of objects to generate for a type.
   * It must be larger enough, to avoid duplicated keys when generating unique
   * values in a collection.
   * <p>
   * For example, if you have a {@code List<Foo>} field, each {@code Foo} object
   * has a unique field {@code Foo.key}. When generating the {@code List<Foo>}
   * field with a loop, if the size of the list exceeds the object pool size,
   * the generated {@code Foo} objects in the list may have duplicated keys.
   *
   * @param objectPoolSize
   *     the size of the object pool, which controls the maximum number of
   *     objects to generate for the same type.
   * @return
   *     the current {@link Parameters} instance for method chaining.
   */
  public Parameters setObjectPoolSize(final int objectPoolSize) {
    if (objectPoolSize < 1) {
      throw new IllegalArgumentException("objectPoolSize must be >= 1");
    }
    this.objectPoolSize = objectPoolSize;
    return this;
  }

  public int getRandomizationDepth() {
    return randomizationDepth;
  }

  public Parameters setRandomizationDepth(final int randomizationDepth) {
    if (randomizationDepth < 1) {
      throw new IllegalArgumentException("randomizationDepth must be >= 1");
    }
    this.randomizationDepth = randomizationDepth;
    return this;
  }

  public final int getMaxLoops() {
    return maxLoops;
  }

  public final Parameters setMaxLoops(final int maxLoops) {
    if (maxLoops <= 0) {
      throw new IllegalArgumentException("max loops must be positive.");
    }
    this.maxLoops = maxLoops;
    return this;
  }

  public Charset getCharset() {
    return charset;
  }

  public Parameters setCharset(final Charset charset) {
    this.charset = requireNonNull(charset, "Charset must not be null");
    return this;
  }

  public boolean isScanClasspathForConcreteTypes() {
    return scanClasspathForConcreteTypes;
  }

  public Parameters setScanClasspathForConcreteTypes(
      final boolean scanClasspathForConcreteTypes) {
    this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    return this;
  }

  public final boolean isExcludeTransient() {
    return excludeTransient;
  }

  public final Parameters setExcludeTransient(final boolean excludeTransient) {
    this.excludeTransient = excludeTransient;
    return this;
  }

  public boolean isOverrideDefaultInitialization() {
    return overrideDefaultInitialization;
  }

  public final Parameters setOverrideDefaultInitialization(
      final boolean overrideDefaultInitialization) {
    this.overrideDefaultInitialization = overrideDefaultInitialization;
    return this;
  }

  public final boolean isOverrideFinal() {
    return overrideFinal;
  }

  public final Parameters setOverrideFinal(final boolean overrideFinal) {
    this.overrideFinal = overrideFinal;
    return this;
  }

  public final boolean isOverrideFinalDefaultInitialization() {
    return overrideFinalDefaultInitialization;
  }

  public final Parameters setOverrideFinalDefaultInitialization(
      final boolean overrideFinalDefaultInitialization) {
    this.overrideFinalDefaultInitialization = overrideFinalDefaultInitialization;
    return this;
  }

  public boolean isIgnoreErrors() {
    return ignoreErrors;
  }

  public final Parameters setIgnoreErrors(final boolean ignoreErrors) {
    this.ignoreErrors = ignoreErrors;
    return this;
  }

  public boolean isBypassSetters() {
    return bypassSetters;
  }

  public final Parameters setBypassSetters(final boolean bypassSetters) {
    this.bypassSetters = bypassSetters;
    return this;
  }

  public ExclusionPolicy getExclusionPolicy() {
    return exclusionPolicy;
  }

  public final Parameters setExclusionPolicy(final ExclusionPolicy exclusionPolicy) {
    this.exclusionPolicy = requireNonNull(exclusionPolicy,
        "Exclusion policy must not be null");
    return this;
  }

  public ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  public final Parameters setObjectFactory(final ObjectFactory objectFactory) {
    this.objectFactory = requireNonNull(objectFactory,
        "Object factory must not be null");
    return this;
  }

  public RandomizerProvider getRandomizerProvider() {
    return randomizerProvider;
  }

  public final Parameters setRandomizerProvider(final RandomizerProvider randomizerProvider) {
    this.randomizerProvider = requireNonNull(randomizerProvider,
        "Randomizer provider must not be null");
    return this;
  }

  public Set<Predicate<Field>> getFieldExclusionPredicates() {
    return fieldExclusionPredicates;
  }

  public Set<Predicate<Class<?>>> getTypeExclusionPredicates() {
    return typeExclusionPredicates;
  }

  CustomRandomizerRegistry getCustomRandomizerRegistry() {
    return customRandomizerRegistry;
  }

  ExclusionRandomizerRegistry getExclusionRandomizerRegistry() {
    return exclusionRandomizerRegistry;
  }

  Set<RandomizerRegistry> getUserRegistries() {
    return userRegistries;
  }

  public Set<Field> getNonNullFields() {
    return nonNullFields;
  }

  /**
   * Register a custom randomizer for the given field predicate.
   * <strong>The predicate must at least specify the field type</strong>
   *
   * @param predicate
   *     to identify the field
   * @param randomizer
   *     to register
   * @param <T>
   *     The field type
   * @return the current {@link Parameters} instance for method chaining
   * @see FieldPredicates
   */
  public <T> Parameters randomize(final Predicate<Field> predicate,
      final Randomizer<T> randomizer) {
    requireNonNull(predicate, "Predicate must not be null");
    requireNonNull(randomizer, "Randomizer must not be null");
    customRandomizerRegistry.register(predicate, randomizer);
    return this;
  }

  /**
   * Register a custom randomizer for a given type.
   *
   * @param type
   *     class of the type to randomize
   * @param randomizer
   *     the custom {@link Randomizer} to use
   * @param <T>
   *     The field type
   * @return the current {@link Parameters} instance for method chaining
   */
  public <T> Parameters randomize(final Class<T> type, final Randomizer<T> randomizer) {
    requireNonNull(type, "Type must not be null");
    requireNonNull(randomizer, "Randomizer must not be null");
    customRandomizerRegistry.register(type, randomizer);
    return this;
  }

  /**
   * Exclude a field from being randomized.
   *
   * @param predicate
   *     to identify the field to exclude
   * @return the current {@link Parameters} instance for method chaining
   * @see FieldPredicates
   */
  public Parameters excludeField(final Predicate<Field> predicate) {
    requireNonNull(predicate, "Predicate must not be null");
    fieldExclusionPredicates.add(predicate);
    exclusionRandomizerRegistry.addFieldPredicate(predicate);
    return this;
  }

  /**
   * Exclude a type from being randomized.
   *
   * @param predicate
   *     to identify the type to exclude
   * @return the current {@link Parameters} instance for method chaining
   * @see FieldPredicates
   */
  public Parameters excludeType(final Predicate<Class<?>> predicate) {
    requireNonNull(predicate, "Predicate must not be null");
    typeExclusionPredicates.add(predicate);
    exclusionRandomizerRegistry.addTypePredicate(predicate);
    return this;
  }

  /**
   * Provide a custom exclusion policy.
   *
   * @param exclusionPolicy
   *     to use
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters exclusionPolicy(final ExclusionPolicy exclusionPolicy) {
    setExclusionPolicy(exclusionPolicy);
    return this;
  }

  /**
   * Provide a custom object factory.
   *
   * @param objectFactory
   *     to use
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters objectFactory(final ObjectFactory objectFactory) {
    setObjectFactory(objectFactory);
    return this;
  }

  /**
   * Provide a custom randomizer provider.
   *
   * @param randomizerProvider
   *     to use
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters randomizerProvider(final RandomizerProvider randomizerProvider) {
    setRandomizerProvider(randomizerProvider);
    return this;
  }

  /**
   * Set the initial random seed.
   *
   * @param seed
   *     the initial seed
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters seed(final long seed) {
    setSeed(seed);
    return this;
  }

  /**
   * Set the collection size range.
   *
   * @param minCollectionSize
   *     the minimum collection size
   * @param maxCollectionSize
   *     the maximum collection size
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters collectionSizeRange(final int minCollectionSize,
      final int maxCollectionSize) {
    if (minCollectionSize < 0) {
      throw new IllegalArgumentException("minCollectionSize must be >= 0");
    }
    if (minCollectionSize > maxCollectionSize) {
      throw new IllegalArgumentException(format(
          "minCollectionSize (%s) must be <= than maxCollectionSize (%s)",
          minCollectionSize, maxCollectionSize));
    }
    setCollectionSizeRange(
        new CloseRange<>(minCollectionSize, maxCollectionSize));
    return this;
  }

  /**
   * Set the string length range.
   *
   * @param minStringLength
   *     the minimum string length
   * @param maxStringLength
   *     the maximum string length
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters stringLengthRange(final int minStringLength,
      final int maxStringLength) {
    if (minStringLength < 0) {
      throw new IllegalArgumentException("minStringLength must be >= 0");
    }
    if (minStringLength > maxStringLength) {
      throw new IllegalArgumentException(
          format("minStringLength (%s) must be <= than maxStringLength (%s)",
              minStringLength, maxStringLength));
    }
    setStringLengthRange(new CloseRange<>(minStringLength, maxStringLength));
    return this;
  }

  /**
   * Set the number of different objects to generate for a type.
   *
   * @param objectPoolSize
   *     the number of objects to generate in the pool
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters objectPoolSize(final int objectPoolSize) {
    setObjectPoolSize(objectPoolSize);
    return this;
  }

  /**
   * Set the randomization depth for objects graph.
   *
   * @param randomizationDepth
   *     the maximum randomization depth
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters randomizationDepth(final int randomizationDepth) {
    setRandomizationDepth(randomizationDepth);
    return this;
  }

  /**
   * Set the charset to use for character based fields.
   *
   * @param charset
   *     the charset to use
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters charset(final Charset charset) {
    setCharset(charset);
    return this;
  }

  /**
   * Set the date range.
   *
   * @param min
   *     date
   * @param max
   *     date
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters dateRange(final LocalDate min, final LocalDate max) {
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("Min date should be before max date");
    }
    setDateRange(new CloseRange<>(min, max));
    return this;
  }

  /**
   * Set the time range.
   *
   * @param min
   *     time
   * @param max
   *     time
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters timeRange(final LocalTime min, final LocalTime max) {
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("Min time should be before max time");
    }
    setTimeRange(new CloseRange<>(min, max));
    return this;
  }

  /**
   * Register a {@link RandomizerRegistry}.
   *
   * @param registry
   *     the {@link RandomizerRegistry} to register
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters randomizerRegistry(final RandomizerRegistry registry) {
    requireNonNull(registry, "Registry must not be null");
    userRegistries.add(registry);
    return this;
  }

  /**
   * Should the classpath be scanned for concrete types when a field with an
   * interface or abstract class type is encountered?
   *
   * <p>Deactivated by default.
   *
   * @param scanClasspathForConcreteTypes
   *     whether to scan the classpath or not
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters scanClasspathForConcreteTypes(
      final boolean scanClasspathForConcreteTypes) {
    setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
    return this;
  }

  /**
   * With this parameter, any randomization error will be silently ignored and
   * the corresponding field will be set to null.
   *
   * <p>Deactivated by default.
   *
   * @param ignoreRandomizationErrors
   *     whether to silently ignore randomization errors or not
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters ignoreRandomizationErrors(
      final boolean ignoreRandomizationErrors) {
    setIgnoreErrors(ignoreRandomizationErrors);
    return this;
  }

  /**
   * Should default initialization of field values be overridden? E.g. should
   * the values of the {@code strings} and {@code integers} fields below be kept
   * untouched or should they be randomized.
   *
   * <pre>
   * {@code
   * public class Bean {
   *     Set<String> strings = new HashSet<>();
   *     List<Integer> integers;
   *
   *     public Bean() {
   *         integers = Arrays.asList(1, 2, 3);
   *     }
   * }}
   * </pre>
   *
   * <p>Deactivated by default.
   *
   * @param overrideDefaultInitialization
   *     whether to override default initialization of field values or not
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters overrideDefaultInitialization(
      final boolean overrideDefaultInitialization) {
    setOverrideDefaultInitialization(overrideDefaultInitialization);
    return this;
  }

  /**
   * Flag to bypass setters if any and use reflection directly instead. False by
   * default.
   *
   * @param bypassSetters
   *     true if setters should be ignored
   * @return the current {@link Parameters} instance for method chaining
   */
  public Parameters bypassSetters(final boolean bypassSetters) {
    setBypassSetters(bypassSetters);
    return this;
  }

  public Parameters clearNonNullFields() {
    nonNullFields.clear();
    return this;
  }

  public Parameters addNonNullField(final Field field) {
    nonNullFields.add(field);
    return this;
  }

  public Parameters addNonNullFields(final Collection<Field> fields) {
    nonNullFields.addAll(fields);
    return this;
  }

  public Parameters removeNonNullField(final Field field) {
    nonNullFields.remove(field);
    return this;
  }

  public Parameters setNonNullFields(final Collection<Field> fields) {
    nonNullFields.clear();
    nonNullFields.addAll(fields);
    return this;
  }

  @Override
  public Parameters clone() {
    return new Parameters(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Parameters other = (Parameters) o;
    return Equality.equals(seed, other.seed)
        && Equality.equals(objectPoolSize, other.objectPoolSize)
        && Equality.equals(randomizationDepth, other.randomizationDepth)
        && Equality.equals(maxLoops, other.maxLoops)
        && Equality.equals(scanClasspathForConcreteTypes, other.scanClasspathForConcreteTypes)
        && Equality.equals(excludeTransient, other.excludeTransient)
        && Equality.equals(overrideDefaultInitialization, other.overrideDefaultInitialization)
        && Equality.equals(overrideFinal, other.overrideFinal)
        && Equality.equals(overrideFinalDefaultInitialization,
            other.overrideFinalDefaultInitialization)
        && Equality.equals(ignoreErrors, other.ignoreErrors)
        && Equality.equals(bypassSetters, other.bypassSetters)
        && Equality.equals(charset, other.charset)
        && Equality.equals(collectionSizeRange, other.collectionSizeRange)
        && Equality.equals(stringLengthRange, other.stringLengthRange)
        && Equality.equals(dateRange, other.dateRange)
        && Equality.equals(timeRange, other.timeRange)
        && Equality.equals(exclusionPolicy, other.exclusionPolicy)
        && Equality.equals(objectFactory, other.objectFactory)
        && Equality.equals(randomizerProvider, other.randomizerProvider)
        && Equality.equals(customRandomizerRegistry, other.customRandomizerRegistry)
        && Equality.equals(exclusionRandomizerRegistry, other.exclusionRandomizerRegistry)
        && Equality.equals(userRegistries, other.userRegistries)
        && Equality.equals(fieldExclusionPredicates, other.fieldExclusionPredicates)
        && Equality.equals(typeExclusionPredicates, other.typeExclusionPredicates)
        && Equality.equals(nonNullFields, other.nonNullFields);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, seed);
    result = Hash.combine(result, multiplier, objectPoolSize);
    result = Hash.combine(result, multiplier, randomizationDepth);
    result = Hash.combine(result, multiplier, maxLoops);
    result = Hash.combine(result, multiplier, charset);
    result = Hash.combine(result, multiplier, scanClasspathForConcreteTypes);
    result = Hash.combine(result, multiplier, excludeTransient);
    result = Hash.combine(result, multiplier, overrideDefaultInitialization);
    result = Hash.combine(result, multiplier, overrideFinal);
    result = Hash.combine(result, multiplier, overrideFinalDefaultInitialization);
    result = Hash.combine(result, multiplier, ignoreErrors);
    result = Hash.combine(result, multiplier, bypassSetters);
    result = Hash.combine(result, multiplier, collectionSizeRange);
    result = Hash.combine(result, multiplier, stringLengthRange);
    result = Hash.combine(result, multiplier, dateRange);
    result = Hash.combine(result, multiplier, timeRange);
    result = Hash.combine(result, multiplier, exclusionPolicy);
    result = Hash.combine(result, multiplier, objectFactory);
    result = Hash.combine(result, multiplier, randomizerProvider);
    result = Hash.combine(result, multiplier, customRandomizerRegistry);
    result = Hash.combine(result, multiplier, exclusionRandomizerRegistry);
    result = Hash.combine(result, multiplier, userRegistries);
    result = Hash.combine(result, multiplier, fieldExclusionPredicates);
    result = Hash.combine(result, multiplier, typeExclusionPredicates);
    result = Hash.combine(result, multiplier, nonNullFields);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("seed", seed)
        .append("objectPoolSize", objectPoolSize)
        .append("randomizationDepth", randomizationDepth)
        .append("maxLoops", maxLoops)
        .append("charset", charset)
        .append("scanClasspathForConcreteTypes", scanClasspathForConcreteTypes)
        .append("excludeTransient", excludeTransient)
        .append("overrideDefaultInitialization", overrideDefaultInitialization)
        .append("overrideFinal", overrideFinal)
        .append("overrideFinalDefaultInitialization", overrideFinalDefaultInitialization)
        .append("ignoreErrors", ignoreErrors)
        .append("bypassSetters", bypassSetters)
        .append("collectionSizeRange", collectionSizeRange)
        .append("stringLengthRange", stringLengthRange)
        .append("dateRange", dateRange)
        .append("timeRange", timeRange)
        .append("exclusionPolicy", exclusionPolicy)
        .append("objectFactory", objectFactory)
        .append("randomizerProvider", randomizerProvider)
        .append("customRandomizerRegistry", customRandomizerRegistry)
        .append("exclusionRandomizerRegistry", exclusionRandomizerRegistry)
        .append("userRegistries", userRegistries)
        .append("fieldExclusionPredicates", fieldExclusionPredicates)
        .append("typeExclusionPredicates", typeExclusionPredicates)
        .append("nonNullFields", nonNullFields)
        .toString();
  }
}
