////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.TimeBean;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeRangeParameterTest {

  @Test
  void testDateRange() {
    // Given
    final LocalDate minDate = LocalDate.of(2016, 1, 1);
    final LocalDate maxDate = LocalDate.of(2016, 1, 31);
    final Parameters parameters = new Parameters().dateRange(minDate, maxDate);

    // When
    final TimeBean timeBean = new EasyRandom(parameters).nextObject(TimeBean.class);

    // Then
    assertThat(timeBean.getLocalDate()).isAfterOrEqualTo(minDate)
                                       .isBeforeOrEqualTo(maxDate);
  }

  @Test
  void testTimeRange() {
    // Given
    final LocalTime minTime = LocalTime.of(15, 0, 0);
    final LocalTime maxTime = LocalTime.of(18, 0, 0);
    final Parameters parameters = new Parameters().timeRange(minTime, maxTime);

    // When
    final TimeBean timeBean = new EasyRandom(parameters).nextObject(TimeBean.class);

    // Then
    assertThat(timeBean.getLocalTime()).isAfterOrEqualTo(minTime)
                                       .isBeforeOrEqualTo(maxTime);
  }
}
