////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.Date.from;

/**
 * 日期工具类。
 *
 * <p><strong>此类仅供内部使用。</strong>
 *
 * @author 胡海星
 * @deprecated此类自v4.3版起已弃用，并将在v5.0版中删除
 */
@Deprecated
public final class DateUtils {

  public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private DateUtils() {
  }

  private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

  /**
   * 将{@link ZonedDateTime}转换为{@link Date}。
   *
   * @param zonedDateTime
   *     要转换的{@link ZonedDateTime}
   * @return 相应的{@link Date}对象
   */
  public static Date toDate(final ZonedDateTime zonedDateTime) {
    return from(zonedDateTime.toInstant());
  }

  /**
   * 将以{@link DateUtils#DATE_FORMAT}格式化的字符串解析为{@link Date}。
   *
   * @param value
   *     要解析的日期。应采用{@link DateUtils#DATE_FORMAT}格式。
   * @return 解析后的日期
   */
  public static Date parse(final String value) {
    try {
      return SIMPLE_DATE_FORMAT.parse(value);
    } catch (final ParseException e) {
      LOGGER.severe("Unable to convert value " + value
          + " to a date using format " + DATE_FORMAT);
      return null;
    }
  }

}
