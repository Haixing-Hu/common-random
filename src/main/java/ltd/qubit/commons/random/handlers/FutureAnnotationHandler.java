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

import jakarta.validation.constraints.Future;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * {@link Future} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class FutureAnnotationHandler implements AnnotationHandler {

  private final long seed;
  private EasyRandom random;

  /**
   * 构造一个 {@link FutureAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public FutureAnnotationHandler(final long seed) {
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
              .dateRange(now.plus(1, ChronoUnit.DAYS),
                      now.plusYears(Parameters.DEFAULT_DATE_RANGE));
      random = new EasyRandom(parameters);
    }
    return () -> random.nextObject(field.getType());
  }
}
