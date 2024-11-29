////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.annotation.Money;
import ltd.qubit.commons.annotation.Round;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.randomizers.AbstractRandomizerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalRandomizerTest extends AbstractRandomizerTest<BigDecimal> {

  @Test
  public void generatedValueShouldHaveProvidedPositiveScale() {
    final Integer scale = 1;
    randomizer = new BigDecimalRandomizer(scale);
    final BigDecimal bigDecimal = randomizer.getRandomValue();
    assertEquals(scale, bigDecimal.scale());
  }

  @Test
  public void generatedValueShouldHaveProvidedNegativeScale() {
    final Integer scale = -1;
    randomizer = new BigDecimalRandomizer(scale);
    final BigDecimal bigDecimal = randomizer.getRandomValue();
    assertEquals(scale, bigDecimal.scale());
  }

  @Test
  public void testCustomRoundingMode() {
    final long initialSeed = 123;
    final Integer scale = 1;
    final RoundingMode roundingMode = RoundingMode.DOWN;
    randomizer = new BigDecimalRandomizer(initialSeed, scale, roundingMode);
    final BigDecimal bigDecimal = randomizer.getRandomValue();
    assertEquals(new BigDecimal("0.7"), bigDecimal);
  }

  static class Foo {
    @Scale(2)
    @Round(RoundingMode.DOWN)
    BigDecimal value;

    @Money
    BigDecimal money;
  }

  @Test
  public void testContextAwareRoundingMode() {
    final EasyRandom random = new EasyRandom();
    final Foo foo = random.nextObject(Foo.class);
    assertEquals(2, foo.value.scale());
    assertEquals(Money.DEFAULT_SCALE, foo.money.scale());
  }

  static class Goo {
    @Scale(2)
    @Round(RoundingMode.DOWN)
    List<BigDecimal> values;

    @Money
    List<BigDecimal> moneys;

    @Scale(2)
    @Round(RoundingMode.DOWN)
    List<List<BigDecimal>> values2D;

    @Money
    List<List<BigDecimal>> moneys2D;
  }

  @Test
  public void testContextAwareRoundingModeForList() {
    final EasyRandom random = new EasyRandom();
    final Goo goo = random.nextObject(Goo.class);
    for (final BigDecimal value : goo.values) {
      assertEquals(2, value.scale());
    }
    for (final BigDecimal money : goo.moneys) {
      assertEquals(Money.DEFAULT_SCALE, money.scale());
    }
    for (final List<BigDecimal> values : goo.values2D) {
      for (final BigDecimal value : values) {
        assertEquals(2, value.scale());
      }
    }
    for (final List<BigDecimal> moneys : goo.moneys2D) {
      for (final BigDecimal money : moneys) {
        assertEquals(Money.DEFAULT_SCALE, money.scale());
      }
    }
  }
}
