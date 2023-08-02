////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Date;

import ltd.qubit.commons.random.annotation.Randomizer;
import ltd.qubit.commons.random.annotation.RandomizerArgument;
import ltd.qubit.commons.random.randomizers.range.DateRangeRandomizer;
import ltd.qubit.commons.random.randomizers.range.IntegerRangeRandomizer;

public class TestData {

  public TestData() {
  }

  @Randomizer(value = DateRangeRandomizer.class, args = {
          @RandomizerArgument(value = "2016-01-10 00:00:00", type = Date.class),
          @RandomizerArgument(value = "2016-01-30 23:59:59", type = Date.class)
  })
  private Date date;

  @Randomizer(value = IntegerRangeRandomizer.class, args = {
          @RandomizerArgument(value = "200", type = Integer.class),
          @RandomizerArgument(value = "500", type = Integer.class)
  })
  private int price;

  public Date getDate() {
    return date;
  }

  public int getPrice() {
    return price;
  }
}
