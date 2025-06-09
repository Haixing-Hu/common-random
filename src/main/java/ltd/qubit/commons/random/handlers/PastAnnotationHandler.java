////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.constraints.Past;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * {@link Past} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class PastAnnotationHandler implements AnnotationHandler {

  private final long seed;
  private EasyRandom random;

  /**
   * 构造一个 {@link PastAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public PastAnnotationHandler(final long seed) {
    this.seed = seed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (random == null) {
      final LocalDate now = LocalDate.now();
      final Parameters parameters = new Parameters()
              .seed(seed)
              .dateRange(now.minusYears(Parameters.DEFAULT_DATE_RANGE),
                         now.minus(1, ChronoUnit.DAYS));
      random = new EasyRandom(parameters);
    }
    return () -> random.nextObject(field.getType());
  }
}
