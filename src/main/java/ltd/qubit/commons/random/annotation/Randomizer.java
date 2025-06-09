////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记字段的注解，该字段将使用给定的{@code Randomizer}
 * 填充随机值。
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Randomizer {

  /**
   * 用于为此字段生成随机值的{@code Randomizer}。
   *
   * @return 随机化器的类
   */
  Class<? extends ltd.qubit.commons.random.api.Randomizer<?>> value();

  /**
   * 随机化器的参数。
   *
   * @return 随机化器的参数。
   */
  @RandomizerArgument RandomizerArgument[] args() default {};
}
