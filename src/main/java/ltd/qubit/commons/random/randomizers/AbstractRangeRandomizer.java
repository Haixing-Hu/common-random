////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import ltd.qubit.commons.util.range.CloseRange;

/**
 * Abstract class for range randomizers.
 *
 * @param <T>
 *     the type of objects in the defined range.
 * @author Rémi Alvergnat, Haixing Hu
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

  public void setRange(final CloseRange<T> range) {
    final T theMin = (range.getMin() != null ? range.getMin() : getDefaultMinValue());
    final T theMax = (range.getMax() != null ? range.getMax() : getDefaultMaxValue());
    this.range = new CloseRange<>(theMin, theMax);
    this.contextAware = false;
    checkValues();
  }

  public void setRange(final T min, final T max) {
    final T theMin = (min != null ? min : getDefaultMinValue());
    final T theMax = (max != null ? max : getDefaultMaxValue());
    this.range = new CloseRange<>(theMin, theMax);
    checkValues();
  }

  protected abstract void checkValues();

  protected abstract T getDefaultMinValue();

  protected abstract T getDefaultMaxValue();

}
