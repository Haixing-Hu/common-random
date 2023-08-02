////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static java.time.LocalDateTime.of;
import static java.time.ZoneOffset.ofTotalSeconds;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

class TimeRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

  //  stop checkstyle: MagicNumberCheck
  static Object[] generateRandomizers() {
    return new Object[]{
        new DurationRandomizer(),
        new LocalDateRandomizer(),
        new MonthDayRandomizer(),
        new LocalTimeRandomizer(),
        new PeriodRandomizer(),
        new YearRandomizer(),
        new YearMonthRandomizer(),
        new ZoneOffsetRandomizer(),
        new CalendarRandomizer(),
        new DateRandomizer(),
        new GregorianCalendarRandomizer(),
        new InstantRandomizer(),
        new LocalDateTimeRandomizer(),
        new OffsetDateTimeRandomizer(),
        new OffsetTimeRandomizer(),
        new SqlDateRandomizer(),
        new SqlTimeRandomizer(),
        new SqlTimestampRandomizer()
    };
  }

  @ParameterizedTest
  @MethodSource("generateRandomizers")
  void generatedTimeShouldNotBeNull(final Randomizer<?> randomizer) {
    // when
    final Object randomNumber = randomizer.getRandomValue();

    then(randomNumber).isNotNull();
  }

  static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
    final Calendar expectedCalendar = Calendar.getInstance();
    expectedCalendar.setTime(new Date(1447946119645L));

    final GregorianCalendar expectedGregorianCalendar = new GregorianCalendar();
    expectedGregorianCalendar.setTimeInMillis(5106534569952410475L);

    return new Object[][]{
            {new DurationRandomizer(SEED), Duration.of(62L, ChronoUnit.HOURS)},
            {new DurationRandomizer(SEED, ChronoUnit.MINUTES),
                    Duration.of(62L, ChronoUnit.MINUTES)},
            {new DurationRandomizer(SEED, ChronoUnit.MILLIS),
                    Duration.of(62L, ChronoUnit.MILLIS)},
            {new LocalDateRandomizer(SEED),
                    LocalDate.of(2016, Month.MARCH, 14)},
            {new MonthDayRandomizer(SEED), MonthDay.of(Month.MARCH, 14)},
            {new LocalTimeRandomizer(SEED), LocalTime.of(21, 57, 49)},
            {new PeriodRandomizer(SEED), Period.of(2016, 3, 14)},
            {new YearRandomizer(SEED), Year.of(2016)},
            {new YearMonthRandomizer(SEED), YearMonth.of(2016, Month.MARCH)},
            {new ZoneOffsetRandomizer(SEED), ZoneOffset.ofTotalSeconds(61722)},
            {new CalendarRandomizer(SEED), expectedCalendar},
            {new DateRandomizer(SEED), new Date(1447946119645L)},
            {new GregorianCalendarRandomizer(SEED), expectedGregorianCalendar},
            {new InstantRandomizer(SEED),
                    Instant.ofEpochSecond(1447946119L, 645000000L)},
            {new LocalDateTimeRandomizer(SEED),
                    LocalDateTime.of(2016, Month.MARCH, 14, 21, 57, 49, 0)},
            {new OffsetDateTimeRandomizer(SEED),
                    OffsetDateTime.of(of(2016, Month.MARCH, 14, 21, 57, 49, 0),
                        ofTotalSeconds(61722))},
            {new OffsetTimeRandomizer(SEED),
                    OffsetTime.of(LocalTime.of(21, 57, 49, 0), ofTotalSeconds(61722))},
            {new SqlDateRandomizer(SEED), new java.sql.Date(1447946119645L)},
            {new SqlTimeRandomizer(SEED), new Time(1447946119645L)},
            {new SqlTimestampRandomizer(SEED), new Timestamp(1447946119645L)}
    };
  }

  @ParameterizedTest
  @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
  void shouldGenerateTheSameValueForTheSameSeed(final Randomizer<?> randomizer,
          final Object expected) {
    //when
    final Object actual = randomizer.getRandomValue();
    System.out.println("expected = " + expected.toString() + ", actual = " + actual
            .toString());
    then(actual).isEqualTo(expected);
  }

  @Test
  void shouldAllowToCreateDurationRandomizerWithSuitableTemporalUnits() {
    assertThat(new DurationRandomizer(ChronoUnit.NANOS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.MICROS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.MILLIS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.SECONDS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.MINUTES).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.HOURS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.HALF_DAYS).getRandomValue())
            .isGreaterThanOrEqualTo(Duration.ZERO);
    assertThat(new DurationRandomizer(ChronoUnit.DAYS).getRandomValue())
        .isGreaterThanOrEqualTo(Duration.ZERO);
  }

  @Test
  void shouldDisallowToCreateDurationRandomizerWithEstimatedTemporalUnits() {
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.WEEKS))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.MONTHS))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.YEARS))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.DECADES))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.CENTURIES))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.MILLENNIA))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.ERAS))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.FOREVER))
        .isInstanceOf(IllegalArgumentException.class);
  }
  //  resume checkstyle: MagicNumberCheck
}
