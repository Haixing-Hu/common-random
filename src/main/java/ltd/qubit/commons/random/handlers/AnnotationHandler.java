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

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * 注解处理器的接口。
 *
 * @author 胡海星
 */
public interface AnnotationHandler {

  /**
   * 为注解字段生成一个随机化器。
   *
   * @param field
   *     要处理的字段。
   * @param context
   *     当前的随机化上下文。
   * @return 为注解字段生成的随机化器，如果此处理器不支持该字段，则返回{@code null}。
   */
  Randomizer<?> getRandomizer(Field field, Context context);

}
