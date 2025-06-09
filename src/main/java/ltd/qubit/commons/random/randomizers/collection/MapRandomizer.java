////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.collection;

import java.util.HashMap;
import java.util.Map;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.number.ByteRandomizer;

import static java.lang.Math.abs;

/**
 * 一个 {@link Randomizer}，它生成一个具有随机条目的 {@link Map}。
 *
 * @param <K>
 *     键的类型。
 * @param <V>
 *     值的类型。
 * @author 胡海星
 */
public class MapRandomizer<K, V> implements ContextAwareRandomizer<Map<K, V>> {

  private final int size;
  private final Randomizer<K> keyRandomizer;
  private final Randomizer<V> valueRandomizer;

  /**
   * 创建一个新的 {@link MapRandomizer}，它具有随机数量的条目。
   *
   * @param keyRandomizer
   *     键的随机化器。
   * @param valueRandomizer
   *     值的随机化器。
   */
  public MapRandomizer(final Randomizer<K> keyRandomizer,
      final Randomizer<V> valueRandomizer) {
    this(keyRandomizer, valueRandomizer, getRandomSize());
  }

  /**
   * 创建一个新的 {@link MapRandomizer}，它具有固定数量的条目。
   *
   * @param keyRandomizer
   *     键的随机化器。
   * @param valueRandomizer
   *     值的随机化器。
   * @param size
   *     要生成的条目数。
   */
  public MapRandomizer(final Randomizer<K> keyRandomizer,
      final Randomizer<V> valueRandomizer, final int size) {
    if (keyRandomizer == null) {
      throw new IllegalArgumentException("keyRandomizer must not be null");
    }
    if (valueRandomizer == null) {
      throw new IllegalArgumentException("valueRandomizer must not be null");
    }
    checkArguments(size);
    this.keyRandomizer = keyRandomizer;
    this.valueRandomizer = valueRandomizer;
    this.size = size;
  }

  /**
   * 设置随机化器的上下文。
   *
   * @param context
   *     上下文。
   */
  @Override
  public void setContext(final Context context) {
    if (keyRandomizer instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer<?>) keyRandomizer).setContext(context);
    }
    if (valueRandomizer instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer<?>) valueRandomizer).setContext(context);
    }
  }

  /**
   * 生成一个随机的 {@link Map}。
   *
   * @return
   *     一个随机的 {@link Map}。
   */
  @Override
  public Map<K, V> getRandomValue() {
    final Map<K, V> result = new HashMap<>();
    for (int i = 0; i < size; i++) {
      result.put(keyRandomizer.getRandomValue(), valueRandomizer.getRandomValue());
    }
    return result;
  }

  private void checkArguments(final int nbEntries) {
    if (nbEntries < 0) {
      throw new IllegalArgumentException("The number of entries to generate must be >= 0");
    }
  }

  private static int getRandomSize() {
    return abs(new ByteRandomizer().getRandomValue()) + 1;
  }
}
