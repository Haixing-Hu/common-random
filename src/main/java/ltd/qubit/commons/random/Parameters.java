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
 * {@link EasyRandom} 实例的参数。
 *
 * @author 胡海星
 */
public class Parameters {

  /**
   * 默认的随机数种子。
   */
  public static final long DEFAULT_SEED = 123L;

  /**
   * 字符串的默认字符集。
   */
  public static final Charset DEFAULT_CHARSET = StandardCharsets.US_ASCII;

  /**
   * 默认的集合大小范围。
   */
  public static final CloseRange<Integer> DEFAULT_COLLECTION_SIZE_RANGE =
      new UnmodifiableCloseRange<>(1, 5);

  /**
   * 为一个类型生成的不同对象的数量。
   * <p>
   * <b>注意</b>：这是为一种类型生成的对象的最大数量。它必须足够大，以避免在集合中生成唯一值时出现重复的键。
   * <p>
   * 例如，如果您有一个{@code List<Foo>}字段，每个{@code Foo}对象都有一个唯一的字段
   * {@code Foo.key}。当使用循环生成{@code List<Foo>}字段时，如果列表的大小超过对象池的大小，
   * 则列表中生成的{@code Foo}对象可能具有重复的键。
   */
  public static final int DEFAULT_OBJECT_POOL_SIZE = Integer.MAX_VALUE;

  /**
   * 随机化深度的默认值，这意味着随机化深度是有限的。
   */
  public static final int DEFAULT_RANDOMIZATION_DEPTH = 20;

  /**
   * 默认的字符串长度范围。
   */
  public static final CloseRange<Integer> DEFAULT_STRING_LENGTH_RANGE =
      new UnmodifiableCloseRange<>(1, 32);

  /**
   * 将在其中生成日期的默认日期范围：[now - 10 years, now + 10 years]。
   */
  public static final int DEFAULT_DATE_RANGE = 10;

  /**
   * 将围绕其生成随机日期的参考日期。
   */
  private static final ZonedDateTime REFERENCE_DATE =
      of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

  /**
   * 默认的日期范围。
   */
  public static final CloseRange<ZonedDateTime> DEFAULT_DATES_RANGE =
      new UnmodifiableCloseRange<>(
          REFERENCE_DATE.minusYears(DEFAULT_DATE_RANGE),
          REFERENCE_DATE.plusYears(DEFAULT_DATE_RANGE));

  /**
   * 在集合中生成唯一值的默认最大循环次数。
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
   * 使用默认值创建新的{@link Parameters}。
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

  /**
   * 基于另一个{@link Parameters}对象创建新的{@link Parameters}对象。
   *
   * @param parameters
   *     用于初始化新对象的{@link Parameters}对象。
   */
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

  /**
   * 获取集合大小的范围。
   *
   * @return 集合大小的范围。
   */
  public CloseRange<Integer> getCollectionSizeRange() {
    return collectionSizeRange;
  }

  /**
   * 设置集合大小的范围。
   *
   * @param collectionSizeRange
   *     要设置的集合大小范围。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setCollectionSizeRange(final CloseRange<Integer> collectionSizeRange) {
    this.collectionSizeRange = collectionSizeRange;
    return this;
  }

  /**
   * 获取日期的范围。
   *
   * @return 日期的范围。
   */
  public CloseRange<LocalDate> getDateRange() {
    return dateRange;
  }

  /**
   * 设置日期的范围。
   *
   * @param dateRange
   *     要设置的日期范围。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setDateRange(final CloseRange<LocalDate> dateRange) {
    this.dateRange = dateRange;
    return this;
  }

  /**
   * 获取时间的范围。
   *
   * @return 时间的范围。
   */
  public CloseRange<LocalTime> getTimeRange() {
    return timeRange;
  }

  /**
   * 设置时间的范围。
   *
   * @param timeRange
   *     要设置的时间范围。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setTimeRange(final CloseRange<LocalTime> timeRange) {
    this.timeRange = timeRange;
    return this;
  }

  /**
   * 获取字符串长度的范围。
   *
   * @return 字符串长度的范围。
   */
  public CloseRange<Integer> getStringLengthRange() {
    return stringLengthRange;
  }

  /**
   * 设置字符串长度的范围。
   *
   * @param stringLengthRange
   *     要设置的字符串长度范围。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setStringLengthRange(final CloseRange<Integer> stringLengthRange) {
    this.stringLengthRange = stringLengthRange;
    return this;
  }

  /**
   * 获取随机数生成器的种子。
   *
   * @return 随机数生成器的种子。
   */
  public long getSeed() {
    return seed;
  }

  /**
   * 设置随机数生成器的种子。
   *
   * @param seed
   *     要设置的随机数生成器的种子。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setSeed(final long seed) {
    this.seed = seed;
    return this;
  }

  /**
   * 获取对象池的大小。
   * <p>
   * 这是为一个类型生成的不同对象的数量。
   * <p>
   * <b>注意</b>：这是为一个类型生成的对象的最大数量。
   * 它必须足够大，以避免在集合中生成唯一值时出现重复的键。
   * <p>
   * 例如，如果您有一个{@code List<Foo>}字段，每个{@code Foo}对象都有一个唯一的字段
   * {@code Foo.key}。当使用循环生成{@code List<Foo>}字段时，如果列表的大小超过对象池的大小，
   * 则列表中生成的{@code Foo}对象可能具有重复的键。
   *
   * @return 对象池的大小。
   */
  public int getObjectPoolSize() {
    return objectPoolSize;
  }

  /**
   * 设置对象池的大小。
   * <p>
   * <b>注意</b>：这是为一种类型生成的对象的最大数量。它必须足够大，
   * 以避免在集合中生成唯一值时出现重复的键。
   * <p>
   * 例如，如果您有一个{@code List<Foo>}字段，每个{@code Foo}对象都有一个唯一的字段
   * {@code Foo.key}。当使用循环生成{@code List<Foo>}字段时，如果列表的大小超过对象池的大小，
   * 则列表中生成的{@code Foo}对象可能具有重复的键。
   *
   * @param objectPoolSize
   *     对象池的大小，它控制为同一类型生成的对象的最大数量。
   * @return 当前的{@link Parameters}实例，用于方法链式调用。
   */
  public Parameters setObjectPoolSize(final int objectPoolSize) {
    if (objectPoolSize < 1) {
      throw new IllegalArgumentException("objectPoolSize must be >= 1");
    }
    this.objectPoolSize = objectPoolSize;
    return this;
  }

  /**
   * 获取随机化深度。
   *
   * @return 随机化深度。
   */
  public int getRandomizationDepth() {
    return randomizationDepth;
  }

  /**
   * 设置随机化深度。
   *
   * @param randomizationDepth
   *     要设置的随机化深度。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setRandomizationDepth(final int randomizationDepth) {
    if (randomizationDepth < 1) {
      throw new IllegalArgumentException("randomizationDepth must be >= 1");
    }
    this.randomizationDepth = randomizationDepth;
    return this;
  }

  /**
   * 获取最大循环次数。
   *
   * @return 最大循环次数。
   */
  public final int getMaxLoops() {
    return maxLoops;
  }

  /**
   * 设置最大循环次数。
   *
   * @param maxLoops
   *     要设置的最大循环次数。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setMaxLoops(final int maxLoops) {
    if (maxLoops <= 0) {
      throw new IllegalArgumentException("max loops must be positive.");
    }
    this.maxLoops = maxLoops;
    return this;
  }

  /**
   * 获取字符集。
   *
   * @return 字符集。
   */
  public Charset getCharset() {
    return charset;
  }

  /**
   * 设置字符集。
   *
   * @param charset
   *     要设置的字符集。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setCharset(final Charset charset) {
    this.charset = requireNonNull(charset, "Charset must not be null");
    return this;
  }

  /**
   * 检查是否扫描类路径以查找具体类型。
   *
   * @return 如果扫描类路径以查找具体类型，则为true，否则为false。
   */
  public boolean isScanClasspathForConcreteTypes() {
    return scanClasspathForConcreteTypes;
  }

  /**
   * 设置是否扫描类路径以查找具体类型。
   *
   * @param scanClasspathForConcreteTypes
   *     如果扫描类路径以查找具体类型，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public Parameters setScanClasspathForConcreteTypes(
      final boolean scanClasspathForConcreteTypes) {
    this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    return this;
  }

  /**
   * 检查是否排除transient字段。
   *
   * @return 如果排除transient字段，则为true，否则为false。
   */
  public final boolean isExcludeTransient() {
    return excludeTransient;
  }

  /**
   * 设置是否排除transient字段。
   *
   * @param excludeTransient
   *     如果排除transient字段，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setExcludeTransient(final boolean excludeTransient) {
    this.excludeTransient = excludeTransient;
    return this;
  }

  /**
   * 检查是否覆盖默认初始化。
   *
   * @return 如果覆盖默认初始化，则为true，否则为false。
   */
  public boolean isOverrideDefaultInitialization() {
    return overrideDefaultInitialization;
  }

  /**
   * 设置是否覆盖默认初始化。
   *
   * @param overrideDefaultInitialization
   *     如果覆盖默认初始化，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setOverrideDefaultInitialization(
      final boolean overrideDefaultInitialization) {
    this.overrideDefaultInitialization = overrideDefaultInitialization;
    return this;
  }

  /**
   * 检查是否覆盖final字段。
   *
   * @return 如果覆盖final字段，则为true，否则为false。
   */
  public final boolean isOverrideFinal() {
    return overrideFinal;
  }

  /**
   * 设置是否覆盖final字段。
   *
   * @param overrideFinal
   *     如果覆盖final字段，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setOverrideFinal(final boolean overrideFinal) {
    this.overrideFinal = overrideFinal;
    return this;
  }

  /**
   * 检查是否覆盖final字段的默认初始化。
   *
   * @return 如果覆盖final字段的默认初始化，则为true，否则为false。
   */
  public final boolean isOverrideFinalDefaultInitialization() {
    return overrideFinalDefaultInitialization;
  }

  /**
   * 设置是否覆盖final字段的默认初始化。
   *
   * @param overrideFinalDefaultInitialization
   *     如果覆盖final字段的默认初始化，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setOverrideFinalDefaultInitialization(
      final boolean overrideFinalDefaultInitialization) {
    this.overrideFinalDefaultInitialization = overrideFinalDefaultInitialization;
    return this;
  }

  /**
   * 检查是否忽略错误。
   *
   * @return 如果忽略错误，则为true，否则为false。
   */
  public boolean isIgnoreErrors() {
    return ignoreErrors;
  }

  /**
   * 设置是否忽略错误。
   *
   * @param ignoreErrors
   *     如果忽略错误，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setIgnoreErrors(final boolean ignoreErrors) {
    this.ignoreErrors = ignoreErrors;
    return this;
  }

  /**
   * 检查是否绕过setter。
   *
   * @return 如果绕过setter，则为true，否则为false。
   */
  public boolean isBypassSetters() {
    return bypassSetters;
  }

  /**
   * 设置是否绕过setter。
   *
   * @param bypassSetters
   *     如果绕过setter，则为true，否则为false。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setBypassSetters(final boolean bypassSetters) {
    this.bypassSetters = bypassSetters;
    return this;
  }

  /**
   * 获取排除策略。
   *
   * @return 排除策略。
   */
  public ExclusionPolicy getExclusionPolicy() {
    return exclusionPolicy;
  }

  /**
   * 设置排除策略。
   *
   * @param exclusionPolicy
   *     要设置的排除策略。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setExclusionPolicy(final ExclusionPolicy exclusionPolicy) {
    this.exclusionPolicy = requireNonNull(exclusionPolicy,
        "Exclusion policy must not be null");
    return this;
  }

  /**
   * 获取对象工厂。
   *
   * @return 对象工厂。
   */
  public ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  /**
   * 设置对象工厂。
   *
   * @param objectFactory
   *     要设置的对象工厂。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setObjectFactory(final ObjectFactory objectFactory) {
    this.objectFactory = requireNonNull(objectFactory,
        "Object factory must not be null");
    return this;
  }

  /**
   * 获取随机化器提供程序。
   *
   * @return 随机化器提供程序。
   */
  public RandomizerProvider getRandomizerProvider() {
    return randomizerProvider;
  }

  /**
   * 设置随机化器提供程序。
   *
   * @param randomizerProvider
   *     要设置的随机化器提供程序。
   * @return 此{@link Parameters}实例。
   */
  public final Parameters setRandomizerProvider(final RandomizerProvider randomizerProvider) {
    this.randomizerProvider = requireNonNull(randomizerProvider,
        "Randomizer provider must not be null");
    return this;
  }

  /**
   * 获取字段排除谓词的集合。
   *
   * @return 字段排除谓词的集合。
   */
  public Set<Predicate<Field>> getFieldExclusionPredicates() {
    return fieldExclusionPredicates;
  }

  /**
   * 获取类型排除谓词的集合。
   *
   * @return 类型排除谓词的集合。
   */
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

  /**
   * 获取所有非空字段的集合。
   *
   * @return 所有非空字段的集合。
   */
  public Set<Field> getNonNullFields() {
    return nonNullFields;
  }

  /**
   * 为满足特定谓词的字段注册一个自定义随机化器。
   *
   * @param predicate
   *     用于应用随机化器的字段的谓词
   * @param randomizer
   *     要注册的随机化器
   * @param <T>
   *     随机化器将生成的类型
   * @return 一个新的{@link Parameters}实例
   */
  public <T> Parameters randomize(final Predicate<Field> predicate,
      final Randomizer<T> randomizer) {
    requireNonNull(predicate, "Predicate must not be null");
    requireNonNull(randomizer, "Randomizer must not be null");
    customRandomizerRegistry.register(predicate, randomizer);
    return this;
  }

  /**
   * 为给定类型注册一个自定义随机化器。
   *
   * @param type
   *     要为其注册随机化器的类型
   * @param randomizer
   *     要注册的随机化器
   * @param <T>
   *     随机化器将生成的类型
   * @return 一个新的{@link Parameters}实例
   */
  public <T> Parameters randomize(final Class<T> type, final Randomizer<T> randomizer) {
    requireNonNull(type, "Type must not be null");
    requireNonNull(randomizer, "Randomizer must not be null");
    customRandomizerRegistry.register(type, randomizer);
    return this;
  }

  /**
   * 添加一个谓词，用于在填充过程中排除某些字段。
   *
   * @param predicate
   *     用于排除字段的谓词
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters excludeField(final Predicate<Field> predicate) {
    requireNonNull(predicate, "Predicate must not be null");
    fieldExclusionPredicates.add(predicate);
    exclusionRandomizerRegistry.addFieldPredicate(predicate);
    return this;
  }

  /**
   * 添加一个谓词，用于在填充过程中排除某些类型。
   *
   * @param predicate
   *     用于排除类型的谓词
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters excludeType(final Predicate<Class<?>> predicate) {
    requireNonNull(predicate, "Predicate must not be null");
    typeExclusionPredicates.add(predicate);
    exclusionRandomizerRegistry.addTypePredicate(predicate);
    return this;
  }

  /**
   * 设置一个排除策略。
   *
   * @param exclusionPolicy
   *     要使用的排除策略
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters exclusionPolicy(final ExclusionPolicy exclusionPolicy) {
    setExclusionPolicy(exclusionPolicy);
    return this;
  }

  /**
   * 设置一个对象工厂。
   *
   * @param objectFactory
   *     要使用的对象工厂
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters objectFactory(final ObjectFactory objectFactory) {
    setObjectFactory(objectFactory);
    return this;
  }

  /**
   * 设置一个随机化器提供程序。
   *
   * @param randomizerProvider
   *     要使用的随机化器提供程序
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters randomizerProvider(final RandomizerProvider randomizerProvider) {
    setRandomizerProvider(randomizerProvider);
    return this;
  }

  /**
   * 设置随机数生成器的种子。
   *
   * @param seed
   *     要使用的种子
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters seed(final long seed) {
    setSeed(seed);
    return this;
  }

  /**
   * 为集合大小设置一个范围。
   *
   * @param minCollectionSize
   *     集合的最小大小
   * @param maxCollectionSize
   *     集合的最大大小
   * @return 一个新的{@link Parameters}实例
   * @throws IllegalArgumentException
   *     如果minCollectionSize大于maxCollectionSize
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
   * 为字符串长度设置一个范围。
   *
   * @param minStringLength
   *     字符串的最小长度
   * @param maxStringLength
   *     字符串的最大长度
   * @return 一个新的{@link Parameters}实例
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
   * 为一种类型设置要生成的不同对象的数量。
   *
   * @param objectPoolSize
   *     对象池大小
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters objectPoolSize(final int objectPoolSize) {
    setObjectPoolSize(objectPoolSize);
    return this;
  }

  /**
   * 设置随机化深度。
   *
   * @param randomizationDepth
   *     随机化深度
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters randomizationDepth(final int randomizationDepth) {
    setRandomizationDepth(randomizationDepth);
    return this;
  }

  /**
   * 为随机字符串设置字符集。
   *
   * @param charset
   *     要使用的字符集
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters charset(final Charset charset) {
    setCharset(charset);
    return this;
  }

  /**
   * 为随机日期设置一个范围。
   *
   * @param min
   *     最小日期
   * @param max
   *     最大日期
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters dateRange(final LocalDate min, final LocalDate max) {
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("Min date should be before max date");
    }
    setDateRange(new CloseRange<>(min, max));
    return this;
  }

  /**
   * 为随机时间设置一个范围。
   *
   * @param min
   *     最小时间
   * @param max
   *     最大时间
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters timeRange(final LocalTime min, final LocalTime max) {
    if (min.isAfter(max)) {
      throw new IllegalArgumentException("Min time should be before max time");
    }
    setTimeRange(new CloseRange<>(min, max));
    return this;
  }

  /**
   * 注册一个新的随机化器注册表。
   *
   * @param registry
   *     要注册的注册表
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters randomizerRegistry(final RandomizerRegistry registry) {
    requireNonNull(registry, "Registry must not be null");
    userRegistries.add(registry);
    return this;
  }

  /**
   * 激活/停用在类路径上扫描具体类型以实例化抽象类型或接口。
   *
   * @param scanClasspathForConcreteTypes
   *     如果为true，则激活在类路径上扫描具体类型
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters scanClasspathForConcreteTypes(
      final boolean scanClasspathForConcreteTypes) {
    setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
    return this;
  }

  /**
   * 设置一个标志以忽略随机化错误。如果设置为true，
   * 当在随机化bean时发生错误，该错误将被记录但不会重新抛出，
   * 并为有问题的字段返回null。否则，默认行为是重新抛出异常。
   *
   * @param ignoreRandomizationErrors
   *     如果为true，则忽略随机化错误
   * @return 一个新的{@link Parameters}实例
   * @deprecated 这个方法已被更名为
   * {@link #ignoreErrors(boolean)}
   */
  public Parameters ignoreRandomizationErrors(
      final boolean ignoreRandomizationErrors) {
    setIgnoreErrors(ignoreRandomizationErrors);
    return this;
  }

  /**
   * 设置一个标志以忽略随机化错误。如果设置为true，
   * 当在随机化bean时发生错误，该错误将被记录但不会重新抛出，
   * 并为有问题的字段返回null。否则，默认行为是重新抛出异常。
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
   *     如果应覆盖具有默认值的字段，则为true
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters overrideDefaultInitialization(
      final boolean overrideDefaultInitialization) {
    setOverrideDefaultInitialization(overrideDefaultInitialization);
    return this;
  }

  /**
   * 是否应绕过字段的setter。
   *
   * @param bypassSetters
   *     如果应绕过字段的setter，则为true
   * @return 一个新的{@link Parameters}实例
   */
  public Parameters bypassSetters(final boolean bypassSetters) {
    setBypassSetters(bypassSetters);
    return this;
  }

  /**
   * 清除非空字段的集合。
   *
   * @return 此{@link Parameters}实例。
   */
  public Parameters clearNonNullFields() {
    nonNullFields.clear();
    return this;
  }

  /**
   * 将一个字段添加到非空字段集合中。
   *
   * @param field
   *     要添加的字段。
   * @return 此{@link Parameters}实例。
   */
  public Parameters addNonNullField(final Field field) {
    nonNullFields.add(field);
    return this;
  }

  /**
   * 将多个字段添加到非空字段集合中。
   *
   * @param fields
   *     要添加的字段集合。
   * @return 此{@link Parameters}实例。
   */
  public Parameters addNonNullFields(final Collection<Field> fields) {
    nonNullFields.addAll(fields);
    return this;
  }

  /**
   * 从非空字段集合中移除一个字段。
   *
   * @param field
   *     要移除的字段。
   * @return 此{@link Parameters}实例。
   */
  public Parameters removeNonNullField(final Field field) {
    nonNullFields.remove(field);
    return this;
  }

  /**
   * 设置非空字段的集合。
   *
   * @param fields
   *     要设置的字段集合。
   * @return 此{@link Parameters}实例。
   */
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
        && Equality.equals(overrideFinalDefaultInitialization, other.overrideFinalDefaultInitialization)
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
