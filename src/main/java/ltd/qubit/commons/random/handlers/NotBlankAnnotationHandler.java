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

import javax.validation.constraints.NotBlank;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.text.StringRandomizer;

/**
 * The annotation handler for the {@link NotBlank} annotation.
 *
 * @author Haixing Hu
 */
public class NotBlankAnnotationHandler implements AnnotationHandler {

  private final Random random;

  public NotBlankAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new StringRandomizer(random.nextLong());
  }
}
