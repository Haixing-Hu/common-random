////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
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
 * Date utilities class.
 *
 * <strong>This class is intended for internal use only.</strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @deprecated This class is deprecated since v4.3 and will be removed in v5.0
 */
@Deprecated
public final class DateUtils {

  public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private DateUtils() {
  }

  private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

  /**
   * Convert a {@link ZonedDateTime} to {@link Date}.
   *
   * @param zonedDateTime
   *     to convert
   * @return the corresponding {@link Date} object
   */
  public static Date toDate(final ZonedDateTime zonedDateTime) {
    return from(zonedDateTime.toInstant());
  }

  /**
   * Parse a string formatted in {@link DateUtils#DATE_FORMAT} to a {@link
   * Date}.
   *
   * @param value
   *     date to parse. Should be in {@link DateUtils#DATE_FORMAT} format.
   * @return parsed date
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
