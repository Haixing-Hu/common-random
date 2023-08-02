////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

public class CountryRandomizer extends AbstractRandomizer<Country> {

  private final String[] names;

  public CountryRandomizer(final String... names) {
    this.names = names;
  }

  @Override
  public Country getRandomValue() {
    final String country = names[random.nextInt(names.length)];
    return new Country(country);
  }
}
