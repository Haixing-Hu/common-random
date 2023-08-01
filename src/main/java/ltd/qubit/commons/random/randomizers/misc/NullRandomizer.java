////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates null values.
 *
 * @author Mahmoud Ben Hassine
 */
@Priority(Integer.MAX_VALUE)
public class NullRandomizer implements Randomizer<Void> {

  @Override
  public Void getRandomValue() {
    return null;
  }

}
