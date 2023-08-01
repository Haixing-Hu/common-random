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
import java.math.BigDecimal;
import java.math.RoundingMode;

import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.BigDecimalRandomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * The annotation handler for the {@link Scale} annotation.
 *
 * @author Haixing Hu
 */
public class ScaleAnnotationHandler implements AnnotationHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final long seed;

  public ScaleAnnotationHandler(final long seed) {
    this.seed = seed;
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (isAnnotationPresent(field, Scale.class)) {
      if (field.getType() == BigDecimal.class) {
        final int scale = getScale(field);
        final RoundingMode roundingMode = getRoundingMode(field);
        return new BigDecimalRandomizer(seed, scale, roundingMode);
      } else {
        logger.warn("@Scale annotation can only be used on java.math.BigDecimal fields.");
      }
    }
    return null;
  }

  private static int getScale(final Field field) {
    final Scale scale = getAnnotation(field, Scale.class);
    if (scale != null) {
      return scale.value();
    } else {
      return Scale.DEFAULT_SCALE;
    }
  }

  private static RoundingMode getRoundingMode(final Field field) {
    final Round round = getAnnotation(field, Round.class);
    if (round != null) {
      return round.value();
    } else {
      return Round.DEFAULT_ROUNDING_MODE;
    }
  }
}
