////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成一个随机的{@link Character}。
 *
 * @author 胡海星
 */
public class CharacterRandomizer extends AbstractRandomizer<Character> {

  /**
   * 创建一个新的{@link CharacterRandomizer}。
   */
  public CharacterRandomizer() {
    super();
  }

  /**
   * 创建一个新的{@link CharacterRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public CharacterRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 生成一个随机的字符。
   *
   * @return 一个随机的字符。
   */
  @Override
  public Character getRandomValue() {
    return random.nextLetterChar();
  }
}
