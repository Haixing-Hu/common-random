////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TransferQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jodah.typetools.TypeResolver;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.annotation.RandomizerArgument;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.Option;
import ltd.qubit.commons.reflect.ReflectionException;
import ltd.qubit.commons.util.pair.Pair;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;

import static net.jodah.typetools.TypeResolver.resolveRawArgument;
import static net.jodah.typetools.TypeResolver.resolveRawArguments;

import static ltd.qubit.commons.lang.ClassUtils.isInterface;
import static ltd.qubit.commons.lang.ClassUtils.isJdkBuiltIn;
import static ltd.qubit.commons.lang.ClassUtils.isParameterizedType;
import static ltd.qubit.commons.reflect.AccessibleUtils.withAccessibleObject;
import static ltd.qubit.commons.reflect.FieldUtils.isFinal;
import static ltd.qubit.commons.reflect.FieldUtils.isStatic;

/**
 * 反射实用工具方法。
 *
 * <p><strong>此类仅供内部使用。所有公共方法（除了
 * {@link ReflectionUtils#asRandomizer(java.util.function.Supplier)}）
 * 可能会在次要版本之间更改，恕不另行通知。</strong>
 *
 * @author 胡海星
 */
public final class ReflectionUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

  private ReflectionUtils() {}

  /**
   * 创建一个动态代理，将给定的{@link Supplier}适配为{@link Randomizer}。
   *
   * @param supplier
   *     要适配的{@link Supplier}
   * @param <T>
   *     目标类型
   * @return 代理随机化器
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> Randomizer<T> asRandomizer(final Supplier<T> supplier) {

    class RandomizerProxy implements InvocationHandler {

      private final Supplier<?> target;

      private RandomizerProxy(final Supplier<?> target) {
        this.target = target;
      }

      @Override
      public Object invoke(final Object proxy, final Method method,
          final Object[] args) throws Throwable {
        if ("getRandomValue".equals(method.getName())) {
          final Method getMethod = target.getClass().getMethod("get");
          final boolean access = getMethod.isBridge();
          if (access) {
            return getMethod.invoke(target);
          } else {
            if (trySetAccessible(getMethod, true)) {
              return getMethod.invoke(target);
            } else {
              return null;
            }
          }
        }
        return null;
      }
    }

    return (Randomizer<T>) Proxy.newProxyInstance(
        Randomizer.class.getClassLoader(), new Class[]{Randomizer.class},
        new RandomizerProxy(supplier));
  }

  /**
   * 获取给定类型的声明字段。
   *
   * @param type
   *     要内省的类型
   * @param <T>
   *     要内省的实际类型
   * @return 声明字段的列表
   */
  public static <T> List<Field> getDeclaredFields(final T type) {
    return new ArrayList<>(asList(type.getClass().getDeclaredFields()));
  }

  /**
   * 获取给定类型的继承字段。
   *
   * @param type
   *     要内省的类型
   * @return 继承字段的列表
   */
  public static List<Field> getInheritedFields(final Class<?> type) {
    final List<Field> inheritedFields = new ArrayList<>();
    Class<?> theType = type;
    while (theType.getSuperclass() != null) {
      final Class<?> superclass = theType.getSuperclass();
      inheritedFields.addAll(asList(superclass.getDeclaredFields()));
      theType = superclass;
    }
    return inheritedFields;
  }

  /**
   * 在目标对象的字段中设置一个值。如果目标对象为该字段提供了setter，
   * 则将使用该setter。否则，将使用反射设置该字段。
   *
   * @param object
   *     要设置属性的实例
   * @param field
   *     要设置属性的字段
   * @param value
   *     要设置的值
   * @throws IllegalAccessException
   *     如果无法设置属性
   * @throws InvocationTargetException
   *     如果无法设置属性
   */
  public static void setProperty(final Object object, final Field field,
      final Object value)
      throws IllegalAccessException, InvocationTargetException {
    try {
      final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
          field.getName(), object.getClass());
      final Method setter = propertyDescriptor.getWriteMethod();
      if (setter != null) {
        setter.invoke(object, value);
      } else {
        setFieldValue(object, field, value);
      }
    } catch (final IllegalAccessException | IllegalArgumentException | IntrospectionException e) {
      //  LOGGER.error("Failed to set the property '{}' of {} to {}: {}", field.getName(),
      //      object.getClass().getName(), value, e.getMessage(), e);
      setFieldValue(object, field, value);
    }
  }

  /**
   * 尝试将方法设置为可访问。
   *
   * @param method
   *     要设置的方法。
   * @param value
   *     如果为 {@code true}，则表示该方法应该可访问；
   *     如果为 {@code false}，则表示该方法应该不可访问。
   * @return 如果操作成功，则为 {@code true}；否则为 {@code false}。
   */
  public static boolean trySetAccessible(final Method method, final boolean value) {
    try {
      method.setAccessible(value);
      //  LOGGER.debug("Successfully set the accessible the field '{}' of the class {} to {}.",
      //    method.getName(), method.getDeclaringClass().getName(), value);
      return true;
    } catch (final Exception e) {
      if ((e instanceof SecurityException)
          || e.getClass().getName().endsWith("InaccessibleObjectException")) {  // for DK >= 1.9
        LOGGER.warn("Cannot set the accessible the method '{}' of the class {}: {}",
            method.getName(), method.getDeclaringClass().getName(), e.getMessage());
        // ignore the exception
        return false;
      } else {
        throw e;    // other exception should be thrown
      }
    }
  }

  /**
   * 尝试将字段设置为可访问。
   *
   * @param field
   *     要设置的字段。
   * @param value
   *     如果为 {@code true}，则表示该字段应该可访问；
   *     如果为 {@code false}，则表示该字段应该不可访问。
   * @return 如果操作成功，则为 {@code true}；否则为 {@code false}。
   */
  public static boolean trySetAccessible(final Field field, final boolean value) {
    try {
      field.setAccessible(value);
      //  LOGGER.debug("Successfully set the accessible the field '{}' of the class {} to {}.",
      //      field.getName(), field.getDeclaringClass().getName(), value);
      return true;
    } catch (final Exception e) {
      if ((e instanceof SecurityException)
          || e.getClass().getName().endsWith("InaccessibleObjectException")) {  // for JDK >= 1.9
        LOGGER.warn("Cannot set the accessible the field '{}' of the class {}: {}",
            field.getName(), field.getDeclaringClass().getName(), e.getMessage());
        // ignore the exception
        return false;
      } else {
        throw e;    // other exception should be thrown
      }
    }
  }

  /**
   * 在目标对象中设置字段值。
   *
   * @param object
   *     要设置字段值的实例。
   * @param field
   *     要设置值的字段。
   * @param value
   *     要设置的值。
   * @throws ReflectionException
   *     如果无法设置字段值。
   */
  public static void setFieldValue(final Object object, final Field field,
      final Object value) throws ReflectionException {
    withAccessibleObject(field, f -> f.set(object, value));
  }

  /**
   * 从目标对象获取字段值。
   *
   * @param object
   *     要从中获取字段值的实例。
   * @param field
   *     要获取值的字段。
   * @return 字段的值。
   * @throws ReflectionException
   *     如果无法获取字段值。
   */
  public static Object getFieldValue(final Object object, final Field field)
      throws ReflectionException {
    return withAccessibleObject(field, f -> f.get(object), true);
  }

  /**
   * 获取给定原始类型的包装类型。
   *
   * @param primitiveType
   *     要为其获取包装类型的原始类型。
   * @return 给定原始类型的包装类型。
   */
  public static Class<?> getWrapperType(final Class<?> primitiveType) {
    for (final PrimitiveEnum p : PrimitiveEnum.values()) {
      if (p.getType().equals(primitiveType)) {
        return p.getClazz();
      }
    }
    return primitiveType; // if not primitive, return it as is
  }

  /**
   * 检查具有默认值的原始类型字段。
   *
   * @param object
   *     要内省的对象。
   * @param field
   *     要检查的字段。
   * @return 如果字段具有默认值，则为true。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   */
  public static boolean isPrimitiveFieldWithDefaultValue(final Object object,
      final Field field) throws IllegalAccessException {
    final Class<?> fieldType = field.getType();
    if (!fieldType.isPrimitive()) {
      return false;
    }
    final Object fieldValue = getFieldValue(object, field);
    return isPrimitiveDefaultValue(fieldType, fieldValue);
  }

  /**
   * 检查给定值是否为原始类型的默认值。
   *
   * @param type
   *     原始类型
   * @param value
   *     要检查的值
   * @return 如果值是原始类型的默认值，则返回true
   */
  private static boolean isPrimitiveDefaultValue(final Class<?> type,
      final Object value) {
    if (value == null) {
      return false;
    }
    if ((type == boolean.class) && (!((boolean) value))) {
      return true;
    }
    if ((type == byte.class) && ((byte) value == (byte) 0)) {
      return true;
    }
    if ((type == short.class) && ((short) value == (short) 0)) {
      return true;
    }
    if ((type == int.class) && ((int) value == 0)) {
      return true;
    }
    if ((type == long.class) && ((long) value == 0L)) {
      return true;
    }
    if ((type == float.class) && ((float) value == 0.0f)) {
      return true;
    }
    if ((type == double.class) && ((double) value == 0.0)) {
      return true;
    }
    return (type == char.class) && ((char) value == '\u0000');
  }

  /**
   * 在类路径中搜索给定接口或抽象类的所有公共具体子类型。
   *
   * @param type
   *     要搜索具体子类型的类型
   * @param <T>
   *     要内省的实际类型
   * @return 找到的所有具体子类型的列表
   */
  public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
    return ClassGraphFacade.getPublicConcreteSubTypesOf(type);
  }

  /**
   * 过滤类型列表，只保留与给定类型具有相同参数化类型的元素。
   *
   * @param types
   *     要过滤的类型列表
   * @param type
   *     用于搜索的类型
   * @return 与给定类型具有相同参数化类型的类型列表
   */
  public static List<Class<?>> filterSameParameterizedTypes(
      final List<Class<?>> types, final Type type) {
    if (type instanceof ParameterizedType) {
      final Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
      final List<Class<?>> result = new ArrayList<>();
      for (final Class<?> concreteType : types) {
        final List<Type[]> actualTypeArguments =
            getActualTypeArgumentsOfGenericInterfaces(concreteType);
        for (final Type[] arguments : actualTypeArguments) {
          if (Arrays.equals(typeArguments, arguments)) {
            result.add(concreteType);
            break;
          }
        }
      }
      return result;
    } else {
      return types;
    }
  }

  /**
   * 为{@link Collection}类型返回一个空的实现。
   *
   * @param collectionInterface
   *     需要返回空实现的集合接口
   * @return 集合接口的空实现
   */
  public static <T> Collection<T> getEmptyImplementationForCollectionInterface(
      final Class<?> collectionInterface) {
    Collection<T> collection = new ArrayList<>();
    if (List.class.isAssignableFrom(collectionInterface)) {
      collection = new ArrayList<>();
    } else if (NavigableSet.class.isAssignableFrom(collectionInterface)) {
      collection = new TreeSet<>();
    } else if (SortedSet.class.isAssignableFrom(collectionInterface)) {
      collection = new TreeSet<>();
    } else if (Set.class.isAssignableFrom(collectionInterface)) {
      collection = new HashSet<>();
    } else if (BlockingDeque.class.isAssignableFrom(collectionInterface)) {
      collection = new LinkedBlockingDeque<>();
    } else if (Deque.class.isAssignableFrom(collectionInterface)) {
      collection = new ArrayDeque<>();
    } else if (TransferQueue.class.isAssignableFrom(collectionInterface)) {
      collection = new LinkedTransferQueue<>();
    } else if (BlockingQueue.class.isAssignableFrom(collectionInterface)) {
      collection = new LinkedBlockingQueue<>();
    } else if (Queue.class.isAssignableFrom(collectionInterface)) {
      collection = new LinkedList<>();
    }
    return collection;
  }

  /**
   * 为给定类型创建一个空集合。
   *
   * @param type
   *     需要创建空集合的类型
   * @param size
   *     集合的初始大小
   * @return 空集合
   */
  @SuppressWarnings("unchecked")
  public static <T> Collection<T> getEmptyCollectionForType(final Class<?> type, final int size) {
    rejectUnsupportedTypes(type);
    Collection<T> collection;
    try {
      collection = (Collection<T>) type.getDeclaredConstructor().newInstance();
    } catch (final InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      if (type.equals(ArrayBlockingQueue.class)) {
        collection = new ArrayBlockingQueue<>(size);
      } else {
        collection = (Collection<T>) new ObjenesisStd().newInstance(type);
      }
    }
    return collection;
  }

  /**
   * 为给定的{@link Map}接口返回一个空的实现。
   *
   * @param mapInterface
   *     需要返回空实现的Map接口
   * @return 给定{@link Map}接口的空实现
   */
  public static Map<?, ?> getEmptyImplementationForMapInterface(
      final Class<?> mapInterface) {
    Map<?, ?> map = new HashMap<>();
    if (ConcurrentNavigableMap.class.isAssignableFrom(mapInterface)) {
      map = new ConcurrentSkipListMap<>();
    } else if (ConcurrentMap.class.isAssignableFrom(mapInterface)) {
      map = new ConcurrentHashMap<>();
    } else if (NavigableMap.class.isAssignableFrom(mapInterface)) {
      map = new TreeMap<>();
    } else if (SortedMap.class.isAssignableFrom(mapInterface)) {
      map = new TreeMap<>();
    }
    return map;
  }

  /**
   * 拒绝不支持的类型。
   *
   * @param type
   *     要检查的类型
   * @throws UnsupportedOperationException
   *     如果类型不受支持
   */
  private static void rejectUnsupportedTypes(final Class<?> type) {
    if (type.equals(SynchronousQueue.class)) {
      // SynchronousQueue is not supported since it requires a consuming thread at insertion time
      throw new UnsupportedOperationException(SynchronousQueue.class.getName()
          + " type is not supported");
    }
    if (type.equals(DelayQueue.class)) {
      // DelayQueue is not supported since it requires creating dummy delayed objects
      throw new UnsupportedOperationException(DelayQueue.class.getName()
          + " type is not supported");
    }
  }

  /**
   * 获取给定字段的读取方法。
   *
   * @param field
   *     要获取读取方法的字段
   * @return 读取方法的Optional，如果字段没有读取方法则为空
   */
  public static Optional<Method> getReadMethod(final Field field) {
    final String fieldName = field.getName();
    final Class<?> fieldClass = field.getDeclaringClass();
    final String capitalizedFieldName = fieldName.substring(0, 1)
                                                 .toUpperCase(ENGLISH)
        + fieldName.substring(1);
    // try to find getProperty
    final Optional<Method> getter = getPublicMethod(
        "get" + capitalizedFieldName, fieldClass);
    if (getter.isPresent()) {
      return getter;
    }
    // try to find isProperty for boolean properties
    return getPublicMethod("is" + capitalizedFieldName, fieldClass);
  }

  /**
   * 获取指定名称的公共方法。
   *
   * @param name
   *     方法名称
   * @param target
   *     目标类
   * @return 方法的Optional，如果没有找到则为空
   */
  private static Optional<Method> getPublicMethod(final String name,
      final Class<?> target) {
    try {
      return Optional.of(target.getMethod(name));
    } catch (final NoSuchMethodException | SecurityException e) {
      return Optional.empty();
    }
  }

  /**
   * 获取泛型接口的实际类型参数。
   *
   * @param type
   *     要检查的类型
   * @return 泛型接口的实际类型参数列表
   */
  private static List<Type[]> getActualTypeArgumentsOfGenericInterfaces(
      final Class<?> type) {
    final List<Type[]> result = new ArrayList<>();
    final Type[] genericInterfaceTypes = type.getGenericInterfaces();
    for (final Type current : genericInterfaceTypes) {
      if (current instanceof ParameterizedType) {
        result.add(((ParameterizedType) current).getActualTypeArguments());
      }
    }
    return result;
  }

  /**
   * 创建新的随机化器实例。
   *
   * @param type
   *     随机化器的类型
   * @param randomizerArguments
   *     随机化器的构造参数
   * @param <T>
   *     随机化器的泛型类型
   * @return 新的随机化器实例
   * @throws ObjectCreationException
   *     如果无法创建对象
   */
  @SuppressWarnings("unchecked")
  public static <T> Randomizer<T> newInstance(final Class<T> type,
      final RandomizerArgument[] randomizerArguments) {
    try {
      if (notEmpty(randomizerArguments)) {
        final Optional<Constructor<?>> matchingConstructor =
            Stream.of(type.getConstructors())
                  .filter(constructor ->
                      hasSameArgumentNumber(constructor, randomizerArguments)
                          && hasSameArgumentTypes(constructor,
                          randomizerArguments))
                  .findFirst();
        if (matchingConstructor.isPresent()) {
          return (Randomizer<T>) matchingConstructor.get()
                                                    .newInstance(
                                                        convertArguments(
                                                            randomizerArguments));
        }
      }
      return (Randomizer<T>) type.getDeclaredConstructor().newInstance();
    } catch (final IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException
        | InstantiationException e) {
      throw new ObjectCreationException(
          format(
              "Could not create Randomizer of type: %s with constructor arguments: %s",
              type, Arrays.toString(randomizerArguments)), e);
    }
  }

  /**
   * 检查随机化器参数数组是否非空。
   *
   * @param randomizerArguments
   *     随机化器参数数组
   * @return 如果数组非空且长度大于0，则返回true
   */
  private static boolean notEmpty(
      final RandomizerArgument[] randomizerArguments) {
    return randomizerArguments != null && randomizerArguments.length > 0;
  }

  /**
   * 检查构造函数是否具有与随机化器参数相同的参数数量。
   *
   * @param constructor
   *     构造函数
   * @param randomizerArguments
   *     随机化器参数数组
   * @return 如果参数数量相同，则返回true
   */
  private static boolean hasSameArgumentNumber(final Constructor<?> constructor,
      final RandomizerArgument[] randomizerArguments) {
    return constructor.getParameterCount() == randomizerArguments.length;
  }

  /**
   * 检查构造函数是否具有与随机化器参数相同的参数类型。
   *
   * @param constructor
   *     构造函数
   * @param randomizerArguments
   *     随机化器参数数组
   * @return 如果参数类型匹配，则返回true
   */
  private static boolean hasSameArgumentTypes(final Constructor<?> constructor,
      final RandomizerArgument[] randomizerArguments) {
    final Class<?>[] constructorParameterTypes = constructor
        .getParameterTypes();
    for (int i = 0; i < randomizerArguments.length; i++) {
      if (!constructorParameterTypes[i]
          .isAssignableFrom(randomizerArguments[i].type())) {
        // Argument types does not match
        return false;
      }
    }
    return true;
  }

  /**
   * 将声明的参数转换为对象数组。
   *
   * @param declaredArguments
   *     声明的参数数组
   * @return 转换后的对象数组
   */
  private static Object[] convertArguments(
      final RandomizerArgument[] declaredArguments) {
    final int numberOfArguments = declaredArguments.length;
    final Object[] arguments = new Object[numberOfArguments];
    for (int i = 0; i < numberOfArguments; i++) {
      final Class<?> type = declaredArguments[i].type();
      final String value = declaredArguments[i].value();
      // issue 299: if argument type is array, split values before conversion
      if (type.isArray()) {
        final Object[] values = Stream.of(value.split(","))
                                      .map(String::trim)
                                      .toArray();
        arguments[i] = convertArray(values, type);
      } else {
        arguments[i] = convertValue(value, type);
      }
    }
    return arguments;
  }

  /**
   * 将字符串值转换为指定的目标类型。
   *
   * @param value
   *     要转换的字符串值
   * @param targetType
   *     目标类型
   * @return 转换后的对象
   */
  @SuppressWarnings("deprecation")
  private static Object convertValue(final String value,
      final Class<?> targetType) {
    if (Boolean.class.equals(targetType) || Boolean.TYPE.equals(targetType)) {
      return Boolean.parseBoolean(value);
    }
    if (Byte.class.equals(targetType) || Byte.TYPE.equals(targetType)) {
      return Byte.parseByte(value);
    }
    if (Short.class.equals(targetType) || Short.TYPE.equals(targetType)) {
      return Short.parseShort(value);
    }
    if (Integer.class.equals(targetType) || Integer.TYPE.equals(targetType)) {
      return Integer.parseInt(value);
    }
    if (Long.class.equals(targetType) || Long.TYPE.equals(targetType)) {
      return Long.parseLong(value);
    }
    if (Float.class.equals(targetType) || Float.TYPE.equals(targetType)) {
      return Float.parseFloat(value);
    }
    if (Double.class.equals(targetType) || Double.TYPE.equals(targetType)) {
      return Double.parseDouble(value);
    }
    if (BigInteger.class.equals(targetType)) {
      return new BigInteger(value);
    }
    if (BigDecimal.class.equals(targetType)) {
      return new BigDecimal(value);
    }
    if (Date.class.equals(targetType)) {
      return DateUtils.parse(value);
    }
    if (java.sql.Date.class.equals(targetType)) {
      return java.sql.Date.valueOf(value);
    }
    if (java.sql.Time.class.equals(targetType)) {
      return java.sql.Time.valueOf(value);
    }
    if (java.sql.Timestamp.class.equals(targetType)) {
      return java.sql.Timestamp.valueOf(value);
    }
    if (LocalDate.class.equals(targetType)) {
      return LocalDate.parse(value);
    }
    if (LocalTime.class.equals(targetType)) {
      return LocalTime.parse(value);
    }
    if (LocalDateTime.class.equals(targetType)) {
      return LocalDateTime.parse(value);
    }
    return value;
  }

  /**
   * 将对象数组转换为指定的目标类型数组。
   *
   * @param array
   *     要转换的对象数组
   * @param targetType
   *     目标数组类型
   * @return 转换后的数组
   */
  private static Object convertArray(final Object array,
      final Class<?> targetType) {
    final Object[] values = (Object[]) array;
    final Object convertedValuesArray = Array
        .newInstance(targetType.getComponentType(), values.length);
    for (int i = 0; i < values.length; i++) {
      Array.set(convertedValuesArray, i,
          convertValue((String) values[i], targetType.getComponentType()));
    }
    return convertedValuesArray;
  }

  /**
   * 获取可填充的字段列表。
   *
   * @param type
   *     类型
   * @param obj
   *     对象实例
   * @return 可填充的字段列表
   */
  public static List<Field> getPopulatableFields(final Class<?> type,
      final Object obj) {
    //    // retrieve declared and inherited fields
    //    List<Field> fields = getDeclaredFields(obj);
    //    // we can not use type here, because with classpath scanning enabled the
    //    // result can be a subtype
    //    fields.addAll(getInheritedFields(obj.getClass()));
    final List<Field> fields = FieldUtils
        .getAllFields(obj.getClass(), Option.ALL);
    // remove static final fields
    fields.removeIf(field -> isStatic(field) && isFinal(field));
    // inner classes (and static nested classes) have a field named "this$0",
    // "this$1", etc, that references the enclosing classes. These fields should
    // be excluded
    if (type.getEnclosingClass() != null) {
      fields.removeIf(field -> field.getName().matches("this\\$[0-9]+"));
    }
    // remove all fields declared from the JDK collections
    fields.removeIf(field -> isJdkBuiltIn(field.getDeclaringClass()));
    return fields;
  }

  /**
   * 测试指定对象的字段是否具有默认值。
   *
   * @param obj
   *     对象
   * @param field
   *     对象的字段
   * @return 如果对象的字段具有默认值（即对象的null值和原始类型的零值）
   *     则返回{@code true}；否则返回{@code false}
   * @throws IllegalAccessException
   *     如果发生任何反射错误
   */
  public static boolean fieldHasDefaultValue(final Object obj,
      final Field field)
      throws IllegalAccessException {
    final Object value = getFieldValue(obj, field);
    if (value == null) {
      return true;
    }
    final Class<?> type = field.getType();
    return type.isPrimitive() && isPrimitiveDefaultValue(type, value);
  }

  /**
   * 获取集合的元素类型。
   *
   * @param type
   *     实现{@code Collection}的集合类的泛型类型
   * @return 集合的元素类，如果无法解析则返回{@code null}
   */
  @Nullable
  public static Class<?> getCollectionElementType(final Type type) {
    if (isParameterizedType(type)) {
      final ParameterizedType parameterizedType = (ParameterizedType) type;
      final Type result = parameterizedType.getActualTypeArguments()[0];
      if (result instanceof Class) {
        return (Class<?>) result;
      } else {
        return null;
      }
    } else if (type instanceof Class) {
      final Class<?> result = resolveRawArgument(Collection.class, (Class<?>) type);
      return (result == TypeResolver.Unknown.class ? null : result);
    } else {
      return null;
    }
  }

  /**
   * 获取映射的键/值类型。
   *
   * @param type
   *     实现{@code Map}的映射类的泛型类型
   * @return 映射的键/值类对，如果无法解析则返回{@code null}
   */
  @Nullable
  public static Pair<Class<?>, Class<?>> getMapElementType(final Type type) {
    if (isParameterizedType(type)) {
      // populate only parameterized types, raw types will be empty
      final ParameterizedType parameterizedType = (ParameterizedType) type;
      final Type keyType = parameterizedType.getActualTypeArguments()[0];
      final Type valueType = parameterizedType.getActualTypeArguments()[1];
      if (keyType instanceof Class && valueType instanceof Class) {
        return new Pair<>((Class<?>) keyType, (Class<?>) valueType);
      } else {
        return null;
      }
    } else if (type instanceof Class) {
      final Class<?>[] args = resolveRawArguments(Map.class, (Class<?>) type);
      if (args.length != 2) {
        return null;
      } else if (args[0] == TypeResolver.Unknown.class
          || args[1] == TypeResolver.Unknown.class) {
        return null;
      } else {
        return new Pair<>(args[0], args[1]);
      }
    } else {
      return null;
    }
  }

  /**
   * 创建一个空集合。
   *
   * @param type
   *     集合的类
   * @param size
   *     集合的期望大小，这将是创建的集合的初始容量
   * @return 空集合对象
   */
  public static <T> Collection<T> createEmptyCollection(final Class<?> type,
      final int size) {
    if (isInterface(type)) {
      return getEmptyImplementationForCollectionInterface(type);
    } else {
      return getEmptyCollectionForType(type, size);
    }
  }

  /**
   * 创建一个空映射。
   *
   * @param objectFactory
   *     对象工厂
   * @param context
   *     随机化上下文
   * @param type
   *     映射的类
   * @param genericType
   *     映射的泛型类型
   * @return 指定的空映射
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Map<Object, Object> createEmptyMap(
      final ObjectFactory objectFactory,
      final Context context, final Class<?> type, final Type genericType) {
    if (isInterface(type)) {
      return (Map<Object, Object>) getEmptyImplementationForMapInterface(type);
    } else {
      try {
        return (Map<Object, Object>) type.getDeclaredConstructor()
                                         .newInstance();
      } catch (final InstantiationException
          | IllegalAccessException
          | NoSuchMethodException
          | InvocationTargetException e) {
        // Creating EnumMap with objenesis by-passes the constructor with keyType
        // which leads to CCE at insertion time
        if (type.isAssignableFrom(EnumMap.class)) {
          if (isParameterizedType(genericType)) {
            final Type enumType = ((ParameterizedType) genericType)
                .getActualTypeArguments()[0];
            return new EnumMap((Class<?>) enumType);
          } else {
            return null;
          }
        } else {
          return (Map<Object, Object>) objectFactory
              .createInstance(type, context);
        }
      }
    }
  }
}
