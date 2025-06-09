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

import jakarta.validation.constraints.Pattern;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.faker.RegularExpressionRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * {@link Pattern} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class PatternAnnotationHandler implements AnnotationHandler {

  private final Random random;

  /**
   * 构造一个 {@link PatternAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public PatternAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    final Class<?> fieldType = field.getType();
    final Pattern patternAnnotation = getAnnotation(field, Pattern.class);
    final String regex = patternAnnotation.regexp();
    if (fieldType.equals(String.class)) {
      return new RegularExpressionRandomizer(regex, random.nextLong());
    }
    return null;
  }
}
