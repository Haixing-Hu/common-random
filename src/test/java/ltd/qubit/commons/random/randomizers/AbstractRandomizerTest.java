////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import java.util.Locale;

import ltd.qubit.commons.random.api.Randomizer;

public class AbstractRandomizerTest<T> {

  protected static final long SEED = 123L;
  protected static final Locale LOCALE = Locale.FRANCE;

  protected Randomizer<T> randomizer;

}
