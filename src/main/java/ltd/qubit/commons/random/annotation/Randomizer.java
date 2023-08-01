////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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
 * Annotation to mark a field to be populated with a random value using the
 * given {@code Randomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Randomizer {

  /**
   * The {@code Randomizer} to use to generate the random value for this field.
   *
   * @return the randomizer's class
   */
  Class<? extends ltd.qubit.commons.random.api.Randomizer<?>> value();

  @RandomizerArgument RandomizerArgument[] args() default {};
}
