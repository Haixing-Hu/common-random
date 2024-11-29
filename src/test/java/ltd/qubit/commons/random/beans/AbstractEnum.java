////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public enum AbstractEnum {
  VAL1() {
    @Override
    public String test() {
      return "1";
    }
  },
  VAL2() {
    @Override
    public String test() {
      return "2";
    }
  };

  public abstract String test();
}
