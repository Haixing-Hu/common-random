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
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.misc.NullRandomizer;

import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * {@link Nullable} 注解的注解处理器。
 *
 * @author 胡海星
 */
public class NullableAnnotationHandler implements AnnotationHandler {

  /**
   * 默认的空值百分比。
   */
  public static final int DEFAULT_NULL_PERCENT = 50;

  /**
   * 百分之百的百分比值。
   */
  public static final int FULL_PERCENT = 100;

  /**
   * 默认的空值比例。
   */
  public static final double DEFAULT_NULL_RATIO = DEFAULT_NULL_PERCENT / 100.0;

  private static final long INTEGER_RANGE = (long) Integer.MAX_VALUE - (long) Integer.MIN_VALUE;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Random random;
  private final int nullPercent;

  /**
   * 构造一个 {@link NullableAnnotationHandler}，使用默认的空值百分比。
   */
  public NullableAnnotationHandler() {
    this(System.currentTimeMillis(), DEFAULT_NULL_PERCENT);
  }

  /**
   * 构造一个 {@link NullableAnnotationHandler}，使用指定的种子和默认的空值百分比。
   *
   * @param seed
   *     用于生成随机数的种子。
   */
  public NullableAnnotationHandler(final long seed) {
    this(seed, DEFAULT_NULL_PERCENT);
  }

  /**
   * 构造一个 {@link NullableAnnotationHandler}。
   *
   * @param seed
   *     用于生成随机数的种子。
   * @param nullPercent
   *     生成空值的百分比，范围从0到100。
   */
  public NullableAnnotationHandler(final long seed, final int nullPercent) {
    this.random = new Random(seed);
    if (nullPercent < 0 || nullPercent > FULL_PERCENT) {
      throw new IllegalArgumentException(
          "The nullPercent argument must between 0 and 100.");
    }
    this.nullPercent = nullPercent;
  }

  /**
   * 判断是否应该生成空值。
   *
   * @return 如果应该生成空值，则返回 {@code true}；否则返回 {@code false}。
   */
  private boolean shouldGenerateNull() {
    final int v = random.nextInt(FULL_PERCENT);
    return (v < nullPercent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    if (isAnnotationPresent(field, Nullable.class)) {
      final Parameters parameters = context.getParameters();
      final Set<Field> nonNullFields = parameters.getNonNullFields();
      if (nonNullFields.contains(field)) {
        logger.debug("The @Nullable annotated field {}.{} is in the non-null field "
            + "list, and should generate non-null value.",
            field.getDeclaringClass().getSimpleName(), field.getName());
        return null;
      } else if (shouldGenerateNull()) {
        logger.debug("Generate null value for the @Nullable annotated field {}.{}.",
            field.getDeclaringClass().getSimpleName(), field.getName());
        return new NullRandomizer(); // the field will be set to null
      } else {
        logger.debug("Generate non-null value for the @Nullable annotated field {}.{}.",
            field.getDeclaringClass().getSimpleName(), field.getName());
        return null;
      }
    }
    return null;
  }
}
