////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.misc.BooleanRandomizer;
import ltd.qubit.commons.random.randomizers.misc.LocaleRandomizer;
import ltd.qubit.commons.random.randomizers.misc.SkipRandomizer;
import ltd.qubit.commons.random.randomizers.misc.UuidRandomizer;
import ltd.qubit.commons.random.randomizers.net.UriRandomizer;
import ltd.qubit.commons.random.randomizers.net.UrlRandomizer;
import ltd.qubit.commons.random.randomizers.number.AtomicIntegerRandomizer;
import ltd.qubit.commons.random.randomizers.number.AtomicLongRandomizer;
import ltd.qubit.commons.random.randomizers.number.BigDecimalRandomizer;
import ltd.qubit.commons.random.randomizers.number.BigIntegerRandomizer;
import ltd.qubit.commons.random.randomizers.number.ByteRandomizer;
import ltd.qubit.commons.random.randomizers.number.DoubleRandomizer;
import ltd.qubit.commons.random.randomizers.number.FloatRandomizer;
import ltd.qubit.commons.random.randomizers.number.IntegerRandomizer;
import ltd.qubit.commons.random.randomizers.number.LongRandomizer;
import ltd.qubit.commons.random.randomizers.number.ShortRandomizer;
import ltd.qubit.commons.random.randomizers.range.DateRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.SqlDateRangeRandomizer;
import ltd.qubit.commons.random.randomizers.text.CharacterRandomizer;
import ltd.qubit.commons.random.randomizers.text.StringRandomizer;
import ltd.qubit.commons.random.randomizers.time.CalendarRandomizer;
import ltd.qubit.commons.random.randomizers.time.SqlTimeRandomizer;
import ltd.qubit.commons.random.randomizers.time.SqlTimestampRandomizer;

/**
 * Registry for Java built-in types.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-4)
public class InternalRandomizerRegistry implements RandomizerRegistry {

  private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    final long seed = parameters.getSeed();
    randomizers.put(String.class, new StringRandomizer(parameters));
    randomizers.put(Character.class, new CharacterRandomizer(seed));
    randomizers.put(char.class, new CharacterRandomizer(seed));
    randomizers.put(Boolean.class, new BooleanRandomizer(seed));
    randomizers.put(boolean.class, new BooleanRandomizer(seed));
    randomizers.put(Byte.class, new ByteRandomizer(seed));
    randomizers.put(byte.class, new ByteRandomizer(seed));
    randomizers.put(Short.class, new ShortRandomizer(seed));
    randomizers.put(short.class, new ShortRandomizer(seed));
    randomizers.put(Integer.class, new IntegerRandomizer(seed));
    randomizers.put(int.class, new IntegerRandomizer(seed));
    randomizers.put(Long.class, new LongRandomizer(seed));
    randomizers.put(long.class, new LongRandomizer(seed));
    randomizers.put(Double.class, new DoubleRandomizer(seed));
    randomizers.put(double.class, new DoubleRandomizer(seed));
    randomizers.put(Float.class, new FloatRandomizer(seed));
    randomizers.put(float.class, new FloatRandomizer(seed));
    randomizers.put(BigInteger.class, new BigIntegerRandomizer(seed));
    randomizers.put(BigDecimal.class, new BigDecimalRandomizer(seed));
    randomizers.put(AtomicLong.class, new AtomicLongRandomizer(seed));
    randomizers.put(AtomicInteger.class, new AtomicIntegerRandomizer(seed));
    randomizers.put(Date.class, new DateRangeRandomizer(parameters));
    randomizers.put(java.sql.Date.class, new SqlDateRangeRandomizer(parameters));
    randomizers.put(java.sql.Time.class, new SqlTimeRandomizer(seed));
    randomizers.put(java.sql.Timestamp.class, new SqlTimestampRandomizer(seed));
    randomizers.put(Calendar.class, new CalendarRandomizer(seed));
    randomizers.put(URL.class, new UrlRandomizer(seed));
    randomizers.put(URI.class, new UriRandomizer(seed));
    randomizers.put(Locale.class, new LocaleRandomizer(seed));
    randomizers.put(UUID.class, new UuidRandomizer(seed));
    // issue #280: skip fields of type Class
    randomizers.put(Class.class, new SkipRandomizer());
  }

  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    return randomizers.get(field.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer<?> get(final Class<?> type, final Context context) {
    return randomizers.get(type);
  }
}
