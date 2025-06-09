////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.annotation;

/**
 * {@link Randomizer}的参数。
 *
 * @author 胡海星
 */
public @interface RandomizerArgument {

  /**
   * 参数的值。
   *
   * @return 参数的值
   */
  String value() default "";

  /**
   * 参数的类型。
   *
   * @return 参数的类型
   */
  Class<?> type() default Object.class;
}
