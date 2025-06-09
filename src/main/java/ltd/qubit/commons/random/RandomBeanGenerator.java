////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

/**
 * 随机Bean生成器，继承自 {@link EasyRandom}，提供了针对Bean对象的特定配置。
 *
 * @author 胡海星
 */
public class RandomBeanGenerator extends EasyRandom {
  private static final long serialVersionUID = -5848950504973855340L;

  /**
   * 构造一个 {@link RandomBeanGenerator}，使用默认参数。
   */
  public RandomBeanGenerator() {
    super(buildParameters(new Parameters()));
  }

  /**
   * 构造一个 {@link RandomBeanGenerator}，使用指定的参数。
   *
   * @param parameters
   *     指定的参数配置。
   */
  public RandomBeanGenerator(final Parameters parameters) {
    super(buildParameters(parameters));
  }

  /**
   * 构建适用于Bean生成的参数配置。
   *
   * @param parameters
   *     输入的参数配置。
   * @return 构建后的参数配置，包含了Bean生成的特定设置。
   */
  private static Parameters buildParameters(final Parameters parameters) {
    return parameters
        .seed(System.currentTimeMillis())
        .overrideDefaultInitialization(true)
        .scanClasspathForConcreteTypes(true)
        .setExcludeTransient(true);
  }

}
