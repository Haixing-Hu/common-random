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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.range.InstantRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.LocalDateRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.LocalDateTimeRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.LocalTimeRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.OffsetDateTimeRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.OffsetTimeRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.YearMonthRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.YearRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.ZonedDateTimeRangeRandomizer;
import ltd.qubit.commons.random.randomizers.time.DurationRandomizer;
import ltd.qubit.commons.random.randomizers.time.GregorianCalendarRandomizer;
import ltd.qubit.commons.random.randomizers.time.MonthDayRandomizer;
import ltd.qubit.commons.random.randomizers.time.PeriodRandomizer;
import ltd.qubit.commons.random.randomizers.time.TimeZoneRandomizer;
import ltd.qubit.commons.random.randomizers.time.ZoneIdRandomizer;
import ltd.qubit.commons.random.randomizers.time.ZoneOffsetRandomizer;

/**
 * A registry of randomizers for Java 8 JSR 310 types.
 *
 * @author Mahmoud Ben Hassine, Haixing Hu
 */
@Priority(-3)
public class TimeRandomizerRegistry implements RandomizerRegistry {

  private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    final long seed = parameters.getSeed();
    randomizers.put(Duration.class, new DurationRandomizer(seed));
    randomizers.put(GregorianCalendar.class, new GregorianCalendarRandomizer(seed));
    randomizers.put(Instant.class, new InstantRangeRandomizer(parameters));
    randomizers.put(LocalDate.class, new LocalDateRangeRandomizer(parameters));
    randomizers.put(LocalDateTime.class, new LocalDateTimeRangeRandomizer(parameters));
    randomizers.put(LocalTime.class, new LocalTimeRangeRandomizer(parameters));
    randomizers.put(MonthDay.class, new MonthDayRandomizer(seed));
    randomizers.put(OffsetDateTime.class, new OffsetDateTimeRangeRandomizer(parameters));
    randomizers.put(OffsetTime.class, new OffsetTimeRangeRandomizer(parameters));
    randomizers.put(Period.class, new PeriodRandomizer(seed));
    randomizers.put(TimeZone.class, new TimeZoneRandomizer(seed));
    randomizers.put(YearMonth.class, new YearMonthRangeRandomizer(parameters));
    randomizers.put(Year.class, new YearRangeRandomizer(parameters));
    randomizers.put(ZonedDateTime.class, new ZonedDateTimeRangeRandomizer(parameters));
    randomizers.put(ZoneOffset.class, new ZoneOffsetRandomizer(seed));
    randomizers.put(ZoneId.class, new ZoneIdRandomizer(seed));
  }

  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    return get(field.getType(), context);
  }

  @Override
  public Randomizer<?> get(final Class<?> type, final Context context) {
    return randomizers.get(type);
  }
}
