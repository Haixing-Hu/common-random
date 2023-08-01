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
import java.util.Map;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.api.ObjectFactory;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractContextAwareRandomizer;
import ltd.qubit.commons.random.util.RandomUtils;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.reflect.ClassUtils.isMapType;

/**
 * Random map populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MapPopulator {

  private final EasyRandom random;
  private final ObjectFactory objectFactory;

  public MapPopulator(final EasyRandom random) {
    this.random = random;
    this.objectFactory = random.getObjectFactory();
  }

  public MapPopulator(final EasyRandom random, final ObjectFactory objectFactory) {
    this.random = random;
    this.objectFactory = objectFactory;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Map<?, ?> populate(final Field field, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    final Class<?> fieldType = field.getType();
    if (! isMapType(fieldType)) {
      throw new UnsupportedOperationException("Only support map type");
    }
    final Type fieldGenericType = field.getGenericType();
    final int size = RandomUtils.getRandomCollectionSize(random, context, sizeRange);
    return RandomUtils.createRandomMap(random, objectFactory, context, fieldType,
            fieldGenericType, size);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Map<?, ?> populate(final Class<?> fieldType, final Context context,
          final @Nullable CloseRange<Integer> sizeRange) {
    if (! isMapType(fieldType)) {
      throw new UnsupportedOperationException("Only support map type");
    }
    final int size = RandomUtils.getRandomCollectionSize(random, context, sizeRange);
    return RandomUtils.createRandomMap(random, objectFactory, context, fieldType,
            fieldType, size);
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

  public Randomizer<?> getRandomizer(final Class<?> fieldType,
          final @Nullable CloseRange<Integer> sizeRange) {
    return new AbstractContextAwareRandomizer<Object>() {
      @Override
      public Object getRandomValue() {
        return populate(fieldType, context, sizeRange);
      }
    };
  }
}
