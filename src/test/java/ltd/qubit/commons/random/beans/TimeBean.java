////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeBean {

  private Duration duration;

  private Instant instant;

  private LocalTime localTime;

  private LocalDate localDate;

  private LocalDateTime localDateTime;

  private MonthDay monthDay;

  private Month month;

  private OffsetDateTime offsetDateTime;

  private OffsetTime offsetTime;

  private Period period;

  private YearMonth yearMonth;

  private Year year;

  private ZonedDateTime zonedDateTime;

  private ZoneOffset zoneOffset;

  private ZoneId zoneId;

  public TimeBean() {
  }

  public Duration getDuration() {
    return this.duration;
  }

  public Instant getInstant() {
    return this.instant;
  }

  public LocalTime getLocalTime() {
    return this.localTime;
  }

  public LocalDate getLocalDate() {
    return this.localDate;
  }

  public LocalDateTime getLocalDateTime() {
    return this.localDateTime;
  }

  public MonthDay getMonthDay() {
    return this.monthDay;
  }

  public Month getMonth() {
    return this.month;
  }

  public OffsetDateTime getOffsetDateTime() {
    return this.offsetDateTime;
  }

  public OffsetTime getOffsetTime() {
    return this.offsetTime;
  }

  public Period getPeriod() {
    return this.period;
  }

  public YearMonth getYearMonth() {
    return this.yearMonth;
  }

  public Year getYear() {
    return this.year;
  }

  public ZonedDateTime getZonedDateTime() {
    return this.zonedDateTime;
  }

  public ZoneOffset getZoneOffset() {
    return this.zoneOffset;
  }

  public ZoneId getZoneId() {
    return this.zoneId;
  }

  public void setDuration(final Duration duration) {
    this.duration = duration;
  }

  public void setInstant(final Instant instant) {
    this.instant = instant;
  }

  public void setLocalTime(final LocalTime localTime) {
    this.localTime = localTime;
  }

  public void setLocalDate(final LocalDate localDate) {
    this.localDate = localDate;
  }

  public void setLocalDateTime(final LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

  public void setMonthDay(final MonthDay monthDay) {
    this.monthDay = monthDay;
  }

  public void setMonth(final Month month) {
    this.month = month;
  }

  public void setOffsetDateTime(final OffsetDateTime offsetDateTime) {
    this.offsetDateTime = offsetDateTime;
  }

  public void setOffsetTime(final OffsetTime offsetTime) {
    this.offsetTime = offsetTime;
  }

  public void setPeriod(final Period period) {
    this.period = period;
  }

  public void setYearMonth(final YearMonth yearMonth) {
    this.yearMonth = yearMonth;
  }

  public void setYear(final Year year) {
    this.year = year;
  }

  public void setZonedDateTime(final ZonedDateTime zonedDateTime) {
    this.zonedDateTime = zonedDateTime;
  }

  public void setZoneOffset(final ZoneOffset zoneOffset) {
    this.zoneOffset = zoneOffset;
  }

  public void setZoneId(final ZoneId zoneId) {
    this.zoneId = zoneId;
  }
}
