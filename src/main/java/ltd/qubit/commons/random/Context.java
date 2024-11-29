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
 * Context object for a single call on {@link EasyRandom#nextObject(Class)}. It
 * contains a map acting as a cache of populated beans to avoid infinite
 * recursion.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class Context {

  private final Parameters parameters;

  private final Map<Class<?>, List<Object>> populatedBeans;

  private final Stack<ContextStackItem> stack;

  private final Class<?> type;

  private Object rootObject;

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

  public Class<?> getTargetType() {
    return type;
  }

  public Object getCurrentObject() {
    if (stack.empty()) {
      return rootObject;
    } else {
      return stack.peek().getObject();
    }
  }

  public Field getCurrentField() {
    if (stack.empty()) {
      return null;
    } else {
      return stack.peek().getField();
    }
  }

  public String getCurrentFieldPath() {
    return String.join(".", getStackedFieldNames());
  }

  public int getCurrentRandomizationDepth() {
    return stack.size();
  }

  public Object getRootObject() {
    return this.rootObject;
  }

  public Parameters getParameters() {
    return parameters;
  }

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
