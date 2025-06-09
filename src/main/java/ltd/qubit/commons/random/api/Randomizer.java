////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.api;

/**
 * 自定义随机化器接口。
 *
 * @param <T>
 *     随机化器生成的类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface Randomizer<T> {

  /**
   * 为给定类型生成一个随机值。
   *
   * @return 给定类型的随机值。
   */
  T getRandomValue();

}
