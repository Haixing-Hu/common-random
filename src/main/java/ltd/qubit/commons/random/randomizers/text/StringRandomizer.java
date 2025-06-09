////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.text;

import java.nio.charset.Charset;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.random.Parameters.DEFAULT_STRING_LENGTH_RANGE;

/**
 * 生成一个随机的{@link String}。
 *
 * @author 胡海星
 */
public class StringRandomizer extends AbstractContextAwareRandomizer<String> {

  private CloseRange<Integer> lengthRange = DEFAULT_STRING_LENGTH_RANGE;

  /**
   * 创建一个新的{@link StringRandomizer}。
   */
  public StringRandomizer() {
    super();
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param maxLength
   *         要生成的字符串的最大长度
   */
  public StringRandomizer(final int maxLength) {
    super();
    lengthRange = new CloseRange<>(DEFAULT_STRING_LENGTH_RANGE.getMin(), maxLength);
    this.contextAware = false;
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public StringRandomizer(final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param charset
   *         要使用的字符集
   * @param seed
   *         初始种子
   */
  public StringRandomizer(final Charset charset, final long seed) {
    super(seed);
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param maxLength
   *         要生成的字符串的最大长度
   * @param seed
   *         初始种子
   */
  public StringRandomizer(final int maxLength, final long seed) {
    super(seed);
    lengthRange = new CloseRange<>(DEFAULT_STRING_LENGTH_RANGE.getMin(), maxLength);
    this.contextAware = false;
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param maxLength
   *         要生成的字符串的最大长度
   * @param minLength
   *         要生成的字符串的最小长度
   * @param seed
   *         初始种子
   */
  public StringRandomizer(final int minLength, final int maxLength, final long seed) {
    super(seed);
    if (minLength > maxLength) {
      throw new IllegalArgumentException("minLength should be less than "
          + "or equal to maxLength");
    }
    lengthRange = new CloseRange<>(minLength, maxLength);
    this.contextAware = false;
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param lengthRange
   *         要生成的字符串的长度范围。
   * @param seed
   *         初始种子
   */
  public StringRandomizer(final CloseRange<Integer> lengthRange, final long seed) {
    super(seed);
    if (lengthRange == null) {
      throw new NullPointerException("length range cannot be null");
    }
    if (lengthRange.getMin() > lengthRange.getMax()) {
      throw new IllegalArgumentException("minLength should be less than "
              + "or equal to maxLength");
    }
    this.lengthRange = lengthRange;
    this.contextAware = false;
  }

  /**
   * 创建一个新的{@link StringRandomizer}。
   *
   * @param parameters
   *         随机化参数。
   */
  public StringRandomizer(final Parameters parameters) {
    super(parameters.getSeed());
    this.lengthRange = parameters.getStringLengthRange();
  }

  @Override
  public void setParameters(final Parameters parameters) {
    this.lengthRange = parameters.getStringLengthRange();
  }

  /**
   * 生成一个随机的字符串。
   *
   * @return
   *       一个随机的字符串
   */
  @Override
  public String getRandomValue() {
    return random.nextLetterString(lengthRange);
  }
}
