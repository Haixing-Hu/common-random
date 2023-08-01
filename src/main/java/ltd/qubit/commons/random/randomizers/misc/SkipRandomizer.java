////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A randomizer used to skip fields from being populated. This is an
 * implementation of the Null Object Pattern
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SkipRandomizer implements Randomizer<Object> {

  @Override
  public Object getRandomValue() {
    return null;
  }
}
