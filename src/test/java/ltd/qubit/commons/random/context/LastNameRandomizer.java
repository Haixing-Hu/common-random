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
 * A last name randomizer that depends on the first name of the currently
 * randomized object. The currently randomized object can be retrieved from the
 * randomization context.
 */
public class LastNameRandomizer implements ContextAwareRandomizer<String> {

  private Context context;

  @Override
  public void setContext(final Context context) {
    this.context = context;
  }

  @Override
  public String getRandomValue() {
    String firstName = null;
    if (context.getCurrentObject() instanceof Person) {
      final Person randomizedObject = (Person) context.getCurrentObject();
      firstName = randomizedObject.getFirstName();
    }
    if (context.getCurrentObject() instanceof Pet) {
      final Pet randomizedObject = (Pet) context.getCurrentObject();
      firstName = randomizedObject.getFirstName();
    }
    if (firstName != null && firstName.equalsIgnoreCase("james")) {
      return "bond";
    }
    if (firstName != null && firstName.equalsIgnoreCase("daniel")) {
      return "craig";
    }
    return null;
  }
}
