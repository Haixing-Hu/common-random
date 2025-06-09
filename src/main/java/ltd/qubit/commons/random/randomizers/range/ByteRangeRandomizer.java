////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.range;

import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.randomizers.AbstractRangeRandomizer;

import static java.util.Objects.requireNonNull;

/**
 * 在给定范围内生成一个随机的{@link Byte}。
 *
 * @author Rémi Alvergnat, 胡海星
 */
public class ByteRangeRandomizer extends AbstractRangeRandomizer<Byte> {

  /**
   * 创建一个新的{@link ByteRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   */
  public ByteRangeRandomizer(final Byte min, final Byte max) {
    super(min, max);
  }

  /**
   * 创建一个新的{@link ByteRangeRandomizer}。
   *
   * @param min
   *         最小值
   * @param max
   *         最大值
   * @param seed
   *         初始种子
   */
  public ByteRangeRandomizer(final Byte min, final Byte max, final long seed) {
    super(min, max, seed);
  }

  @Override
  protected void checkValues() {
    final Byte min = requireNonNull(range.getMin(), "range.min cannot be null");
    final Byte max = requireNonNull(range.getMax(), "range.max cannot be null");
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
  }

  @Override
  protected Byte getDefaultMaxValue() {
    return Byte.MAX_VALUE;
  }

  @Override
  protected Byte getDefaultMinValue() {
    return Byte.MIN_VALUE;
  }

  @Override
  public void setParameters(final Parameters parameters) {
    //  do nothing
  }

  @Override
  public Byte getRandomValue() {
    return random.nextByte(range);
  }
}
