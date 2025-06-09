////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.handlers.AnnotationHandler;
import ltd.qubit.commons.random.handlers.AssertFalseAnnotationHandler;
import ltd.qubit.commons.random.handlers.AssertTrueAnnotationHandler;
import ltd.qubit.commons.random.handlers.DecimalMinMaxAnnotationHandler;
import ltd.qubit.commons.random.handlers.EmailAnnotationHandler;
import ltd.qubit.commons.random.handlers.FutureAnnotationHandler;
import ltd.qubit.commons.random.handlers.FutureOrPresentAnnotationHandler;
import ltd.qubit.commons.random.handlers.MinMaxAnnotationHandler;
import ltd.qubit.commons.random.handlers.NegativeAnnotationHandler;
import ltd.qubit.commons.random.handlers.NegativeOrZeroAnnotationHandler;
import ltd.qubit.commons.random.handlers.NotBlankAnnotationHandler;
import ltd.qubit.commons.random.handlers.NotEmptyAnnotationHandler;
import ltd.qubit.commons.random.handlers.NullAnnotationHandler;
import ltd.qubit.commons.random.handlers.PastAnnotationHandler;
import ltd.qubit.commons.random.handlers.PastOrPresentAnnotationHandler;
import ltd.qubit.commons.random.handlers.PatternAnnotationHandler;
import ltd.qubit.commons.random.handlers.PositiveAnnotationHandler;
import ltd.qubit.commons.random.handlers.PositiveOrZeroAnnotationHandler;
import ltd.qubit.commons.random.handlers.SizeAnnotationHandler;

import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * 一个随机化器注册表，用于支持使用
 * <a href="http://beanvalidation.org/">JSR 349</a> 注解的字段。
 *
 * @author 胡海星
 */
@Priority(-2)
public class BeanValidationRandomizerRegistry implements RandomizerRegistry {

  /**
   * 注解处理器映射表。
   */
  protected Map<Class<? extends Annotation>, AnnotationHandler>
          handlers = new HashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    final long seed = parameters.getSeed();
    final Charset charset = parameters.getCharset();
    handlers.put(AssertFalse.class, new AssertFalseAnnotationHandler());
    handlers.put(AssertTrue.class, new AssertTrueAnnotationHandler());
    handlers.put(Null.class, new NullAnnotationHandler());
    handlers.put(Future.class, new FutureAnnotationHandler(seed));
    handlers.put(FutureOrPresent.class, new FutureOrPresentAnnotationHandler(seed));
    handlers.put(Past.class, new PastAnnotationHandler(seed));
    handlers.put(PastOrPresent.class, new PastOrPresentAnnotationHandler(seed));
    handlers.put(Min.class, new MinMaxAnnotationHandler(seed));
    handlers.put(Max.class, new MinMaxAnnotationHandler(seed));
    handlers.put(DecimalMin.class, new DecimalMinMaxAnnotationHandler(seed));
    handlers.put(DecimalMax.class, new DecimalMinMaxAnnotationHandler(seed));
    handlers.put(Pattern.class, new PatternAnnotationHandler(seed));
    handlers.put(Size.class, new SizeAnnotationHandler(seed, random, parameters));
    handlers.put(Positive.class, new PositiveAnnotationHandler(seed));
    handlers.put(PositiveOrZero.class, new PositiveOrZeroAnnotationHandler(seed));
    handlers.put(Negative.class, new NegativeAnnotationHandler(seed));
    handlers.put(NegativeOrZero.class, new NegativeOrZeroAnnotationHandler(seed));
    handlers.put(NotEmpty.class, new NotEmptyAnnotationHandler(seed, random, parameters));
    handlers.put(NotBlank.class, new NotBlankAnnotationHandler(seed));
    handlers.put(Email.class, new EmailAnnotationHandler(seed));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    for (final Map.Entry<Class<? extends Annotation>, AnnotationHandler> entry
        : handlers.entrySet()) {
      final Class<? extends Annotation> annotation = entry.getKey();
      final AnnotationHandler handler = entry.getValue();
      if (isAnnotationPresent(field, annotation) && handler != null) {
        return handler.getRandomizer(field, context);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Class<?> fieldType, final Context context) {
    return null;
  }
}
