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

public class ComparableBean {

  private Comparable<Date> dateComparable;

  public ComparableBean() {
  }

  public Comparable<Date> getDateComparable() {
    return this.dateComparable;
  }

  public void setDateComparable(final Comparable<Date> dateComparable) {
    this.dateComparable = dateComparable;
  }

  public static class AlwaysEqual implements Comparable<Date> {
    @Override
    public int compareTo(final Date o) {
      return 0;
    }
  }
}
