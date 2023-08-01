////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.random.util.RandomUtils;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CollectionPopulator {

  private final EasyRandom random;

  public CollectionPopulator(final EasyRandom random) {
    this.random = random;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Collection<?> populate(final Field field, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    final Class<?> fieldType = field.getType();
    if (! isCollectionType(fieldType)) {
      throw new UnsupportedOperationException("Only support collection type");
    }
    final Type genericType = field.getGenericType();
    final int size = RandomUtils.getRandomCollectionSize(random, context, sizeRange);
    return RandomUtils.createRandomCollection(random, context, fieldType, genericType, size);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Collection<?> populate(final Class<?> fieldType, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    if (! isCollectionType(fieldType)) {
      throw new UnsupportedOperationException("Only support collection type");
    }
    final int size = RandomUtils.getRandomCollectionSize(random, context, sizeRange);
    return RandomUtils.createRandomCollection(random, context, fieldType, fieldType, size);
  }

  public Randomizer<?> getRandomizer(final Field field,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(field, context, sizeRange);
      }
    };
  }

  public Randomizer<?> getRandomizer(final Class<?> collectionType,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(collectionType, context, sizeRange);
      }
    };
  }
}
