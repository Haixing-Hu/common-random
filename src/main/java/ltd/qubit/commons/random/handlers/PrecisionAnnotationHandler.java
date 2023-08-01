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
import java.time.Instant;
import java.util.Date;

import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.range.DateRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.InstantRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.SqlDateRangeRandomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * The annotation handler for the {@link Precision} annotation.
 *
 * @author Haixing Hu
 */
public class PrecisionAnnotationHandler implements AnnotationHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final long seed;
  private final Parameters parameters;

  public PrecisionAnnotationHandler(final long seed, final Parameters parameters) {
    this.seed = seed;
    this.parameters = parameters;
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (isAnnotationPresent(field, Precision.class)) {
      final Precision precision = getAnnotation(field, Precision.class);
      if (field.getType() == Instant.class) {
        return new InstantRangeRandomizer(parameters, precision.value());
      } else if (field.getType() == Date.class) {
        return new DateRangeRandomizer(parameters, precision.value());
      } else if (field.getType() == java.sql.Date.class) {
        return new SqlDateRangeRandomizer(parameters, precision.value());
      } else {
        logger.warn("@Precision annotation can only be used on "
            + "java.util.Date, java.sql.Date, java.time.Instant fields.");
      }
    }
    return null;
  }
}
