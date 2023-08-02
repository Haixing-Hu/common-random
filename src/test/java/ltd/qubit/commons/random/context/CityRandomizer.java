////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;

/**
 * A city randomizer that depends on the country of the currently randomized
 * object. The currently randomized object can be retrieved from the
 * randomization context.
 */
public class CityRandomizer implements ContextAwareRandomizer<City> {

  private Context context;

  @Override
  public void setContext(final Context context) {
    this.context = context;
  }

  @Override
  public City getRandomValue() {
    final Person person = (Person) context.getRootObject();
    final Country country = person.getCountry();
    if (country == null) {
      return null;
    }
    final String countryName = country.getName();
    if (countryName != null && countryName.equalsIgnoreCase("france")) {
      return new City("paris");
    }
    if (countryName != null && countryName.equalsIgnoreCase("germany")) {
      return new City("berlin");
    }
    if (countryName != null && countryName.equalsIgnoreCase("belgium")) {
      return new City("brussels");
    }
    return null;
  }
}
