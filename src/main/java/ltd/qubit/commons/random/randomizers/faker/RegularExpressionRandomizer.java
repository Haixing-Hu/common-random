////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.faker;

import ltd.qubit.commons.random.api.Randomizer;

/**
 * A {@link Randomizer} that generates random strings matching a regular
 * expression.
 */
public class RegularExpressionRandomizer extends FakerBasedRandomizer<String> {

  private final String regularExpression;

  /**
   * Create a new {@link RegularExpressionRandomizer}.
   *
   * @param regularExpression
   *         the regular expression which strings generated by this randomizer
   *         will match.
   */
  public RegularExpressionRandomizer(final String regularExpression) {
    super();
    this.regularExpression = regularExpression;
  }

  /**
   * Create a new {@link RegularExpressionRandomizer}.
   *
   * @param regularExpression
   *         the regular expression which strings generated by this randomizer
   *         will match.
   * @param seed
   *         the initial seed
   */
  public RegularExpressionRandomizer(final String regularExpression,
          final long seed) {
    super(seed);
    this.regularExpression = regularExpression;
  }

  @Override
  public String getRandomValue() {
    return faker.regexify(removeLeadingAndTailingBoundaryMatchers(regularExpression));
  }

  private static String removeLeadingAndTailingBoundaryMatchers(
          final String regularExpression) {
    String str = regularExpression;
    int lastIndex = str.length() - 1;
    if (str.indexOf('^') == 0) {
      str = str.substring(1, lastIndex + 1);
      lastIndex = str.length() - 1;
    }
    if (str.lastIndexOf('$') == lastIndex) {
      str = str.substring(0, lastIndex);
    }
    return str;
  }
}
