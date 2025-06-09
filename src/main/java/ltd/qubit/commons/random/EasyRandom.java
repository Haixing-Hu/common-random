////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.io.Serial;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.ExclusionPolicy;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.misc.EnumRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.lang.ClassUtils.isArrayType;
import static ltd.qubit.commons.lang.ClassUtils.isCollectionType;
import static ltd.qubit.commons.lang.ClassUtils.isEnumType;
import static ltd.qubit.commons.lang.ClassUtils.isIntrospectable;
import static ltd.qubit.commons.lang.ClassUtils.isMapType;
import static ltd.qubit.commons.random.util.ReflectionUtils.getEmptyImplementationForCollectionInterface;
import static ltd.qubit.commons.random.util.ReflectionUtils.getEmptyImplementationForMapInterface;
import static ltd.qubit.commons.random.util.ReflectionUtils.getPopulatableFields;

/**
 * {@link java.util.Random} 的扩展，能够生成随机的Java对象。
 *
 * @author 胡海星
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EasyRandom extends RandomEx {

  @Serial
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
   * 使用默认参数创建一个新的 {@link EasyRandom} 实例。
   */
  public EasyRandom() {
    this(new Parameters());
  }

  /**
   * 创建一个新的 {@link EasyRandom} 实例。
   *
   * @param parameters
   *     随机化参数。
   */
  public EasyRandom(final Parameters parameters) {
    this.parameters = requireNonNull(parameters, "Parameters must not be null");
    setSeed(parameters.getSeed());
    objectFactory = parameters.getObjectFactory();
    enumRandomizersByType = new ConcurrentHashMap<>();
    arrayPopulator = new ArrayPopulator(this);
    mapPopulator = new MapPopulator(this);
    collectionPopulator = new CollectionPopulator(this);
    // Nota that randomizerProvider must be created after the initialization
    // of all other populators, since some provider may need them.
    randomizerProvider = buildRandomizerProvider(parameters);
    // Note that the field populator must be created after the randomizerProvider
    // is created, since the field populator needs the randomizerProvider.
    fieldPopulator = new FieldPopulator(this);
  }

  /**
   * 构建随机化器提供者。
   *
   * @param parameters
   *     参数
   * @return 随机化器提供者
   */
  private RandomizerProvider buildRandomizerProvider(
      final Parameters parameters) {
    final LinkedHashSet<RandomizerRegistry> registries = setupRegistries(parameters);
    final RandomizerProvider provider = parameters.getRandomizerProvider();
    final RandomizerProvider result = (provider == null
                                       ? new RegistriesRandomizerProvider()
                                       : provider);
    result.addRegistries(registries);
    parameters.setRandomizerProvider(result);
    return result;
  }

  /**
   * 获取此随机生成器的日志记录器。
   *
   * @return 此随机生成器的日志记录器。
   */
  public Logger getLogger() {
    return logger;
  }

  /**
   * 获取此随机生成器的参数。
   *
   * @return 此随机生成器的参数。
   */
  public final Parameters getParameters() {
    return parameters;
  }

  /**
   * 获取此随机生成器的随机化器提供者。
   *
   * @return 此随机生成器的随机化器提供者。
   */
  public final RandomizerProvider getRandomizerProvider() {
    return randomizerProvider;
  }

  /**
   * 生成给定类型的随机实例。
   *
   * @param type
   *     将为其生成实例的类型。
   * @param <T>
   *     目标对象的实际类型。
   * @return 给定类型的随机实例。
   * @throws ObjectCreationException
   *     当无法创建给定类型的新实例时。
   */
  public <T> T nextObject(final Class<T> type) {
    return doPopulateBean(type, new Context(type, parameters));
  }

  /**
   * 生成给定类型的随机实例。
   *
   * @param type
   *     将为其生成实例的类型。
   * @param context
   *     随机化上下文。
   * @param <T>
   *     目标对象的实际类型。
   * @return 给定类型的随机实例。
   * @throws ObjectCreationException
   *     当无法创建给定类型的新实例时。
   */
  public <T> T nextObject(final Class<T> type, final Context context) {
    return doPopulateBean(type, context);
  }

  /**
   * 生成给定类型的随机实例流。
   *
   * @param type
   *     将为其生成实例的类型。
   * @param streamSize
   *     要生成的实例数。
   * @param <T>
   *     目标对象的实际类型。
   * @return 给定类型的随机实例流。
   * @throws ObjectCreationException
   *     当无法创建给定类型的新实例时。
   */
  public <T> Stream<T> objects(final Class<T> type, final int streamSize) {
    if (streamSize < 0) {
      throw new IllegalArgumentException("The stream size must be positive");
    }
    return Stream.generate(() -> nextObject(type)).limit(streamSize);
  }

  /**
   * 执行Bean填充。
   *
   * @param type
   *     要填充的类型
   * @param context
   *     随机化上下文
   * @param <T>
   *     目标类型
   * @return 填充后的对象
   * @throws ObjectCreationException
   *     如果无法创建对象
   */
  private <T> T doPopulateBean(final Class<T> type, final Context context) {
    logger.debug("Populate bean: class = {}, context = {}", type, context);
    final ExclusionPolicy exclusionPolicy = parameters.getExclusionPolicy();
    if ((exclusionPolicy != null) && exclusionPolicy.shouldBeExcluded(type, context)) {
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
      final List<Field> fields = getPopulatableFields(type, result);
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

  /**
   * 随机化不可内省的类型。
   *
   * @param type
   *     要随机化的类型
   * @param context
   *     随机化上下文
   * @param <T>
   *     目标类型
   * @return 随机化的对象
   */
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
      return (T) getEmptyImplementationForCollectionInterface(type);
    }
    if (isMapType(type)) {
      return (T) getEmptyImplementationForMapInterface(type);
    }
    return null;
  }

  /**
   * 设置随机化器注册表。
   *
   * @param parameters
   *     参数
   * @return 随机化器注册表集合
   */
  private LinkedHashSet<RandomizerRegistry> setupRegistries(final Parameters parameters) {
    final LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<>();
    registries.add(parameters.getCustomRandomizerRegistry());
    registries.add(parameters.getExclusionRandomizerRegistry());
    registries.addAll(parameters.getUserRegistries());
    registries.addAll(loadRegistries());
    registries.forEach(registry -> registry.init(this, parameters));
    return registries;
  }

  /**
   * 加载随机化器注册表。
   *
   * @return 随机化器注册表集合
   */
  private Collection<RandomizerRegistry> loadRegistries() {
    final List<RandomizerRegistry> registries = new ArrayList<>();
    ServiceLoader.load(RandomizerRegistry.class).forEach(registries::add);
    return registries;
  }

  /**
   * 获取此随机生成器的字段填充器。
   *
   * @return 此随机生成器的字段填充器。
   */
  public final FieldPopulator getFieldPopulator() {
    return fieldPopulator;
  }

  /**
   * 获取此随机生成器的数组填充器。
   *
   * @return 此随机生成器的数组填充器。
   */
  public final ArrayPopulator getArrayPopulator() {
    return arrayPopulator;
  }

  /**
   * 获取此随机生成器的集合填充器。
   *
   * @return 此随机生成器的集合填充器。
   */
  public final CollectionPopulator getCollectionPopulator() {
    return collectionPopulator;
  }

  /**
   * 获取此随机生成器的映射填充器。
   *
   * @return 此随机生成器的映射填充器。
   */
  public final MapPopulator getMapPopulator() {
    return mapPopulator;
  }

  /**
   * 获取此随机生成器的对象工厂。
   *
   * @return 此随机生成器的对象工厂。
   */
  public final ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  /**
   * 生成一个指定元素类型的随机列表。
   *
   * @param elementType
   *     列表中元素的类型。
   * @param <E>
   *     列表中元素的类型。
   * @return 指定元素类型的随机列表。
   */
  public <E> List<E> nextList(final Class<E> elementType) {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    final List<E> result = new ArrayList<>();
    for (int i = 0; i < size; ++i) {
      result.add(nextObject(elementType));
    }
    return result;
  }

  /**
   * 生成一个指定元素类型的随机集。
   *
   * @param elementType
   *     集中元素的类型。
   * @param <E>
   *     集中元素的类型。
   * @return 指定元素类型的随机集。
   */
  public <E> Set<E> nextSet(final Class<E> elementType) {
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

  /**
   * 生成一个随机的数字字符数组。
   *
   * @return 随机的数字字符数组。
   */
  public final char[] nextDigitChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextDigitChars(size);
  }

  /**
   * 生成一个随机的小写字母字符数组。
   *
   * @return 随机的小写字母字符数组。
   */
  public final char[] nextLowercaseLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLowercaseLetterChars(size);
  }

  /**
   * 生成一个随机的大写字母字符数组。
   *
   * @return 随机的大写字母字符数组。
   */
  public final char[] nextUppercaseLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextUppercaseLetterChars(size);
  }

  /**
   * 生成一个随机的字母字符数组。
   *
   * @return 随机的字母字符数组。
   */
  public final char[] nextLetterChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLetterChars(size);
  }

  /**
   * 生成一个随机的字母数字字符数组。
   *
   * @return 随机的字母数字字符数组。
   */
  public final char[] nextLetterDigitChars() {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextLetterDigitChars(size);
  }

  /**
   * 生成一个随机的数字字符串。
   *
   * @return 随机的数字字符串。
   */
  public final String nextDigitString() {
    final CloseRange<Integer> lengthCloseRange = parameters.getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextDigitString(length);
  }

  /**
   * 生成一个随机的小写字母字符串。
   *
   * @return 随机的小写字母字符串。
   */
  public final String nextLowercaseLetterString() {
    final CloseRange<Integer> lengthCloseRange = parameters.getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLowercaseLetterString(length);
  }

  /**
   * 生成一个随机的大写字母字符串。
   *
   * @return 随机的大写字母字符串。
   */
  public final String nextUppercaseLetterString() {
    final CloseRange<Integer> lengthCloseRange = parameters.getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextUppercaseLetterString(length);
  }

  /**
   * 生成一个随机的字母字符串。
   *
   * @return 随机的字母字符串。
   */
  public final String nextLetterString() {
    final CloseRange<Integer> lengthCloseRange = parameters.getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLetterString(length);
  }

  /**
   * 生成一个随机的字母数字字符串。
   *
   * @return 随机的字母数字字符串。
   */
  public final String nextLetterDigitString() {
    final CloseRange<Integer> lengthCloseRange = parameters.getStringLengthRange();
    final int length = nextInt(lengthCloseRange);
    return nextLetterDigitString(length);
  }

  /**
   * 生成指定类型的对象数组。
   *
   * @param size
   *     要生成的数组大小。
   * @param type
   *     数组元素的类型。
   * @param <T>
   *     数组元素的类型。
   * @return 指定类型的对象数组。
   */
  public <T> T[] nextObjectArray(final int size, final Class<T> type) {
    @SuppressWarnings("unchecked")
    final T[] result = (T[]) Array.newInstance(type, size);
    for (int i = 0; i < size; ++i) {
      result[i] = nextObject(type);
    }
    return result;
  }

  /**
   * 生成指定类型的对象数组。
   *
   * @param type
   *     数组元素的类型。
   * @param <T>
   *     数组元素的类型。
   * @return 指定类型的对象数组。
   */
  public <T> T[] nextObjectArray(final Class<T> type) {
    final CloseRange<Integer> range = parameters.getCollectionSizeRange();
    final int size = nextInt(range);
    return nextObjectArray(size, type);
  }
}
