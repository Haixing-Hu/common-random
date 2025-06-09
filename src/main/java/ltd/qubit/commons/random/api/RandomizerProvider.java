////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

import java.lang.reflect.Field;
import java.util.Set;

import ltd.qubit.commons.random.Context;

/**
 * 根据当前上下文为字段/类型提供随机化器的策略接口。
 * 实现可以（或不可以）使用注册表来提供随机化器。
 *
 * <p>此接口与简单的 {@link RandomizerRegistry} 相比的附加价值在于，它允许访问当前上下文，
 * 并允许基于该上下文进行细粒度的随机化器选择。
 *
 * @author 胡海星
 */
public interface RandomizerProvider extends Cloneable {

  /**
   * 在当前上下文中返回给定字段的随机化器。
   *
   * @param field
   *     应为其返回随机化器的字段。
   * @param context
   *     当前的随机化上下文。
   * @return 在当前上下文中为给定字段提供的随机化器。
   */
  default Randomizer<?> getByField(Field field, Context context) {
    return null;
  }

  /**
   * 在当前上下文中返回给定类型的随机化器。
   *
   * @param type
   *     应为其返回随机化器的类型。
   * @param context
   *     当前的随机化上下文。
   * @param <T>
   *     泛型类型。
   * @return 在当前上下文中为给定类型提供的随机化器。
   */
  default <T> Randomizer<T> getByType(Class<T> type, Context context) {
    return null;
  }

  /**
   * 添加随机化器注册表。
   *
   * @param registries
   *     要添加的注册表。
   * @return 此对象。
   */
  RandomizerProvider addRegistries(Set<RandomizerRegistry> registries);

  /**
   * 删除指定的随机化器注册表。
   *
   * @param registryClass
   *     要删除的指定注册表的类。
   * @return 此对象。
   */
  RandomizerProvider removeRegistry(Class<? extends RandomizerRegistry> registryClass);

  /**
   * 克隆此随机化器注册表。
   *
   * @return 此对象的克隆副本。
   */
  RandomizerProvider clone();

}
