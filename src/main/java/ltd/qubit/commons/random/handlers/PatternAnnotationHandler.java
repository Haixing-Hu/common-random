////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;
import java.util.Random;

import javax.validation.constraints.Pattern;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.faker.RegularExpressionRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * The annotation handler for the {@link Pattern} annotation.
 *
 * @author Haixing Hu
 */
public class PatternAnnotationHandler implements AnnotationHandler {

  private final Random random;

  public PatternAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

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
