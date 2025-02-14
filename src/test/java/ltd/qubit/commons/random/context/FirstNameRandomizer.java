////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

public class FirstNameRandomizer extends AbstractRandomizer<String> {

  private final String[] names;

  public FirstNameRandomizer(final String... names) {
    this.names = names;
  }

  @Override
  public String getRandomValue() {
    return names[random.nextInt(names.length)];
  }
}
