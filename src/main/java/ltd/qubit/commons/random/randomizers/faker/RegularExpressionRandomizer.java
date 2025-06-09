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
 * 生成匹配正则表达式的随机字符串的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class RegularExpressionRandomizer extends FakerBasedRandomizer<String> {

  private final String regularExpression;

  /**
   * 创建一个新的{@link RegularExpressionRandomizer}。
   *
   * @param regularExpression
   *         此随机化器生成的字符串将匹配的正则表达式
   */
  public RegularExpressionRandomizer(final String regularExpression) {
    super();
    this.regularExpression = regularExpression;
  }

  /**
   * 创建一个新的{@link RegularExpressionRandomizer}。
   *
   * @param regularExpression
   *         此随机化器生成的字符串将匹配的正则表达式
   * @param seed
   *         初始种子
   */
  public RegularExpressionRandomizer(final String regularExpression,
          final long seed) {
    super(seed);
    this.regularExpression = regularExpression;
  }

  /**
   * 生成一个匹配正则表达式的随机字符串。
   *
   * @return 一个匹配正则表达式的随机字符串
   */
  @Override
  public String getRandomValue() {
    return faker.regexify(removeLeadingAndTailingBoundaryMatchers(regularExpression));
  }

  /**
   * 移除正则表达式开头和结尾的边界匹配符。
   *
   * @param regularExpression
   *         要处理的正则表达式
   * @return 移除边界匹配符后的正则表达式
   */
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
