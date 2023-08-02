////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;
import java.util.Random;

import jakarta.validation.constraints.Positive;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

/**
 * The annotation handler for the {@link Positive} annotation.
 *
 * @author Haixing Hu
 */
public class PositiveAnnotationHandler implements AnnotationHandler {

  private final Random random;

  public PositiveAnnotationHandler(final long seed) {
    random = new Random(seed);
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    return new IntegerRangeRandomizer(1, Integer.MAX_VALUE, random.nextLong());
  }
}
