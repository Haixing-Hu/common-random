////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;
import java.util.Random;

import jakarta.validation.constraints.NotBlank;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.text.StringRandomizer;

/**
 * {@link NotBlank} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class NotBlankAnnotationHandler implements AnnotationHandler {

  private final Random random;

  /**
   * 构造一个 {@link NotBlankAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public NotBlankAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new StringRandomizer(random.nextLong());
  }
}
