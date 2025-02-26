/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.lang.reflect.Field;
import java.math.RoundingMode;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;

/**
 * A randomizer which can scale the generated value.
 *
 * @param <T>
 *      the type of the generated value.
 */
public abstract class ScalableRandomizer<T> implements ContextAwareRandomizer<T> {

  protected Integer scale;
  protected RoundingMode roundingMode = RoundingMode.HALF_UP;

  public Integer getScale() {
    return scale;
  }

  public void setScale(final Integer scale) {
    this.scale = scale;
  }

  public RoundingMode getRoundingMode() {
    return roundingMode;
  }

  public void setRoundingMode(final RoundingMode roundingMode) {
    this.roundingMode = roundingMode;
  }

  @Override
  public void setContext(final Context context) {
    final Field field = context.getCurrentField();
    if (field != null) {
      final Scale scaleAnn = getAnnotation(field, Scale.class);
      if (scaleAnn != null) {
        this.scale = scaleAnn.value();
      }
      final Round roundAnn = getAnnotation(field, Round.class);
      if (roundAnn != null) {
        this.roundingMode = roundAnn.value();
      }
      final Money moneyAnn = getAnnotation(field, Money.class);
      if (moneyAnn != null) {
        this.scale = moneyAnn.scale();
        this.roundingMode = moneyAnn.roundingModel();
      }
    }
  }
}
