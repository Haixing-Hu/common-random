////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

public class RandomBeanGenerator extends EasyRandom {
  private static final long serialVersionUID = -5848950504973855340L;

  public RandomBeanGenerator() {
    super(buildParameters(new Parameters()));
  }

  public RandomBeanGenerator(final Parameters parameters) {
    super(buildParameters(parameters));
  }

  private static Parameters buildParameters(final Parameters parameters) {
    return parameters
        .seed(System.currentTimeMillis())
        .overrideDefaultInitialization(true)
        .scanClasspathForConcreteTypes(true)
        .setExcludeTransient(true);
  }

}
