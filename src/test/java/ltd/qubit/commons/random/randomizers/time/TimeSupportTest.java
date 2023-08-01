////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.Instant;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.TimeBean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.FieldPredicates.inClass;
import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

import static org.assertj.core.api.Assertions.assertThat;

class TimeSupportTest {

  private EasyRandom easyRandom;

  @BeforeEach
  void setUp() {
    easyRandom = new EasyRandom();
  }

  @Test
  void threeTenTypesShouldBePopulated() {
    final TimeBean timeBean = easyRandom.nextObject(TimeBean.class);

    assertThat(timeBean).hasNoNullFieldsOrProperties();
  }

  // https://github.com/j-easy/easy-random/issues/135
  @Test
  void threeTenRandomizersCanBeOverriddenByCustomRandomizers() {
    final Parameters parameters = new Parameters()
            .excludeField(named("instant").and(ofType(Instant.class))
                                          .and(inClass(TimeBean.class)));
    easyRandom = new EasyRandom(parameters);

    final TimeBean timeBean = easyRandom.nextObject(TimeBean.class);

    assertThat(timeBean.getInstant()).isNull();
  }
}
