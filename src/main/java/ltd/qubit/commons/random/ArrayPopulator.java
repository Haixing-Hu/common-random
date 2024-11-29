////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Array;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.lang.ClassUtils.isArrayType;
import static ltd.qubit.commons.random.util.RandomUtils.getRandomCollectionSize;

/**
 * Random array populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ArrayPopulator {

  private final EasyRandom random;

  public ArrayPopulator(final EasyRandom random) {
    this.random = random;
  }

  public Object populate(final Class<?> fieldType, final Context context,
          @Nullable final CloseRange<Integer> sizeRange) {
    if (! isArrayType(fieldType)) {
      throw new UnsupportedOperationException("Only support array type");
    }
    final int size = getRandomCollectionSize(random, context, sizeRange);
    final Class<?> componentType = fieldType.getComponentType();
    final Object result = Array.newInstance(componentType, size);
    for (int i = 0; i < size; i++) {
      final Object element = random.nextObject(componentType, context);
      Array.set(result, i, element);
    }
    return result;
  }

  public Randomizer<?> getRandomizer(final Class<?> fieldType,
          @Nullable final CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(fieldType, context, sizeRange);
      }
    };
  }
}
