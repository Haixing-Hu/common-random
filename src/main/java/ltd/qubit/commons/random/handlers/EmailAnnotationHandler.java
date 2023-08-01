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

import javax.validation.constraints.Email;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.faker.EmailRandomizer;

/**
 * The annotation handler for the {@link Email} annotation.
 *
 * @author Haixing Hu
 */
public class EmailAnnotationHandler implements AnnotationHandler {

  private final Random random;

  public EmailAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new EmailRandomizer(random.nextLong());
  }
}
