////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class Salary {

  private int amount;
  private boolean setterInvoked;

  public int getAmount() {
    return amount;
  }

  public void setAmount(final int amount) {
    setterInvoked = true;
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
    this.amount = amount;
  }

  public boolean isSetterInvoked() {
    return setterInvoked;
  }
}
