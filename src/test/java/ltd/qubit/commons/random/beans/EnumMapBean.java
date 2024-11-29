////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.EnumMap;

public class EnumMapBean {

  private EnumMap<Gender, String> typedEnumMap;

  @SuppressWarnings("rawtypes")
  private EnumMap untypedEnumMap;

  public EnumMapBean() {
  }

  public EnumMap<Gender, String> getTypedEnumMap() {
    return this.typedEnumMap;
  }

  @SuppressWarnings("rawtypes")
  public EnumMap getUntypedEnumMap() {
    return this.untypedEnumMap;
  }

  public void setTypedEnumMap(final EnumMap<Gender, String> typedEnumMap) {
    this.typedEnumMap = typedEnumMap;
  }

  @SuppressWarnings("rawtypes")
  public void setUntypedEnumMap(final EnumMap untypedEnumMap) {
    this.untypedEnumMap = untypedEnumMap;
  }
}
