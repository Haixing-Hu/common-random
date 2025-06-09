////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import ltd.qubit.commons.util.range.CloseRange;

/**
 * 范围随机化器的抽象类。
 *
 * @param <T>
 *     定义范围内对象的类型。
 * @author Rémi Alvergnat, 胡海星
 */
public abstract class AbstractRangeRandomizer<T> extends
    AbstractContextAwareRandomizer<T> {

  protected CloseRange<T> range;

  protected AbstractRangeRandomizer() {
    super();
  }

  protected AbstractRangeRandomizer(final long seed) {
    super(seed);
  }

  protected AbstractRangeRandomizer(final T min, final T max) {
    super();
    setRange(min, max);
    contextAware = false;
  }

  protected AbstractRangeRandomizer(final CloseRange<T> range) {
    super();
    setRange(range);
    contextAware = false;
  }

  protected AbstractRangeRandomizer(final T min, final T max, final long seed) {
    super(seed);
    setRange(min, max);
    contextAware = false;
  }

  protected AbstractRangeRandomizer(final CloseRange<T> range, final long seed) {
    super(seed);
    setRange(range);
    contextAware = false;
  }

  /**
   * 设置随机化器的范围。
   *
   * @param range
   *         要设置的范围。
   */
  public void setRange(final CloseRange<T> range) {
    final T theMin = (range.getMin() != null ? range.getMin() : getDefaultMinValue());
    final T theMax = (range.getMax() != null ? range.getMax() : getDefaultMaxValue());
    this.range = new CloseRange<>(theMin, theMax);
    this.contextAware = false;
    checkValues();
  }

  /**
   * 设置随机化器的范围。
   *
   * @param min
   *         最小值。
   * @param max
   *         最大值。
   */
  public void setRange(final T min, final T max) {
    final T theMin = (min != null ? min : getDefaultMinValue());
    final T theMax = (max != null ? max : getDefaultMaxValue());
    this.range = new CloseRange<>(theMin, theMax);
    checkValues();
  }

  /**
   * 检查范围值的有效性。
   */
  protected abstract void checkValues();

  /**
   * 获取默认的最小值。
   *
   * @return 默认的最小值。
   */
  protected abstract T getDefaultMinValue();

  /**
   * 获取默认的最大值。
   *
   * @return 默认的最大值。
   */
  protected abstract T getDefaultMaxValue();

}
