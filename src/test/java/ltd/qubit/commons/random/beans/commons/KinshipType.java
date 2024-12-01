////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * 此枚举表示亲属关系类型。
 *
 * @author 潘凯
 */
public enum KinshipType {

  /**
   * 本人。
   */
  SELF,

  /**
   * 父母。
   */
  PARENT,

  /**
   * 配偶。
   */
  SPOUSE,

  /**
   * 子女。
   */
  CHILD,

  /**
   * 其他。
   */
  OTHER;

  static {
    registerLocalizedNames(KinshipType.class, "i18n.kinship-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}
