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
 * 可以缩放生成值的随机化器。
 *
 * @param <T>
 *     生成值的类型。
 * @author 胡海星
 */
public abstract class ScalableRandomizer<T> implements ContextAwareRandomizer<T> {

  protected Integer scale;
  protected RoundingMode roundingMode = RoundingMode.HALF_UP;

  /**
   * 获取小数位数。
   *
   * @return 小数位数
   */
  public Integer getScale() {
    return scale;
  }

  /**
   * 设置小数位数。
   *
   * @param scale
   *         要设置的小数位数
   */
  public void setScale(final Integer scale) {
    this.scale = scale;
  }

  /**
   * 获取舍入模式。
   *
   * @return 舍入模式
   */
  public RoundingMode getRoundingMode() {
    return roundingMode;
  }

  /**
   * 设置舍入模式。
   *
   * @param roundingMode
   *         要设置的舍入模式
   */
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
