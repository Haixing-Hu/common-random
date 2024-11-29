////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.beans.Salary;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

import static ltd.qubit.commons.random.FieldPredicates.inClass;
import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

public class BypassSettersParameterTest {

  //  stop checkstyle: MagicNumberCheck
  @Test
  void whenBypassSettersIsActivated_thenShouldNotInvokeSetters() {
    // given
    final Parameters parameters = new Parameters()
            .bypassSetters(true)
            .randomize(named("amount").and(ofType(int.class))
                                      .and(inClass(Salary.class)),
                    new IntegerRangeRandomizer(-10, -1))
            .excludeField(named("setterInvoked").and(ofType(boolean.class))
                                                .and(inClass(Salary.class)));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Salary salary = easyRandom.nextObject(Salary.class);

    // then
    assertThat(salary).isNotNull();
    assertThat(salary.getAmount()).isBetween(-10, -1);
    assertThat(salary.isSetterInvoked()).isFalse();
  }

  @Test
  void whenBypassSettersIsNotActivated_thenShouldInvokeSetters() {
    // given
    final Parameters parameters = new Parameters()
            .bypassSetters(true)
            .randomize(named("amount").and(ofType(int.class))
                                      .and(inClass(Salary.class)),
                    new IntegerRangeRandomizer(-10, -1));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Salary salary = easyRandom.nextObject(Salary.class);

    // then
    assertThat(salary).isNotNull();
    assertThat(salary.getAmount()).isBetween(-10, -1);
    assertThat(salary.isSetterInvoked()).isTrue();
  }
  //  resume checkstyle: MagicNumberCheck
}
