////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * A {@link Randomizer} that generates a random value from a given {@link
 * Enum}.
 *
 * @param <E>
 *         the type of elements in the enumeration
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnumRandomizer<E extends Enum<E>> extends AbstractRandomizer<E> {

  private final List<E> enumConstants;

  /**
   * Create a new {@link EnumRandomizer}.
   *
   * @param enumeration
   *         the enumeration from which this randomizer will generate random
   *         values
   */
  public EnumRandomizer(final Class<E> enumeration) {
    this.enumConstants = Arrays.asList(enumeration.getEnumConstants());
  }

  /**
   * Create a new {@link EnumRandomizer}.
   *
   * @param enumeration
   *         the enumeration from which this randomizer will generate random
   *         values
   * @param seed
   *         the initial seed
   */
  public EnumRandomizer(final Class<E> enumeration, final long seed) {
    super(seed);
    this.enumConstants = Arrays.asList(enumeration.getEnumConstants());
  }

  /**
   * Create a new {@link EnumRandomizer}.
   *
   * @param enumeration
   *         the enumeration from which this randomizer will generate random
   *         values
   * @param excludedValues
   *         the values to exclude from random picking
   * @throws IllegalArgumentException
   *         when excludedValues contains all enumeration values, ie all
   *         elements from the enumeration are excluded
   */
  @SafeVarargs
  public EnumRandomizer(final Class<E> enumeration,
          final E... excludedValues) throws IllegalArgumentException {
    checkExcludedValues(enumeration, excludedValues);
    this.enumConstants = getFilteredList(enumeration, excludedValues);
  }

  /**
   * Get a random value within an enumeration or an enumeration subset (when
   * values are excluded).
   *
   * @return a random value within the enumeration
   */
  @Override
  public E getRandomValue() {
    if (enumConstants.isEmpty()) {
      return null;
    }
    final int randomIndex = random.nextInt(enumConstants.size());
    return enumConstants.get(randomIndex);
  }

  @SuppressWarnings("unchecked")
  private void checkExcludedValues(final Class<E> enumeration, final E[] excludedValues) {
    final boolean excludeAllEnums = ArrayUtils.containsAll(excludedValues,
        enumeration.getEnumConstants());
    if (excludeAllEnums) {
      throw new IllegalArgumentException("No enum element available for random picking.");
    }
  }

  /**
   * Get a subset of enumeration.
   *
   * @return the enumeration values minus those excluded.
   */
  @SuppressWarnings("unchecked")
  @SafeVarargs
  private final List<E> getFilteredList(final Class<E> enumeration,
          final E... excludedValues) {
    final List<E> filteredValues = new ArrayList<>();
    Collections.addAll(filteredValues, enumeration.getEnumConstants());
    if (excludedValues != null) {
      for (final E element : excludedValues) {
        filteredValues.remove(element);
      }
    }
    return filteredValues;
  }
}
