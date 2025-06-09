////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * 一个生成null值的 {@link Randomizer}。
 *
 * @author 胡海星
 */
@Priority(Integer.MAX_VALUE)
public class NullRandomizer implements Randomizer<Void> {

  @Override
  public Void getRandomValue() {
    return null;
  }

}
