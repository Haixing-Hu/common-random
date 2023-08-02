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
import java.math.BigDecimal;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.BigDecimalRandomizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * The annotation handler for the {@link Money} annotation.
 *
 * @author Haixing Hu
 */
public class MoneyAnnotationHandler implements AnnotationHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final long seed;

  public MoneyAnnotationHandler(final long seed) {
    this.seed = seed;
  }

  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (isAnnotationPresent(field, Money.class)) {
      if (field.getType() == BigDecimal.class) {
        final Money money = getAnnotation(field, Money.class);
        return new BigDecimalRandomizer(seed, money.scale(), money.roundingModel());
      } else {
        logger.warn("@Money annotation can only be used on java.math.BigDecimal fields.");
      }
    }
    return null;
  }
}
