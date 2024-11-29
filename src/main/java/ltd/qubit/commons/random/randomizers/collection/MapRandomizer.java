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
 * A {@link Randomizer} that generates a {@link Map} with random entries.
 *
 * @param <K>
 *         the type of keys
 * @param <V>
 *         the type of values
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MapRandomizer<K, V> implements ContextAwareRandomizer<Map<K, V>> {

  private final int size;
  private final Randomizer<K> keyRandomizer;
  private final Randomizer<V> valueRandomizer;

  /**
   * Create a new {@link MapRandomizer} with a random number of entries.
   *
   * @param keyRandomizer
   *         the randomizer for keys
   * @param valueRandomizer
   *         the randomizer for values
   */
  public MapRandomizer(final Randomizer<K> keyRandomizer,
          final Randomizer<V> valueRandomizer) {
    this(keyRandomizer, valueRandomizer, getRandomSize());
  }

  /**
   * Create a new {@link MapRandomizer} with a fixed number of entries.
   *
   * @param keyRandomizer
   *         the randomizer for keys
   * @param valueRandomizer
   *         the randomizer for values
   * @param size
   *         the number of entries to generate
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

  @Override
  public void setContext(final Context context) {
    if (keyRandomizer instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer<?>) keyRandomizer).setContext(context);
    }
    if (valueRandomizer instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer<?>) valueRandomizer).setContext(context);
    }
  }

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
