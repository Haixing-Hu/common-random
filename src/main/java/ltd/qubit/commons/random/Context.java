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
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.random.api.ExclusionPolicy;

import static java.util.stream.Collectors.toList;

import static ltd.qubit.commons.random.util.ReflectionUtils.fieldHasDefaultValue;
import static ltd.qubit.commons.reflect.FieldUtils.isFinal;
import static ltd.qubit.commons.reflect.FieldUtils.isTransient;

/**
 * 对{@link EasyRandom#nextObject(Class)}的单次调用的上下文对象。
 * 它包含一个映射，充当已填充Bean的缓存，以避免无限递归。
 *
 * @author 胡海星
 */
public class Context {

  private final Parameters parameters;

  private final Map<Class<?>, List<Object>> populatedBeans;

  private final Stack<ContextStackItem> stack;

  private final Class<?> type;

  private Object rootObject;

  /**
   * 创建一个新的 {@link Context}。
   *
   * @param type
   *     要创建的对象的类型。
   * @param parameters
   *     EasyRandom 参数。
   */
  public Context(final Class<?> type, final Parameters parameters) {
    this.type = type;
    populatedBeans = new IdentityHashMap<>();
    stack = new Stack<>();
    this.parameters = parameters;
  }

  void addPopulatedBean(final Class<?> type, final Object object) {
    final int objectPoolSize = parameters.getObjectPoolSize();
    List<Object> objects = populatedBeans.get(type);
    if (objects == null) {
      objects = new ArrayList<>();
    }
    if (objects.size() < objectPoolSize) {
      objects.add(object);
    }
    populatedBeans.put(type, objects);
  }

  Object getPopulatedBean(final Class<?> type) {
    final List<Object> beans = populatedBeans.get(type);
    final RandomEx random = new RandomEx();
    return random.choose(beans);
  }

  boolean hasAlreadyRandomizedType(final Class<?> type) {
    return populatedBeans.containsKey(type)
            && populatedBeans.get(type).size() == parameters.getObjectPoolSize();
  }

  void pushStackItem(final ContextStackItem field) {
    stack.push(field);
  }

  ContextStackItem popStackItem() {
    return stack.pop();
  }

  ContextStackItem peekStackItem() {
    return stack.peek();
  }

  String getFieldFullName(final Field field) {
    final List<String> pathToField = getStackedFieldNames();
    pathToField.add(field.getName());
    return String.join(".", toLowerCase(pathToField));
  }

  boolean hasExceededRandomizationDepth() {
    final int currentRandomizationDepth = stack.size();
    return currentRandomizationDepth > parameters.getRandomizationDepth();
  }

  private List<String> getStackedFieldNames() {
    return stack.stream().map(i -> i.getField().getName()).collect(toList());
  }

  private List<String> toLowerCase(final List<String> strings) {
    return strings.stream().map(String::toLowerCase).collect(toList());
  }

  void setRandomizedObject(final Object randomizedObject) {
    if (this.rootObject == null) {
      this.rootObject = randomizedObject;
    }
  }

  /**
   * 获取此上下文中正在填充的目标对象的类型。
   *
   * @return 目标对象的类型。
   */
  public Class<?> getTargetType() {
    return type;
  }

  /**
   * 获取当前作用域中的对象实例。
   *
   * @return 当前作用域中的对象实例。
   */
  public Object getCurrentObject() {
    if (stack.empty()) {
      return rootObject;
    } else {
      return stack.peek().getObject();
    }
  }

  /**
   * 获取当前正在填充的字段。
   *
   * @return 当前正在填充的字段。
   */
  public Field getCurrentField() {
    if (stack.empty()) {
      return null;
    } else {
      return stack.peek().getField();
    }
  }

  /**
   * 获取当前字段的路径。
   *
   * @return 当前字段的路径。
   */
  public String getCurrentFieldPath() {
    return String.join(".", getStackedFieldNames());
  }

  /**
   * 获取当前随机化深度。
   *
   * @return 当前随机化深度。
   */
  public int getCurrentRandomizationDepth() {
    return stack.size();
  }

  /**
   * 获取根对象。
   *
   * @return 根对象。
   */
  public Object getRootObject() {
    return this.rootObject;
  }

  /**
   * 获取Easy Random参数。
   *
   * @return Easy Random参数。
   */
  public Parameters getParameters() {
    return parameters;
  }

  /**
   * 检查是否应填充字段。
   *
   * @param obj
   *     要检查的字段所在的对象。
   * @param field
   *     要检查的字段。
   * @return 如果应填充字段，则为true，否则为false。
   * @throws IllegalAccessException
   *     如果字段不可访问。
   */
  public boolean shouldFieldBePopulated(final Object obj, final Field field)
          throws IllegalAccessException {
    final ExclusionPolicy exclusionPolicy = parameters.getExclusionPolicy();
    if (exclusionPolicy != null
            && exclusionPolicy.shouldBeExcluded(field, this)) {
      return false;
    }
    if (parameters.isExcludeTransient() && isTransient(field)) {
      return false;
    }
    if (!parameters.isOverrideDefaultInitialization()
            && !fieldHasDefaultValue(obj, field)) {
      return false;
    }
    if (isFinal(field)) {
      if (!parameters.isOverrideFinal()) {
        return false;
      }
      return parameters.isOverrideFinalDefaultInitialization()
          || fieldHasDefaultValue(obj, field);
    }
    return true;
  }
}
