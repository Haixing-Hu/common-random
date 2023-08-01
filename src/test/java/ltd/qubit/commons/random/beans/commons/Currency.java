////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示货币单位。
 *
 * @author 胡海星
 */
public enum Currency {

  /**
   * 人民币。
   */
  CNY("CNY"),

  /**
   * 港币。
   */
  HKD("HKD"),

  /**
   * 新台币。
   */
  TWD("TWD"),

  /**
   * 美元。
   */
  USD("USD"),

  /**
   * 欧元。
   */
  EUR("EUR"),

  /**
   * 英镑。
   */
  GBP("GBP"),

  /**
   * 日元。
   */
  JPY("JPY"),

  /**
   * 虚拟货币。
   */
  VIRTUAL("VIRTUAL");

  private final String code;

  Currency(final String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }

  static {
    registerLocalizedNames(Currency.class, "i18n/currency");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }

  public static Currency forCode(final String code) {
    return CODE_MAP.get(code);
  }

  private static final Map<String, Currency> CODE_MAP = new HashMap<>();
  static {
    for (final Currency e : values()) {
      CODE_MAP.put(e.code, e);
    }
  }
}
