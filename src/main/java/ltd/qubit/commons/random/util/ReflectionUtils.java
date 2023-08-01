////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.ObjectCreationException;
import ltd.qubit.commons.random.annotation.RandomizerArgument;
import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.Option;
import ltd.qubit.commons.reflect.ReflectionException;
import ltd.qubit.commons.util.pair.Pair;

import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jodah.typetools.TypeResolver;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;

import static ltd.qubit.commons.reflect.AccessibleUtils.withAccessibleObject;
import static ltd.qubit.commons.reflect.ClassUtils.isInterface;
import static ltd.qubit.commons.reflect.ClassUtils.isJdkBuiltIn;
import static ltd.qubit.commons.reflect.ClassUtils.isParameterizedType;
import static ltd.qubit.commons.reflect.FieldUtils.isFinal;
import static ltd.qubit.commons.reflect.FieldUtils.isStatic;

import static net.jodah.typetools.TypeResolver.resolveRawArgument;
import static net.jodah.typetools.TypeResolver.resolveRawArguments;

/**
 * Reflection utility methods.
 *
 * <strong>This class is intended for internal use only. All public methods
 * (except {@link ReflectionUtils#asRandomizer(java.util.function.Supplier)}
 * might change between minor versions without notice.</strong>
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
public final class ReflectionUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

  private ReflectionUtils() {}

  /**
   * Create a dynamic proxy that adapts the given {@link Supplier} to a {@link
   * Randomizer}.
   *
   * @param supplier
   *     to adapt
   * @param <T>
   *     target type
   * @return the proxy randomizer
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
   * Get declared fields of a given type.
   *
   * @param type
   *     the type to introspect
   * @param <T>
   *     the actual type to introspect
   * @return list of declared fields
   */
  public static <T> List<Field> getDeclaredFields(final T type) {
    return new ArrayList<>(asList(type.getClass().getDeclaredFields()));
  }

  /**
   * Get inherited fields of a given type.
   *
   * @param type
   *     the type to introspect
   * @return list of inherited fields
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
   * Set a value in a field of a target object. If the target object provides a
   * setter for the field, this setter will be used. Otherwise, the field will
   * be set using reflection.
   *
   * @param object
   *     instance to set the property on
   * @param field
   *     field to set the property on
   * @param value
   *     value to set
   * @throws IllegalAccessException
   *     if the property cannot be set
   * @throws InvocationTargetException
   *     if the property cannot be set
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
   * Set a value (accessible or not accessible) in a field of a target object.
   *
   * @param object
   *     instance to set the property on
   * @param field
   *     field to set the property on
   * @param value
   *     value to set
   * @throws ReflectionException
   *     if the property cannot be set
   */
  public static void setFieldValue(final Object object, final Field field,
      final Object value) throws ReflectionException {
    withAccessibleObject(field, f -> f.set(object, value));
  }

  /**
   * Get the value (accessible or not accessible) of a field of a target
   * object.
   *
   * @param object
   *     instance to get the field of
   * @param field
   *     field to get the value of
   * @return the value of the field
   * @throws ReflectionException
   *     if field can not be accessed
   */
  public static Object getFieldValue(final Object object, final Field field)
      throws ReflectionException {
    return withAccessibleObject(field, f -> f.get(object), true);
  }

  /**
   * Get wrapper type of a primitive type.
   *
   * @param primitiveType
   *     to get its wrapper type
   * @return the wrapper type of the given primitive type
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
   * Check if a field has a primitive type and matching default value which is
   * set by the compiler.
   *
   * @param object
   *     instance to get the field value of
   * @param field
   *     field to check
   * @return true if the field is primitive and is set to the default value,
   *     false otherwise
   * @throws IllegalAccessException
   *     if field cannot be accessed
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

  private static boolean isPrimitiveDefaultValue(final Class<?> type,
      final Object value) {
    if (value == null) {
      return false;
    }
    if ((type == boolean.class) && ((boolean) value == false)) {
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
   * Searches the classpath for all public concrete subtypes of the given
   * interface or abstract class.
   *
   * @param type
   *     to search concrete subtypes of
   * @param <T>
   *     the actual type to introspect
   * @return a list of all concrete subtypes found
   */
  public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
    return ClassGraphFacade.getPublicConcreteSubTypesOf(type);
  }

  /**
   * Filters a list of types to keep only elements having the same parameterized
   * types as the given type.
   *
   * @param type
   *     the type to use for the search
   * @param types
   *     a list of types to filter
   * @return a list of types having the same parameterized types as the given
   *     type
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
   * Return an empty implementation for a {@link Collection} type.
   *
   * @param collectionInterface
   *     for which an empty implementation should be returned
   * @return empty implementation for the collection interface
   */
  public static Collection<?> getEmptyImplementationForCollectionInterface(
      final Class<?> collectionInterface) {
    Collection<?> collection = new ArrayList<>();
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
   * Create an empty collection for the given type.
   *
   * @param fieldType
   *     for which an empty collection should we created
   * @param initialSize
   *     initial size of the collection
   * @return empty collection
   */
  public static Collection<?> getEmptyCollectionForType(
      final Class<?> fieldType,
      final int initialSize) {
    rejectUnsupportedTypes(fieldType);
    Collection<?> collection;
    try {
      collection = (Collection<?>) fieldType.getDeclaredConstructor()
                                            .newInstance();
    } catch (final InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      if (fieldType.equals(ArrayBlockingQueue.class)) {
        collection = new ArrayBlockingQueue<>(initialSize);
      } else {
        collection = (Collection<?>) new ObjenesisStd().newInstance(fieldType);
      }
    }
    return collection;
  }

  /**
   * Return an empty implementation for the given {@link Map} interface.
   *
   * @param mapInterface
   *     for which an empty implementation should be returned
   * @return empty implementation for the given {@link Map} interface.
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
   * Get the read method for given field.
   *
   * @param field
   *     field to get the read method for.
   * @return Optional of read method or empty if field has no read method
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

  private static Optional<Method> getPublicMethod(final String name,
      final Class<?> target) {
    try {
      return Optional.of(target.getMethod(name));
    } catch (final NoSuchMethodException | SecurityException e) {
      return Optional.empty();
    }
  }

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

  private static boolean notEmpty(
      final RandomizerArgument[] randomizerArguments) {
    return randomizerArguments != null && randomizerArguments.length > 0;
  }

  private static boolean hasSameArgumentNumber(final Constructor<?> constructor,
      final RandomizerArgument[] randomizerArguments) {
    return constructor.getParameterCount() == randomizerArguments.length;
  }

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
    // inner classes (and static nested classes) have a field named "this$0"
    // that references the enclosing class.
    // This field should be excluded
    if (type.getEnclosingClass() != null) {
      fields.removeIf(field -> field.getName().equals("this$0"));
    }
    // remove all fields declared from the JDK collections
    fields.removeIf(field -> isJdkBuiltIn(field.getDeclaringClass()));
    return fields;
  }

  /**
   * Tests whether a field of a specified object has a default value.
   *
   * @param obj
   *     the object.
   * @param field
   *     the field of the object.
   * @return {@code true} if the field of the object has a default value, i.e.,
   *     null value for objects and zero value for primitive types; returns
   *     {@code false} otherwise;
   * @throws IllegalAccessException
   *     if any reflection error occurs.
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
   * Gets the element type of a collection.
   *
   * @param type
   *     the generic type of a collection class, which implements {@code
   *     Collection}.
   * @return the element class of the collection, or {@code null} if it cannot
   *     be resolved.
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
      final Class<?> result = resolveRawArgument(Collection.class,
          (Class<?>) type);
      return (result == TypeResolver.Unknown.class ? null : result);
    } else {
      return null;
    }
  }

  /**
   * Gets the key/value type of a map.
   *
   * @param type
   *     the generic type of a map class, which implements {@code Map}.
   * @return the pair of key/value class of the map, or {@code null} if it
   *     cannot be resolved.
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
   * Create an empty collection.
   *
   * @param type
   *     the class of the collection.
   * @param size
   *     the expected size of the collection, which will be the initial capacity
   *     of the created collection.
   * @return an empty collection object.
   */
  public static Collection<?> createEmptyCollection(final Class<?> type,
      final int size) {
    if (isInterface(type)) {
      return getEmptyImplementationForCollectionInterface(type);
    } else {
      return getEmptyCollectionForType(type, size);
    }
  }

  /**
   * Create an empty map.
   *
   * @param objectFactory
   *     the object factory.
   * @param context
   *     the randomization context.
   * @param type
   *     the class of the map.
   * @param genericType
   *     the generic type of the map.
   * @return the specified empty map.
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
