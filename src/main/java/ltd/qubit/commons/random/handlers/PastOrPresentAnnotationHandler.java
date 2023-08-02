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
import java.time.LocalDate;

import jakarta.validation.constraints.PastOrPresent;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * The annotation handler for the {@link PastOrPresent} annotation.
 *
 * @author Haixing Hu
 */
public class PastOrPresentAnnotationHandler implements AnnotationHandler {

  private final long seed;
  private EasyRandom random;

  public PastOrPresentAnnotationHandler(final long seed) {
    this.seed = seed;
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (random == null) {
      final LocalDate now = LocalDate.now();
      final Parameters parameters = new Parameters()
              .seed(seed)
              .dateRange(now.minusYears(Parameters.DEFAULT_DATE_RANGE), now);
      random = new EasyRandom(parameters);
    }
    return () -> random.nextObject(field.getType());
  }
}
