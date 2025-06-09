////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个用于跳过字段填充的随机化器。这是空对象模式的一个实现。
 *
 * @author 胡海星
 */
public class SkipRandomizer implements Randomizer<Object> {

  @Override
  public Object getRandomValue() {
    return null;
  }
}
