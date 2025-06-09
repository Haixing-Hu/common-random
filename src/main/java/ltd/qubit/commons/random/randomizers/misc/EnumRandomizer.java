////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
 * 一个 {@link Randomizer}，可以从给定的 {@link Enum} 中生成一个随机值。
 *
 * @param <E>
 *     枚举中元素的类型
 * @author 胡海星
 */
public class EnumRandomizer<E extends Enum<E>> extends AbstractRandomizer<E> {

  private final List<E> enumConstants;

  /**
   * 创建一个新的 {@link EnumRandomizer}。
   *
   * @param enumeration
   *     此随机化器将从中生成随机值的枚举
   */
  public EnumRandomizer(final Class<E> enumeration) {
    this.enumConstants = Arrays.asList(enumeration.getEnumConstants());
  }

  /**
   * 创建一个新的 {@link EnumRandomizer}。
   *
   * @param enumeration
   *     此随机化器将从中生成随机值的枚举
   * @param seed
   *     初始种子
   */
  public EnumRandomizer(final Class<E> enumeration, final long seed) {
    super(seed);
    this.enumConstants = Arrays.asList(enumeration.getEnumConstants());
  }

  /**
   * 创建一个新的 {@link EnumRandomizer}。
   *
   * @param enumeration
   *     此随机化器将从中生成随机值的枚举
   * @param excludedValues
   *     要从随机选择中排除的值
   * @throws IllegalArgumentException
   *     当 excludedValues 包含所有枚举值时，即枚举中的所有元素都被排除时
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public EnumRandomizer(final Class<E> enumeration,
          final E... excludedValues) throws IllegalArgumentException {
    checkExcludedValues(enumeration, excludedValues);
    this.enumConstants = getFilteredList(enumeration, excludedValues);
  }

  /**
   * 在枚举或枚举子集中获取随机值（当值被排除时）。
   *
   * @return 枚举中的随机值
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
   * 获取枚举的子集。
   *
   * @return 枚举值减去排除的值。
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
