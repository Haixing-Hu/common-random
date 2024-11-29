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
 * Argument of a {@link Randomizer}.
 *
 * @author Dovid Kopel (https://github.com/dovidkopel)
 */
public @interface RandomizerArgument {

  /**
   * The value of the argument.
   *
   * @return value of the argument
   */
  String value() default "";

  /**
   * The type of the argument.
   *
   * @return type of the argument
   */
  Class<?> type() default Object.class;
}
