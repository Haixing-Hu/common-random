////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.util.Locale;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示日期类型。
 *
 * @author 胡海星
 */
public enum DayType {

  /**
   * 任何日期。
   */
  ANY,

  /**
   * 工作日。
   */
  WORKING_DAY,

  /**
   * 节假日。
   */
  HOLIDAY,

  /**
   * 周末。
   */
  WEEKEND;

  static {
    registerLocalizedNames(DayType.class, "i18n/day-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}
