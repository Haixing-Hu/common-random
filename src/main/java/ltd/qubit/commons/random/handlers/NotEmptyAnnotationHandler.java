////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;

import javax.validation.constraints.NotEmpty;

import ltd.qubit.commons.random.ArrayPopulator;
import ltd.qubit.commons.random.CollectionPopulator;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.MapPopulator;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.text.StringRandomizer;
import ltd.qubit.commons.util.range.CloseRange;

import static ltd.qubit.commons.reflect.ClassUtils.isArrayType;
import static ltd.qubit.commons.reflect.ClassUtils.isCollectionType;
import static ltd.qubit.commons.reflect.ClassUtils.isMapType;

/**
 * An annotation handler for the {@link NotEmpty} annotation.
 *
 * @author Haixing Hu
 */
public class NotEmptyAnnotationHandler implements AnnotationHandler {

  private final long seed;
  private final Parameters parameters;
  private final EasyRandom random;
  private final ArrayPopulator arrayPopulator;
  private final CollectionPopulator collectionPopulator;
  private final MapPopulator mapPopulator;

  public NotEmptyAnnotationHandler(final long seed, final EasyRandom random,
          final Parameters parameters) {
    this.seed = seed;
    this.random = random;
    this.parameters = parameters;
    this.arrayPopulator = random.getArrayPopulator();
    this.collectionPopulator = random.getCollectionPopulator();
    this.mapPopulator = random.getMapPopulator();
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Randomizer<?> getRandomizer(final Field field, final Context context) {
    // NotEmpty sizeAnnotation = getAnnotation(field, NotEmpty.class);
    final CloseRange<Integer> defaultSizeRange = parameters.getCollectionSizeRange();
    final int min = (defaultSizeRange.getMin() == 0 ? 1 : defaultSizeRange.getMin());
    final int max = defaultSizeRange.getMax();
    final CloseRange<Integer> sizeRange = new CloseRange<>(min, max);
    final Class<?> fieldType = field.getType();
    if (fieldType.equals(String.class)) {
      return new StringRandomizer(sizeRange, random.nextLong());
    } else if (isArrayType(fieldType)) {
      return arrayPopulator.getRandomizer(fieldType, sizeRange);
    } else if (isCollectionType(fieldType)) {
      return collectionPopulator.getRandomizer(field, sizeRange);
    } else if (isMapType(fieldType)) {
      return mapPopulator.getRandomizer(field, sizeRange);
    } else {
      return null;
    }
  }
}
