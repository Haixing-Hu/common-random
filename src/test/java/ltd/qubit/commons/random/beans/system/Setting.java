////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.error.InvalidSettingValueException;
import ltd.qubit.commons.error.TypeConvertException;
import ltd.qubit.commons.error.TypeMismatchException;
import ltd.qubit.commons.error.UnsupportedDataTypeException;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.DateUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.random.beans.util.Creatable;
import ltd.qubit.commons.random.beans.util.Modifiable;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.text.BooleanFormat;
import ltd.qubit.commons.text.DateFormat;
import ltd.qubit.commons.text.NumberFormat;
import ltd.qubit.commons.text.Splitter;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.codec.HexCodec;

/**
 * 此模型表示系统设置条目。
 *
 * <p>每条系统设置，包含一个名称，一个类型，一个或多个值。
 *
 * @author 胡海星
 */
public class Setting implements Serializable, WithName, Creatable, Modifiable,
    Comparable<Setting>, Assignable<Setting> {

  private static final long serialVersionUID = -498430514095909438L;

  /**
   * The string representing the {@code null} value.
   */
  public static final String NULL_STRING = "[null]";

  /**
   * The character used to separate the list of non-string values.
   */
  public static final char NON_STRING_SEPARATOR = ',';

  /**
   * The character used to separate the list of string values.
   */
  public static final char STRING_SEPARATOR = '\n';

  /**
   * 名称，全局唯一。
   */
  @Size(min = 1, max = 256)
  @Identifier
  private String name;

  /**
   * 取值类型。
   */
  private Type type;

  /**
   * 是否只读。
   */
  private boolean readonly;

  /**
   * 是否可以为空。
   */
  private boolean nullable;

  /**
   * 是否可以取多个值。
   */
  private boolean multiple;

  /**
   * 是否加密。
   */
  private boolean encrypted;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 取值，以字符串形式表示。
   */
  @Nullable
  private String value;

  /**
   * 创建时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 从文本表示解析出的具体类型的值。
   */
  @JsonIgnore
  private transient Object parsedValue;

  /**
   * 若允许多值，则此字段存储取值的数目。
   */
  @JsonIgnore
  private transient int count;

  /**
   * Constructs a {@link Setting}.
   */
  public Setting() {
    this((String) null);
  }

  /**
   * Constructs a {@link Setting}.
   *
   * @param name
   *     the ID of the new setting.
   */
  public Setting(@Nullable final String name) {
    this.name = name;
    type = Type.STRING;
    readonly = false;
    nullable = false;
    multiple = false;
    encrypted = false;
    description = null;
    value = null;
    parsedValue = null;
    count = 0;
  }

  public Setting(final Setting other) {
    assign(other);
  }

  @Override
  public void assign(final Setting other) {
    Argument.requireNonNull("other", other);
    name = other.name;
    type = other.type;
    readonly = other.readonly;
    nullable = other.nullable;
    multiple = other.multiple;
    encrypted = other.encrypted;
    description = other.description;
    value = other.value;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
  }

  @Override
  public Setting clone() {
    return new Setting(this);
  }

  /**
   * Gets the name of this setting.
   *
   * @return the name of this setting, or {@code null} if not set.
   */
  public final String getName() {
    return name;
  }

  /**
   * Sets the name of this setting.
   *
   * @param name
   *     the new name of this setting, or {@code null} to set none.
   */
  public final void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets the type of values of this setting.
   *
   * @return the type of values of this setting, which will never be {@code
   *     null}.
   */
  public final Type getType() {
    return type;
  }

  /**
   * Sets the type of values of this setting.
   *
   * <p><b>NOTE:</b> setting a new type will clear all values in this setting.
   *
   * @param type
   *     the new type to set, which cannot be {@code null}.
   */
  public final void setType(final Type type) {
    if (this.type != type) {
      this.type = type;
      count = 0;
      value = null;
      parsedValue = null;
    }
  }

  /**
   * Tests whether this setting is read only.
   *
   * @return {@code true} if this setting is read only; {@code false} otherwise.
   */
  public final boolean isReadonly() {
    return readonly;
  }

  /**
   * Sets whether this setting is read only.
   *
   * @param readonly
   *     {@code true} to set this setting to be read only; {@code false}
   *     otherwise.
   */
  public final void setReadonly(final boolean readonly) {
    this.readonly = readonly;
  }

  /**
   * Tests whether the value of this setting could be {@code null}.
   *
   * @return {@code true} if the value of this setting could be {@code null};
   *     {@code false} otherwise.
   */
  public final boolean isNullable() {
    return nullable;
  }

  /**
   * Sets whether the value of this setting could be {@code null}.
   *
   * @param nullable
   *     {@code true} if the value of this setting could be {@code null}; {@code
   *     false} otherwise.
   */
  public final void setNullable(final boolean nullable) {
    this.nullable = nullable;
  }

  /**
   * Tests whether this setting could have multiple values.
   *
   * @return {@code true} if this setting could have multiple values; {@code
   *     false} otherwise.
   */
  public final boolean isMultiple() {
    return multiple;
  }

  /**
   * Sets whether this setting could have multiple values.
   *
   * @param multiple
   *     {@code true} to set this setting to be have multiple values; {@code
   *     false} otherwise.
   */
  public final void setMultiple(final boolean multiple) {
    this.multiple = multiple;
  }

  /**
   * Tests whether the value of this setting is encrypted.
   *
   * @return {@code true} if the value of this setting is encrypted; {@code
   *     false} otherwise.
   */
  public final boolean isEncrypted() {
    return encrypted;
  }

  /**
   * Sets whether the value of this setting is encrypted.
   *
   * @param encrypted
   *     {@code true} if the value of this setting is encrypted; {@code false}
   *     otherwise.
   */
  public final void setEncrypted(final boolean encrypted) {
    this.encrypted = encrypted;
  }

  /**
   * Gets the descriptions of this setting.
   *
   * @return the descriptions of this setting, or {@code null} if none.
   */
  @Nullable
  public final String getDescription() {
    return description;
  }

  /**
   * Sets the description of this setting.
   *
   * @param description
   *     the descriptions of this setting, or {@code null} if none.
   */
  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Gets the value or values of this setting as a string.
   *
   * @return the value or values of this setting as a string, or {@code null} if
   *     none.
   */
  @Nullable
  public final String getValue() {
    return value;
  }

  /**
   * Sets the value or values of this setting as a string.
   *
   * @param value
   *     the new value or values of this setting as a string, or {@code null} to
   *     set none.
   */
  public final void setValue(@Nullable final String value) {
    this.value = value;
    parsedValue = null;
    count = 1;
  }

  /**
   * Parses the value or values of this setting from the string representation.
   *
   * @throws InvalidSettingValueException
   *     if the value of this setting is invalid.
   */
  private void parse() {
    if (value == null) {
      parsedValue = null;
      count = 0;
    } else {
      final char separator;
      if (type == Type.STRING) {
        separator = STRING_SEPARATOR;
      } else {
        separator = NON_STRING_SEPARATOR;
      }
      final List<String> list = new Splitter()
          .byChar(separator)
          .strip(true)
          .ignoreEmpty(true)
          .split(value);
      if (list.isEmpty()) {
        value = null;
        parsedValue = null;
        count = 0;
      } else if (list.size() == 1) {
        parseSingle(name, type, list.get(0));
      } else {
        parseMultiple(name, type, list);
      }
    }
  }

  /**
   * Parses the string representation of value of a setting.
   *
   * @param name
   *     the name of the setting.
   * @param type
   *     the type of the setting.
   * @param str
   *     the string representation of the value of the setting.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private void parseSingle(final String name, final Type type,
      final String str) {
    switch (type) {
      case BOOL:
        parsedValue = parseBool(name, str);
        value = formatBool((Boolean) parsedValue);
        break;
      case CHAR:
        parsedValue = parseChar(name, str);
        value = formatChar((Character) parsedValue);
        break;
      case BYTE:
        parsedValue = parseByte(name, str);
        value = formatByte((Byte) parsedValue);
        break;
      case SHORT:
        parsedValue = parseShort(name, str);
        value = formatShort((Short) parsedValue);
        break;
      case INT:
        parsedValue = parseInt(name, str);
        value = formatInt((Integer) parsedValue);
        break;
      case LONG:
        parsedValue = parseLong(name, str);
        value = formatLong((Long) parsedValue);
        break;
      case FLOAT:
        parsedValue = parseFloat(name, str);
        value = formatFloat((Float) parsedValue);
        break;
      case DOUBLE:
        parsedValue = parseDouble(name, str);
        value = formatDouble((Double) parsedValue);
        break;
      case STRING:
        parsedValue = parseString(name, str);
        value = formatString((String) parsedValue);
        break;
      case DATE:
        parsedValue = parseDate(name, str);
        value = formatDate((LocalDate) parsedValue);
        break;
      case TIME:
        parsedValue = parseTime(name, str);
        value = formatTime((LocalTime) parsedValue);
        break;
      case DATETIME:
        parsedValue = parseDateTime(name, str);
        value = formatDateTime((LocalDateTime) parsedValue);
        break;
      case TIMESTAMP:
        parsedValue = parseTimestamp(name, str);
        value = formatTimestamp((Timestamp) parsedValue);
        break;
      case BYTE_ARRAY:
        parsedValue = parseByteArray(name, str);
        value = formatByteArray((byte[]) parsedValue);
        break;
      case BIG_INTEGER:
        parsedValue = parseBigInteger(name, str);
        value = formatBigInteger((BigInteger) parsedValue);
        break;
      case BIG_DECIMAL:
        parsedValue = parseBigDecimal(name, str);
        value = formatBigDecimal((BigDecimal) parsedValue);
        break;
      default:
        throw new UnsupportedDataTypeException(type);
    }
    count = 1;
  }

  /**
   * Parses the string representation of values of a setting.
   *
   * @param name
   *     the ID of the setting.
   * @param type
   *     the type of the setting.
   * @param list
   *     the list of string representations of values of the setting.
   * @throws NullPointerException
   *     if {@code values} is {@code null}.
   * @throws TypeConvertException
   *     if the conversion failed.
   */
  private void parseMultiple(final String name, final Type type,
          final List<String> list) {
    assert (list.size() > 1);
    switch (type) {
      case BOOL: {
        final List<Boolean> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseBool(name, str));
        }
        parsedValue = result.toArray(new Boolean[0]);
        value = formatBools((Boolean[]) parsedValue);
        break;
      }
      case CHAR: {
        final List<Character> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseChar(name, str));
        }
        parsedValue = result.toArray(new Character[0]);
        value = formatChars((Character[]) parsedValue);
        break;
      }
      case BYTE: {
        final List<Byte> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseByte(name, str));
        }
        parsedValue = result.toArray(new Byte[0]);
        value = formatBytes((Byte[]) parsedValue);
        break;
      }
      case SHORT: {
        final List<Short> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseShort(name, str));
        }
        parsedValue = result.toArray(new Short[0]);
        value = formatShorts((Short[]) parsedValue);
        break;
      }
      case INT: {
        final List<Integer> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseInt(name, str));
        }
        parsedValue = result.toArray(new Integer[0]);
        value = formatInts((Integer[]) parsedValue);
        break;
      }
      case LONG: {
        final List<Long> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseLong(name, str));
        }
        parsedValue = result.toArray(new Long[0]);
        value = formatLongs((Long[]) parsedValue);
        break;
      }
      case FLOAT: {
        final List<Float> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseFloat(name, str));
        }
        parsedValue = result.toArray(new Float[0]);
        value = formatFloats((Float[]) parsedValue);
        break;
      }
      case DOUBLE: {
        final List<Double> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseDouble(name, str));
        }
        parsedValue = result.toArray(new Double[0]);
        value = formatDoubles((Double[]) parsedValue);
        break;
      }
      case STRING: {
        final List<String> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseString(name, str));
        }
        parsedValue = result.toArray(new String[0]);
        value = formatStrings((String[]) parsedValue);
        break;
      }
      case DATE: {
        final List<LocalDate> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseDate(name, str));
        }
        parsedValue = result.toArray(new LocalDate[0]);
        value = formatDates((LocalDate[]) parsedValue);
        break;
      }
      case TIME: {
        final List<LocalTime> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseTime(name, str));
        }
        parsedValue = result.toArray(new LocalTime[0]);
        value = formatTimes((LocalTime[]) parsedValue);
        break;
      }
      case DATETIME: {
        final List<LocalDateTime> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseDateTime(name, str));
        }
        parsedValue = result.toArray(new LocalDateTime[0]);
        value = formatDateTimes((LocalDateTime[]) parsedValue);
        break;
      }
      case TIMESTAMP: {
        final List<Timestamp> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseTimestamp(name, str));
        }
        parsedValue = result.toArray(new Timestamp[0]);
        value = formatTimestamps((Timestamp[]) parsedValue);
        break;
      }
      case BYTE_ARRAY: {
        final List<byte[]> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseByteArray(name, str));
        }
        parsedValue = result.toArray(new byte[0][]);
        value = formatByteArrays((byte[][]) parsedValue);
        break;
      }
      case CLASS:
        break;
      case BIG_INTEGER: {
        final List<BigInteger> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseBigInteger(name, str));
        }
        parsedValue = result.toArray(new BigInteger[0]);
        value = formatBigIntegers((BigInteger[]) parsedValue);
        break;
      }
      case BIG_DECIMAL: {
        final List<BigDecimal> result = new ArrayList<>();
        for (final String str : list) {
          result.add(parseBigDecimal(name, str));
        }
        parsedValue = result.toArray(new BigDecimal[0]);
        value = formatBigDecimals((BigDecimal[]) parsedValue);
        break;
      }
      default:
        throw new UnsupportedDataTypeException(type);
    }
    count = list.size();
  }

  /**
   * Parses the string representation of a {@code boolean} value to a {@code
   * Boolean} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code boolean} value, which may be
   *     {@code null}.
   * @return the corresponds {@code Boolean} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Boolean parseBool(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final BooleanFormat bf = new BooleanFormat();
    final boolean value = bf.parse(str);
    if (!bf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Boolean} object to its string representation.
   *
   * @param value
   *     a {@code Boolean} object, which may be {@code null}.
   * @return the string representation of the {@code Boolean} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatBool(@Nullable final Boolean value) {
    if (value == null) {
      return null;
    } else {
      final BooleanFormat bf = new BooleanFormat();
      return bf.format(value);
    }
  }

  /**
   * Formats an array of {@code Boolean} objects to its string representation.
   *
   * @param values
   *     an array of {@code Boolean} objects.
   * @return the string representation of the {@code Boolean} objects.
   */
  private static String formatBools(final Boolean[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatBool(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code char} value to a {@code
   * Character} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code char} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Character} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws NullPointerException
   *     if {@code str} is {@code null}.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Character parseChar(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    if (str.length() != 1) {
      throw new InvalidSettingValueException(name, str);
    }
    return str.charAt(0);
  }

  /**
   * Formats a {@code Character} object to its string representation.
   *
   * @param value
   *     a {@code Character} object, which may be {@code null}.
   * @return the string representation of the {@code Character} object, or
   *     {@code null} if the object is {@code null}.
   */
  private static String formatChar(@Nullable final Character value) {
    if (value == null) {
      return null;
    } else {
      return String.valueOf(value);
    }
  }

  /**
   * Formats an array of {@code Character} objects to its string
   * representation.
   *
   * @param values
   *     an array of {@code Character} objects.
   * @return the string representation of the {@code Character} objects.
   */
  private static String formatChars(final Character[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatChar(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code byte} value to a {@code Byte}
   * object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code byte} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Byte} object, or {@code null} if the string
   *     represents a {@code null} value.
   * @throws NullPointerException
   *     if {@code str} is {@code null}.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Byte parseByte(final String name, @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final byte value = nf.parseByte(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Byte} object to its string representation.
   *
   * @param value
   *     a {@code Byte} object, which may be {@code null}.
   * @return the string representation of the {@code Byte} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatByte(@Nullable final Byte value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatByte(value);
    }
  }

  /**
   * Formats an array of {@code Byte} objects to its string representation.
   *
   * @param values
   *     an array of {@code Byte} objects.
   * @return the string representation of the {@code Byte} objects.
   */
  private static String formatBytes(final Byte[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatByte(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code short} value to a {@code Short}
   * object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code short} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Short} object, or {@code null} if the string
   *     represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Short parseShort(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final short value = nf.parseShort(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Short} object to its string representation.
   *
   * @param value
   *     a {@code Short} object, which may be {@code null}.
   * @return the string representation of the {@code Short} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatShort(@Nullable final Short value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatShort(value);
    }
  }

  /**
   * Formats an array of {@code Short} objects to its string representation.
   *
   * @param values
   *     an array of {@code Short} objects.
   * @return the string representation of the {@code Short} objects.
   */
  private static String formatShorts(final Short[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatShort(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code int} value to an {@code Integer}
   * object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code int} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Integer} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Integer parseInt(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final int value = nf.parseInt(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Integer} object to its string representation.
   *
   * @param value
   *     a {@code Integer} object, which may be {@code null}.
   * @return the string representation of the {@code Integer} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatInt(@Nullable final Integer value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatInt(value);
    }
  }

  /**
   * Formats an array of {@code Integer} objects to its string representation.
   *
   * @param values
   *     an array of {@code Integer} objects.
   * @return the string representation of the {@code Integer} objects.
   */
  private static String formatInts(final Integer[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatInt(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code long} value to a {@code Long}
   * object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code long} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Long} object, or {@code null} if the string
   *     represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Long parseLong(final String name, @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final long value = nf.parseLong(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Long} object to its string representation.
   *
   * @param value
   *     a {@code Long} object, which may be {@code null}.
   * @return the string representation of the {@code Long} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatLong(@Nullable final Long value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatLong(value);
    }
  }

  /**
   * Formats an array of {@code Long} objects to its string representation.
   *
   * @param values
   *     an array of {@code Long} objects.
   * @return the string representation of the {@code Long} objects.
   */
  private static String formatLongs(final Long[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatLong(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code float} value to a {@code Float}
   * object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code float} value, which may be {@code
   *     null}.
   * @return the corresponds {@code Float} object, or {@code null} if the string
   *     represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Float parseFloat(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final float value = nf.parseFloat(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Float} object to its string representation.
   *
   * @param value
   *     a {@code Float} object, which may be {@code null}.
   * @return the string representation of the {@code Float} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatFloat(@Nullable final Float value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatFloat(value);
    }
  }

  /**
   * Formats an array of {@code Float} objects to its string representation.
   *
   * @param values
   *     an array of {@code Float} objects.
   * @return the string representation of the {@code Float} objects.
   */
  private static String formatFloats(final Float[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatFloat(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code double} value to a {@code
   * Double} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code double} value, which may be
   *     {@code null}.
   * @return the corresponds {@code Double} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Double parseDouble(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final double value = nf.parseDouble(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code Double} object to its string representation.
   *
   * @param value
   *     a {@code Double} object, which may be {@code null}.
   * @return the string representation of the {@code Double} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatDouble(@Nullable final Double value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatDouble(value);
    }
  }

  /**
   * Formats an array of {@code Double} objects to its string representation.
   *
   * @param values
   *     an array of {@code Double} objects.
   * @return the string representation of the {@code Double} objects.
   */
  private static String formatDoubles(final Double[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatDouble(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code String} value to a {@code
   * String} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code String} value, which may be
   *     {@code null}.
   * @return the corresponds {@code String} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static String parseString(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    } else {
      return str;
    }
  }

  /**
   * Formats a {@code String} object to its string representation.
   *
   * @param value
   *     a {@code String} object, which may be {@code null}.
   * @return the string representation of the {@code String} object, or {@code
   *     null} if the object is {@code null}.
   */
  private static String formatString(@Nullable final String value) {
    return value;
  }

  /**
   * Formats an array of {@code String} values to its string representation.
   *
   * @param values
   *     an array of {@code String} values.
   * @return the string representation of the {@code String} values.
   */
  private static String formatStrings(final String[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(STRING_SEPARATOR);
      }
      final String str = values[i];
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code LocalDate} value to a {@code
   * LocalDate} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code LocalDate} value, which may be
   *     {@code null}.
   * @return the corresponds {@code LocalDate} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static LocalDate parseDate(final String name, final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    try {
      return LocalDate.parse(str);
    } catch (final DateTimeParseException e) {
      throw new InvalidSettingValueException(name, str);
    }
  }

  /**
   * Formats a {@code LocalDate} object to its string representation.
   *
   * @param value
   *     a {@code LocalDate} object, which may be {@code null}.
   * @return the string representation of the {@code LocalDate} object, or
   *     {@code null} if the object is {@code null}.
   */
  private static String formatDate(@Nullable final LocalDate value) {
    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * Formats an array of {@code LocalDate} values to its string representation.
   *
   * @param values
   *     an array of {@code LocalDate} values.
   * @return the string representation of the {@code LocalDate} values.
   */
  private static String formatDates(final LocalDate[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatDate(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code LocalTime} value to a {@code
   * LocalTime} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code LocalTime} value, which may be
   *     {@code null}.
   * @return the corresponds {@code LocalTime} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static LocalTime parseTime(final String name, final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    try {
      return LocalTime.parse(str);
    } catch (final DateTimeParseException e) {
      throw new InvalidSettingValueException(name, str);
    }
  }

  /**
   * Formats a {@code LocalTime} object to its string representation.
   *
   * @param value
   *     a {@code LocalTime} object, which may be {@code null}.
   * @return the string representation of the {@code LocalTime} object, or
   *     {@code null} if the object is {@code null}.
   */
  private static String formatTime(@Nullable final LocalTime value) {
    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * Formats an array of {@code LocalTime} values to its string representation.
   *
   * @param values
   *     an array of {@code LocalTime} values.
   * @return the string representation of the {@code LocalTime} values.
   */
  private static String formatTimes(final LocalTime[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatTime(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code LocalDateTime} value to a {@code
   * LocalDateTime} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code LocalDateTime} value, which may
   *     be {@code null}.
   * @return the corresponds {@code LocalDateTime} object, or {@code null} if
   *     the string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static LocalDateTime parseDateTime(final String name,
      final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    try {
      return LocalDateTime.parse(str);
    } catch (final DateTimeParseException e) {
      throw new InvalidSettingValueException(name, str);
    }
  }

  /**
   * Formats a {@code LocalDateTime} object to its string representation.
   *
   * @param value
   *     a {@code LocalDateTime} object, which may be {@code null}.
   * @return the string representation of the {@code LocalDateTime} object, or
   *     {@code null} if the object is {@code null}.
   */
  private static String formatDateTime(@Nullable final LocalDateTime value) {
    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * Formats an array of {@code LocalDateTime} values to its string
   * representation.
   *
   * @param values
   *     an array of {@code LocalDateTime} values.
   * @return the string representation of the {@code LocalDateTime} values.
   */
  private static String formatDateTimes(final LocalDateTime[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatDateTime(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code Timestamp} value to a {@code
   * Timestamp} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code Timestamp} value, which may be
   *     {@code null}.
   * @return the corresponds {@code Timestamp} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static Timestamp parseTimestamp(final String name, final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final DateFormat df = new DateFormat(DateUtils.DEFAULT_DATETIME_PATTERN,
        DateUtils.UTC);
    final Date date = df.parse(str);
    if (df.fail()) {
      throw new InvalidSettingValueException(name, str);
    }
    return new Timestamp(date.getTime());
  }

  /**
   * Formats a {@code Timestamp} object to its string representation.
   *
   * @param value
   *     a {@code Timestamp} object, which may be {@code null}.
   * @return the string representation of the {@code Timestamp} object, or
   *     {@code null} if the object is {@code null}.
   */
  private static String formatTimestamp(@Nullable final Timestamp value) {
    if (value == null) {
      return null;
    } else {
      final DateFormat df = new DateFormat(DateUtils.DEFAULT_DATETIME_PATTERN,
          DateUtils.UTC);
      return df.format(value);
    }
  }

  /**
   * Formats an array of {@code Timestamp} values to its string representation.
   *
   * @param values
   *     an array of {@code Timestamp} values.
   * @return the string representation of the {@code Timestamp} values.
   */
  private static String formatTimestamps(final Timestamp[] values) {
    final StringBuilder builder = new StringBuilder();
    final DateFormat df = new DateFormat(DateUtils.DEFAULT_DATETIME_PATTERN,
        DateUtils.UTC);
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = (values[i] == null ? null : df.format(values[i]));
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code byte[]} value to a {@code
   * byte[]} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code byte[]} value, which may be
   *     {@code null}.
   * @return the corresponds {@code byte[]} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static byte[] parseByteArray(final String name, final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    if (str.length() == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final HexCodec codec = new HexCodec();
    final byte[] value = codec.decode(str);
    if (!codec.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code byte[]} value to its string representation.
   *
   * @param value
   *     a {@code byte[]} value
   * @return the string representation of the {@code byte[]} value, or {@code
   *     null} if the {@code value} is {@code null}.
   */
  private static String formatByteArray(@Nullable final byte[] value) {
    if (value == null) {
      return null;
    } else {
      final HexCodec codec = new HexCodec();
      return codec.encode(value);
    }
  }

  /**
   * Formats an array of {@code byte} values to its string representation.
   *
   * @param values
   *     an array of {@code byte} values.
   * @return the string representation of the {@code byte} values.
   */
  private static String formatByteArrays(final byte[][] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatByteArray(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code BigInteger} value to a {@code
   * BigInteger} object.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code BigInteger} value, which may be
   *     {@code null}.
   * @return the corresponds {@code BigInteger} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static BigInteger parseBigInteger(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final BigInteger value = nf.parseBigInteger(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code BigInteger} value to its string representation.
   *
   * @param value
   *     a {@code BigInteger} value
   * @return the string representation of the {@code BigInteger} value, or
   *     {@code null} if the {@code value} is {@code null}.
   */
  private static String formatBigInteger(@Nullable final BigInteger value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatBigInteger(value);
    }
  }

  /**
   * Formats an array of {@code BigInteger} values to its string
   * representation.
   *
   * @param values
   *     an array of {@code BigInteger} values.
   * @return the string representation of the {@code BigInteger} values.
   */
  private static String formatBigIntegers(final BigInteger[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatBigInteger(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Parses the string representation of {@code BigDecimal} value to a {@code
   * BigDecimal} value.
   *
   * @param name
   *     the name of the setting.
   * @param str
   *     the string representation of a {@code BigDecimal} value, which may be
   *     {@code null}.
   * @return the corresponds {@code BigDecimal} object, or {@code null} if the
   *     string represents a {@code null} value.
   * @throws InvalidSettingValueException
   *     if the parsing failed.
   */
  private static BigDecimal parseBigDecimal(final String name,
      @Nullable final String str) {
    if ((str == null) || str.equals(NULL_STRING)) {
      return null;
    }
    final NumberFormat nf = new NumberFormat();
    final BigDecimal value = nf.parseBigDecimal(str);
    if (!nf.success()) {
      throw new InvalidSettingValueException(name, str);
    }
    return value;
  }

  /**
   * Formats a {@code BigDecimal} value to its string representation.
   *
   * @param value
   *     a {@code BigDecimal} value
   * @return the string representation of the {@code BigDecimal} value, or
   *     {@code null} if the {@code value} is {@code null}.
   */
  private static String formatBigDecimal(@Nullable final BigDecimal value) {
    if (value == null) {
      return null;
    } else {
      final NumberFormat nf = new NumberFormat();
      return nf.formatBigDecimal(value);
    }
  }

  /**
   * Formats an array of {@code BigDecimal} values to its string
   * representation.
   *
   * @param values
   *     an array of {@code BigDecimal} values.
   * @return the string representation of the {@code BigDecimal} values.
   */
  private static String formatBigDecimals(final BigDecimal[] values) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < values.length; ++i) {
      if (i > 0) {
        builder.append(NON_STRING_SEPARATOR).append(' ');
      }
      final String str = formatBigDecimal(values[i]);
      if (str == null) {
        builder.append(NULL_STRING);
      } else {
        builder.append(str);
      }
    }
    return builder.toString();
  }

  /**
   * Gets the number of values.
   *
   * @return the number of values.
   */
  @Computed("value")
  public final int getCount() {
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    return count;
  }

  /**
   * Tests whether this setting is empty or not.
   *
   * @return {@code true} if this setting is empty; {@code false} otherwise.
   */
  @Computed("value")
  public final boolean isEmpty() {
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    return (count == 0);
  }

  /**
   * Gets the first value of this setting as a {@code Boolean} value.
   *
   * @return the first value of this setting as a {@code Boolean} value, which
   *     may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BOOL}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Boolean getBool() throws TypeMismatchException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL, type);
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Boolean) parsedValue;
    } else {
      final Boolean[] values = (Boolean[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code boolean} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BOOL}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code boolean} value to be set to this setting, or {@code
   *     null} to set none.
   * @return this object.
   */
  public final Setting setBool(@Nullable final Boolean value) {
    type = Type.BOOL;
    parsedValue = value;
    this.value = formatBool(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code Boolean} value.
   *
   * @return the values of this setting as a {@code Boolean} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BOOL}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Boolean[] getBools() throws TypeMismatchException {
    if (type != Type.BOOL) {
      throw new TypeMismatchException(Type.BOOL, type);
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY;
    } else if (count == 1) {
      final Boolean val = (Boolean) parsedValue;
      return new Boolean[]{val};
    } else { // count > 1
      final Boolean[] values = (Boolean[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Boolean} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BOOL}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Boolean} objects to be set to this object.
   * @return this object.
   */
  public final Setting setBools(final Boolean[] values) {
    if (values.length == 0) {
      type = Type.BOOL;
      parsedValue = null;
      value = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BOOL;
      parsedValue = values[0];
      value = formatBool(values[0]);
      count = 1;
    } else {
      type = Type.BOOL;
      parsedValue = Assignment.clone(values);
      value = formatBools(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code boolean} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BOOL}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code boolean} values to be set to this object.
   * @return this object.
   */
  public final Setting setBools(final boolean... values) {
    return setBools(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code Character} value.
   *
   * @return the first value of this setting as a {@code Character} value, which
   *     may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#CHAR}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Character getChar() throws TypeMismatchException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Character) parsedValue;
    } else {
      final Character[] values = (Character[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code Character} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#CHAR}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code Character} value to be set to this setting.
   * @return this object.
   */
  public final Setting setChar(@Nullable final Character value) {
    type = Type.CHAR;
    parsedValue = value;
    this.value = formatChar(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code Character} value.
   *
   * @return the values of this setting as a {@code Character} array; or an
   *     empty array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#CHAR}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Character[] getChars() throws TypeMismatchException {
    if (type != Type.CHAR) {
      throw new TypeMismatchException(Type.CHAR.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
    } else if (count == 1) {
      final Character val = (Character) parsedValue;
      return new Character[]{val};
    } else { // count > 1
      final Character[] values = (Character[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Character} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#CHAR}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Character} objects to be set to this object.
   * @return this object.
   */
  public final Setting setChars(final Character[] values) {
    if (values.length == 0) {
      type = Type.CHAR;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.CHAR;
      parsedValue = values[0];
      value = formatChar(values[0]);
      count = 1;
    } else {
      type = Type.CHAR;
      parsedValue = Assignment.clone(values);
      value = formatChars(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code char} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#CHAR}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code char} values to be set to this object.
   * @return this object.
   */
  public final Setting setChars(final char... values) {
    return setChars(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code Byte} value.
   *
   * @return the first value of this setting as a {@code Byte} value, which may
   *     be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BYTE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Byte getByte() throws TypeMismatchException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Byte) parsedValue;
    } else {  // count > 1
      final Byte[] values = (Byte[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code byte} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BYTE}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code byte} value to be set to this setting.
   * @return this object.
   */
  public final Setting setByte(@Nullable final Byte value) {
    type = Type.BYTE;
    parsedValue = value;
    this.value = formatByte(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code byte} value.
   *
   * @return the values of this setting as a {@code byte} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BYTE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Byte[] getBytes() throws TypeMismatchException {
    if (type != Type.BYTE) {
      throw new TypeMismatchException(Type.BYTE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;
    } else if (count == 1) {
      final Byte val = (Byte) parsedValue;
      return new Byte[]{val};
    } else { // count > 1
      final Byte[] values = (Byte[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Byte} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BYTE}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Byte} objects to be set to this object.
   * @return this object.
   */
  public final Setting setBytes(final Byte[] values) {
    if (values.length == 0) {
      type = Type.BYTE;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BYTE;
      parsedValue = values[0];
      value = formatByte(values[0]);
      count = 1;
    } else {
      type = Type.BYTE;
      parsedValue = Assignment.clone(values);
      value = formatBytes(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code byte} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BYTE}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code byte} values to be set to this object.
   * @return this object.
   */
  public final Setting setBytes(final byte... values) {
    return setBytes(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code Short} value.
   *
   * @return the first value of this setting as a {@code Short} value, which may
   *     be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#SHORT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Short getShort() throws TypeMismatchException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Short) parsedValue;
    } else {
      final Short[] values = (Short[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code short} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#SHORT}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code short} value to be set to this setting.
   * @return this object.
   */
  public final Setting setShort(@Nullable final Short value) {
    type = Type.SHORT;
    parsedValue = value;
    this.value = formatShort(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code short} value.
   *
   * @return the values of this setting as a {@code short} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#SHORT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Short[] getShorts() throws TypeMismatchException {
    if (type != Type.SHORT) {
      throw new TypeMismatchException(Type.SHORT.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY;
    } else if (count == 1) {
      final Short val = (Short) parsedValue;
      return new Short[]{val};
    } else { // count > 1
      final Short[] values = (Short[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Short} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#SHORT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Short} objects to be set to this object.
   * @return this object.
   */
  public final Setting setShorts(final Short[] values) {
    if (values.length == 0) {
      type = Type.SHORT;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.SHORT;
      parsedValue = values[0];
      value = formatShort(values[0]);
      count = 1;
    } else {
      type = Type.SHORT;
      parsedValue = Assignment.clone(values);
      value = formatShorts(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code short} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#SHORT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code short} values to be set to this object.
   * @return this object.
   */
  public final Setting setShorts(final short... values) {
    return setShorts(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code Integer} value.
   *
   * @return the first value of this setting as a {@code Integer} value, which
   *     may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#INT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Integer getInt() throws TypeMismatchException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Integer) parsedValue;
    } else {  //  count > 1
      final Integer[] values = (Integer[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code int} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#INT}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code int} value to be set to this setting.
   * @return this object.
   */
  public final Setting setInt(@Nullable final Integer value) {
    type = Type.INT;
    parsedValue = value;
    this.value = formatInt(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code int} value.
   *
   * @return the values of this setting as a {@code int} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#INT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Integer[] getInts() throws TypeMismatchException {
    if (type != Type.INT) {
      throw new TypeMismatchException(Type.INT.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
    } else if (count == 1) {
      final Integer val = (Integer) parsedValue;
      return new Integer[]{val};
    } else { // count > 1
      final Integer[] values = (Integer[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code int} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#INT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Integer} objects to be set to this setting.
   * @return this object.
   */
  public final Setting setInts(final Integer[] values) {
    if (values.length == 0) {
      type = Type.INT;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.INT;
      parsedValue = values[0];
      value = formatInt(values[0]);
      count = 1;
    } else {
      type = Type.INT;
      parsedValue = Assignment.clone(values);
      value = formatInts(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code int} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#INT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code int} values to be set to this setting.
   * @return this object.
   */
  public final Setting setInts(final int... values) {
    return setInts(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code long} value.
   *
   * @return the first value of this setting as a {@code long} value, which may
   *     be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#LONG}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Long getLong() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Long) parsedValue;
    } else {
      final Long[] values = (Long[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code long} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#LONG}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code long} value to be set to this setting.
   * @return this object.
   */
  public final Setting setLong(@Nullable final Long value) {
    type = Type.LONG;
    parsedValue = value;
    this.value = formatLong(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code long} value.
   *
   * @return the values of this setting as a {@code long} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#LONG}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Long[] getLongs() throws TypeMismatchException {
    if (type != Type.LONG) {
      throw new TypeMismatchException(Type.LONG.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
    } else if (count == 1) {
      final Long val = (Long) parsedValue;
      return new Long[]{val};
    } else { // count > 1
      final Long[] values = (Long[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Long} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#LONG}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Long} objects to be set to this object.
   * @return this object.
   */
  public final Setting setLongs(final Long[] values) {
    if (values.length == 0) {
      type = Type.LONG;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.LONG;
      parsedValue = values[0];
      value = formatLong(values[0]);
      count = 1;
    } else {
      type = Type.LONG;
      parsedValue = Assignment.clone(values);
      value = formatLongs(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code long} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#LONG}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code long} values to be set to this object.
   * @return this object.
   */
  public final Setting setLongs(final long... values) {
    return setLongs(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code float} value.
   *
   * @return the first value of this setting as a {@code float} value, which may
   *     be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#FLOAT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Float getFloat() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT, type);
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Float) parsedValue;
    } else {
      final Float[] values = (Float[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code float} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#FLOAT}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code float} value to be set to this setting.
   * @return this object.
   */
  public final Setting setFloat(@Nullable final Float value) {
    type = Type.FLOAT;
    parsedValue = value;
    this.value = formatFloat(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code float} value.
   *
   * @return the values of this setting as a {@code float} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#FLOAT}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Float[] getFloats() throws TypeMismatchException {
    if (type != Type.FLOAT) {
      throw new TypeMismatchException(Type.FLOAT, type);
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY;
    } else if (count == 1) {
      final Float val = (Float) parsedValue;
      return new Float[]{val};
    } else { // count > 1
      final Float[] values = (Float[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code Float} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#FLOAT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code Float} objects to be set to this object.
   * @return this object.
   */
  public final Setting setFloats(final Float[] values) {
    if (values.length == 0) {
      type = Type.FLOAT;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.FLOAT;
      parsedValue = values[0];
      value = formatFloat(values[0]);
      count = 1;
    } else {
      type = Type.FLOAT;
      parsedValue = Assignment.clone(values);
      value = formatFloats(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code float} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#FLOAT}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code float} values to be set to this object.
   * @return this object.
   */
  public final Setting setFloats(final float... values) {
    return setFloats(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@code double} value.
   *
   * @return the first value of this setting as a {@code double} value, which
   *     may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DOUBLE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Double getDouble() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (Double) parsedValue;
    } else {
      final Double[] values = (Double[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@code double} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DOUBLE}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@code double} value to be set to this setting.
   * @return this object.
   */
  public final Setting setDouble(@Nullable final Double value) {
    type = Type.DOUBLE;
    parsedValue = value;
    this.value = formatDouble(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code double} value.
   *
   * @return the values of this setting as a {@code double} array; or an empty
   *     array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DOUBLE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Double[] getDoubles() throws TypeMismatchException {
    if (type != Type.DOUBLE) {
      throw new TypeMismatchException(Type.DOUBLE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY;
    } else if (count == 1) {
      final Double val = (Double) parsedValue;
      return new Double[]{val};
    } else { // count > 1
      final Double[] values = (Double[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@code double} objects to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DOUBLE}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code double} objects to be set to this object.
   * @return this object.
   */
  public final Setting setDoubles(final Double[] values) {
    if (values.length == 0) {
      type = Type.DOUBLE;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.DOUBLE;
      parsedValue = values[0];
      value = formatDouble(values[0]);
      count = 1;
    } else {
      type = Type.DOUBLE;
      parsedValue = Assignment.clone(values);
      value = formatDoubles(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@code double} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DOUBLE}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@code double} values to be set to this object.
   * @return this object.
   */
  public final Setting setDoubles(final double... values) {
    return setDoubles(ArrayUtils.toObject(values));
  }

  /**
   * Gets the first value of this setting as a {@link String} value.
   *
   * @return the first value of this setting as a {@link String} value, which
   *     may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#STRING}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final String getString() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING, type);
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (String) parsedValue;
    } else {
      final String[] values = (String[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link String} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#STRING}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@link String} value to be set to this setting, which may be
   *     null.
   * @return this object.
   */
  public final Setting setString(@Nullable final String value) {
    type = Type.STRING;
    parsedValue = value;
    this.value = formatString(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link String} value.
   *
   * @return the values of this setting as a {@link String} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#STRING}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final String[] getStrings() throws TypeMismatchException {
    if (type != Type.STRING) {
      throw new TypeMismatchException(Type.STRING.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else if (count == 1) {
      final String val = (String) parsedValue;
      return new String[]{val};
    } else { // count > 1
      final String[] values = (String[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link String} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#STRING}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@link String} values to be set to this setting, whose
   *     elements may be null.
   * @return this object.
   */
  public final Setting setStrings(final String... values) {
    if (values.length == 0) {
      type = Type.STRING;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.STRING;
      parsedValue = values[0];
      value = values[0];
      count = 1;
    } else {
      type = Type.STRING;
      parsedValue = Assignment.clone(values);
      value = formatStrings(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Sets {@link String} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#STRING}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the collection of new {@link String} values to be set to this setting,
   *     whose elements may be null.
   * @return this object.
   */
  public final Setting setStrings(final Collection<String> values) {
    return setStrings(values.toArray(new String[0]));
  }

  /**
   * Gets the first value of this setting as a {@link LocalDate} value.
   *
   * @return the first value of this setting as a {@link LocalDate} value, which
   *     may be {@code null}. Note that the returned object is the cloned copy
   *     of the first {@link LocalDate} object stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DATE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final LocalDate getDate() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalDate) parsedValue;
    } else {
      final LocalDate[] values = (LocalDate[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link Date} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DATE}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@link Date} value to be set to this setting, which may be
   *     null. Note that the cloned copy of this setting will be stored in this
   *     setting.
   * @return this object.
   */
  public final Setting setDate(final LocalDate value) {
    type = Type.DATE;
    parsedValue = value;
    this.value = formatDate(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link LocalDate} value.
   *
   * @return the values of this setting as a {@link LocalDate} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   *     Note that the objects in returned array is the cloned copies of the
   *     {@link LocalDate} objects stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DATE}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final LocalDate[] getDates() throws TypeMismatchException {
    if (type != Type.DATE) {
      throw new TypeMismatchException(Type.DATE.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATE_ARRAY;
    } else if (count == 1) {
      final LocalDate val = (LocalDate) parsedValue;
      return new LocalDate[]{val};
    } else { // count > 1
      final LocalDate[] values = (LocalDate[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link LocalDate} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DATE}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@link LocalDate} values to be set to this setting,
   *     whose elements may be null. Note that the cloned copy of this array
   *     will be stored in this setting.
   * @return this object.
   */
  public final Setting setDates(final LocalDate... values) {
    if (values.length == 0) {
      type = Type.DATE;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.DATE;
      parsedValue = values[0];
      value = formatDate(values[0]);
      count = 1;
    } else {
      type = Type.DATE;
      parsedValue = Assignment.clone(values);
      value = formatDates(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@link LocalTime} value.
   *
   * @return the first value of this setting as a {@link LocalTime} value, which
   *     may be {@code null}. Note that the returned object is the cloned copy
   *     of the first {@link LocalTime} object stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#TIME}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final LocalTime getTime() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.TIME) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalTime) parsedValue;
    } else {
      final LocalTime[] values = (LocalTime[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link LocalTime} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#TIME}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@link LocalTime} value to be set to this setting, which may be
   *     null. Note that the cloned copy of this setting will be stored in this
   *     setting.
   * @return this object.
   */
  public final Setting setTime(final LocalTime value) {
    type = Type.TIME;
    parsedValue = value;
    this.value = formatTime(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link LocalTime} value.
   *
   * @return the values of this setting as a {@link LocalTime} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   *     Note that the objects in returned array is the cloned copies of the
   *     {@link LocalTime} objects stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#TIME}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final LocalTime[] getTimes() throws TypeMismatchException {
    if (type != Type.TIME) {
      throw new TypeMismatchException(Type.TIME.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_TIME_ARRAY;
    } else if (count == 1) {
      final LocalTime val = (LocalTime) parsedValue;
      return new LocalTime[]{val};
    } else { // count > 1
      final LocalTime[] values = (LocalTime[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link LocalTime} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#TIME}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@link LocalTime} values to be set to this setting,
   *     whose elements may be null. Note that the cloned copy of this array
   *     will be stored in this setting.
   * @return this object.
   */
  public final Setting setTimes(final LocalTime... values) {
    if (values.length == 0) {
      type = Type.TIME;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.TIME;
      parsedValue = values[0];
      value = formatTime(values[0]);
      count = 1;
    } else {
      type = Type.TIME;
      parsedValue = Assignment.clone(values);
      value = formatTimes(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@link LocalDateTime} value.
   *
   * @return the first value of this setting as a {@link LocalDateTime} value,
   *     which may be {@code null}. Note that the returned object is the cloned
   *     copy of the first {@link LocalDateTime} object stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DATETIME}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final LocalDateTime getDateTime() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.DATETIME) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (LocalDateTime) parsedValue;
    } else {
      final LocalDateTime[] values = (LocalDateTime[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link LocalDateTime} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DATETIME}, all previous values of this setting is cleared, and the new
   * value is set to this setting.
   *
   * @param value
   *     the new {@link LocalDateTime} value to be set to this setting, which
   *     may be null. Note that the cloned copy of this setting will be stored
   *     in this setting.
   * @return this object.
   */
  public final Setting setDateTime(final LocalDateTime value) {
    type = Type.DATETIME;
    parsedValue = value;
    this.value = formatDateTime(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link LocalDateTime} value.
   *
   * @return the values of this setting as a {@link LocalDateTime} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   *     Note that the objects in returned array is the cloned copies of the
   *     {@link LocalDateTime} objects stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#DATETIME}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final LocalDateTime[] getDateTimes() throws TypeMismatchException {
    if (type != Type.DATETIME) {
      throw new TypeMismatchException(Type.DATETIME.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_LOCAL_DATETIME_ARRAY;
    } else if (count == 1) {
      final LocalDateTime val = (LocalDateTime) parsedValue;
      return new LocalDateTime[]{val};
    } else { // count > 1
      final LocalDateTime[] values = (LocalDateTime[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link LocalDateTime} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#DATETIME}, all previous values of this setting is cleared, and the new
   * values is set to this setting.
   *
   * @param values
   *     the array of new {@link LocalDateTime} values to be set to this
   *     setting, whose elements may be null. Note that the cloned copy of this
   *     array will be stored in this setting.
   * @return this object.
   */
  public final Setting setDateTimes(final LocalDateTime... values) {
    if (values.length == 0) {
      type = Type.DATETIME;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.DATETIME;
      parsedValue = values[0];
      value = formatDateTime(values[0]);
      count = 1;
    } else {
      type = Type.DATETIME;
      parsedValue = Assignment.clone(values);
      value = formatDateTimes(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@link Timestamp} value.
   *
   * @return the first value of this setting as a {@link Timestamp} value, which
   *     may be {@code null}. Note that the returned object is the cloned copy
   *     of the first {@link Timestamp} object stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#TIMESTAMP}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final Timestamp getTimestamp() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.TIMESTAMP) {
      throw new TypeMismatchException(Type.TIMESTAMP.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      final Timestamp val = (Timestamp) parsedValue;
      return Assignment.clone(val);
    } else {
      final Timestamp[] values = (Timestamp[]) parsedValue;
      assert (values != null && values.length > 0);
      return Assignment.clone(values[0]);
    }
  }

  /**
   * Sets a single {@link Timestamp} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#TIMESTAMP}, all previous values of this setting is cleared, and the
   * new value is set to this setting.
   *
   * @param value
   *     the new {@link Timestamp} value to be set to this setting, which may be
   *     null. Note that the cloned copy of this setting will be stored in this
   *     setting.
   * @return this object.
   */
  public final Setting setTimestamp(final Timestamp value) {
    type = Type.TIMESTAMP;
    parsedValue = Assignment.clone(value);
    this.value = formatTimestamp(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link Timestamp} value.
   *
   * @return the values of this setting as a {@link Timestamp} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   *     Note that the objects in returned array is the cloned copies of the
   *     {@link Timestamp} objects stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#TIMESTAMP}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final Timestamp[] getTimestamps() throws TypeMismatchException {
    if (type != Type.TIMESTAMP) {
      throw new TypeMismatchException(Type.TIMESTAMP.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_TIMESTAMP_ARRAY;
    } else if (count == 1) {
      final Timestamp val = (Timestamp) parsedValue;
      return new Timestamp[]{Assignment.clone(val)};
    } else { // count > 1
      final Timestamp[] values = (Timestamp[]) parsedValue;
      return Assignment.deepClone(values);
    }
  }

  /**
   * Sets {@link Timestamp} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#TIMESTAMP}, all previous values of this setting is cleared, and the
   * new values is set to this setting.
   *
   * @param values
   *     the array of new {@link Timestamp} values to be set to this setting,
   *     whose elements may be null. Note that the cloned copy of this array
   *     will be stored in this setting.
   * @return this object.
   */
  public final Setting setTimestamps(final Timestamp... values) {
    if (values.length == 0) {
      type = Type.TIMESTAMP;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.TIMESTAMP;
      parsedValue = Assignment.clone(values[0]);
      value = formatTimestamp(values[0]);
      count = 1;
    } else {
      type = Type.TIMESTAMP;
      parsedValue = Assignment.deepClone(values);
      value = formatTimestamps(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@link BigInteger} value.
   *
   * @return the first value of this setting as a {@link BigInteger} value,
   *     which may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BIG_INTEGER}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final BigInteger getBigInteger() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (BigInteger) parsedValue;
    } else {
      final BigInteger[] values = (BigInteger[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link BigInteger} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BIG_INTEGER}, all previous values of this setting is cleared, and the
   * new value is set to this setting.
   *
   * @param value
   *     the new {@link BigInteger} value to be set to this setting, which may
   *     be null.
   * @return this object.
   */
  public final Setting setBigInteger(final BigInteger value) {
    type = Type.BIG_INTEGER;
    parsedValue = value;
    this.value = formatBigInteger(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link BigInteger} value.
   *
   * @return the values of this setting as a {@link BigInteger} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BIG_INTEGER}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final BigInteger[] getBigIntegers() throws TypeMismatchException {
    if (type != Type.BIG_INTEGER) {
      throw new TypeMismatchException(Type.BIG_INTEGER.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_INTEGER_ARRAY;
    } else if (count == 1) {
      final BigInteger val = (BigInteger) parsedValue;
      return new BigInteger[]{val};
    } else { // count > 1
      final BigInteger[] values = (BigInteger[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link BigInteger} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BIG_INTEGER}, all previous values of this setting is cleared, and the
   * new values is set to this setting.
   *
   * @param values
   *     the array of new {@link BigInteger} values to be set to this object,
   *     whose elements may be null.
   * @return this object.
   */
  public final Setting setBigIntegers(final BigInteger... values) {
    if (values.length == 0) {
      type = Type.BIG_INTEGER;
      parsedValue = null;
      count = 1;
    } else if (values.length == 1) {
      type = Type.BIG_INTEGER;
      parsedValue = values[0];
      value = formatBigInteger(values[0]);
      count = 1;
    } else {
      type = Type.BIG_INTEGER;
      parsedValue = Assignment.clone(values);
      value = formatBigIntegers(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@link BigDecimal} value.
   *
   * @return the first value of this setting as a {@link BigDecimal} value,
   *     which may be {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BIG_DECIMAL}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final BigDecimal getBigDecimal() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (BigDecimal) parsedValue;
    } else {
      final BigDecimal[] values = (BigDecimal[]) parsedValue;
      assert (values != null && values.length > 0);
      return values[0];
    }
  }

  /**
   * Sets a single {@link BigDecimal} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BIG_DECIMAL}, all previous values of this setting is cleared, and the
   * new value is set to this setting.
   *
   * @param value
   *     the new {@link BigDecimal} value to be set to this setting, which may
   *     be null.
   * @return this object.
   */
  public final Setting setBigDecimal(final BigDecimal value) {
    type = Type.BIG_DECIMAL;
    parsedValue = value;
    this.value = formatBigDecimal(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@link BigDecimal} value.
   *
   * @return the values of this setting as a {@link BigDecimal} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BIG_DECIMAL}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final BigDecimal[] getBigDecimals() throws TypeMismatchException {
    if (type != Type.BIG_DECIMAL) {
      throw new TypeMismatchException(Type.BIG_DECIMAL.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BIG_DECIMAL_ARRAY;
    } else if (count == 1) {
      final BigDecimal val = (BigDecimal) parsedValue;
      return new BigDecimal[]{val};
    } else { // count > 1
      final BigDecimal[] values = (BigDecimal[]) parsedValue;
      return Assignment.clone(values);
    }
  }

  /**
   * Sets {@link BigDecimal} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BIG_DECIMAL}, all previous values of this setting is cleared, and the
   * new values is set to this setting.
   *
   * @param values
   *     the array of new {@link BigDecimal} values to be set to this object,
   *     whose elements may be null.
   * @return this object.
   */
  public final Setting setBigDecimals(final BigDecimal... values) {
    if (values.length == 0) {
      type = Type.BIG_DECIMAL;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BIG_DECIMAL;
      parsedValue = values[0];
      value = formatBigDecimal(values[0]);
      count = 1;
    } else {
      type = Type.BIG_DECIMAL;
      parsedValue = Assignment.clone(values);
      value = formatBigDecimals(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the first value of this setting as a {@code byte[]} value.
   *
   * @return the first value of this setting as a {@code byte[]} value, which
   *     may be {@code null}. Note that the returned object is the cloned copy
   *     of the first {@code byte[]} object stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BYTE_ARRAY}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final byte[] getByteArray() throws TypeMismatchException,
      NoSuchElementException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      throw new NoSuchElementException();
    } else if (count == 1) {
      return (byte[]) parsedValue;
    } else {
      final byte[][] values = (byte[][]) parsedValue;
      assert (values != null && values.length > 0);
      return Assignment.clone(values[0]);
    }
  }

  /**
   * Sets a single {@code byte[]} value to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BYTE_ARRAY}, all previous values of this setting is cleared, and the
   * new value is set to this setting.
   *
   * @param value
   *     the new {@code byte[]} value to be set to this setting, which may be
   *     null. Note that the cloned copy of this setting will be stored in this
   *     setting.
   * @return this object.
   */
  public final Setting setByteArray(final byte[] value) {
    type = Type.BYTE_ARRAY;
    parsedValue = Assignment.clone(value);
    this.value = formatByteArray(value);
    count = 1;
    return this;
  }

  /**
   * Gets the values of this setting as a {@code byte[]} value.
   *
   * @return the values of this setting as a {@code byte[]} array, whose
   *     elements may be null; or an empty array if this setting has no value.
   *     Note that the objects in returned array is the cloned copies of the
   *     {@code byte[]} objects stored in this setting.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#BYTE_ARRAY}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final byte[][] getByteArrays() throws TypeMismatchException {
    if (type != Type.BYTE_ARRAY) {
      throw new TypeMismatchException(Type.BYTE_ARRAY.name(), type.name());
    }
    if ((parsedValue == null) && (value != null)) {
      parse();
    }
    if (count == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY_ARRAY;
    } else if (count == 1) {
      final byte[] val = (byte[]) parsedValue;
      return new byte[][]{Assignment.clone(val)};
    } else { // count > 1
      final byte[][] values = (byte[][]) parsedValue;
      return Assignment.deepClone(values);
    }
  }

  /**
   * Sets {@code byte[]} values to this setting.
   *
   * <p>After calling this function, the type of this setting is set to {@link
   * Type#BYTE_ARRAY}, all previous values of this setting is cleared, and the
   * new values is set to this setting.
   *
   * @param values
   *     the array of new {@code byte[]} values to be set to this object, whose
   *     elements may be null. Note that the cloned copy of this array will be
   *     stored in this setting.
   * @return this object.
   */
  public final Setting setByteArrays(final byte[]... values) {
    if (values.length == 0) {
      type = Type.BYTE_ARRAY;
      parsedValue = null;
      count = 0;
    } else if (values.length == 1) {
      type = Type.BYTE_ARRAY;
      parsedValue = Assignment.clone(values[0]);
      value = formatByteArray(values[0]);
      count = 1;
    } else {
      type = Type.BYTE_ARRAY;
      parsedValue = Assignment.deepClone(values);
      value = formatByteArrays(values);
      count = values.length;
    }
    return this;
  }

  /**
   * Gets the value of this setting as an enumeration object.
   *
   * <p>The value of this setting must be stored as a string, and must be the name
   * of an enumeration object.
   *
   * @param <E>
   *     the enumeration type.
   * @param enumType
   *     the class of the enumeration class.
   * @return the value of this setting as an enumeration object, which may be
   *     {@code null}.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#STRING}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   * @throws NoSuchElementException
   *     if there is no value set to this setting.
   */
  @Computed({"type", "value"})
  public final <E extends Enum<E>> E getEnum(final Class<E> enumType)
      throws TypeMismatchException, NoSuchElementException,
      IllegalArgumentException {
    final String val = getString();
    if (val == null) {
      return null;
    } else {
      try {
        return Enum.valueOf(enumType, val);
      } catch (final IllegalArgumentException e) {
        throw new InvalidSettingValueException(name, val);
      }
    }
  }

  /**
   * Sets the value of this setting as an enumeration object.
   *
   * <p>The value of this setting will be stored as the name of the enumeration
   * object.
   *
   * @param <E>
   *     the enumeration type.
   * @param value
   *     the enumeration object to set as the new value, which cannot be {@code
   *     null}.
   * @return this object.
   * @throws NullPointerException
   *     if {@code value} is {@code null}.
   */
  public final <E extends Enum<E>> Setting setEnum(final E value) {
    setString(value.name());
    return this;
  }

  /**
   * Gets the values of this setting as enumeration objects.
   *
   * <p>The values of this setting must be stored as strings, and must be the
   * names of enumeration objects.
   *
   * @param <E>
   *     the type of the enumeration class.
   * @param enumType
   *     the class of the enumeration class.
   * @return the list of values of this setting as enumeration objects.
   * @throws TypeMismatchException
   *     if the type of this setting is not {@link Type#STRING}.
   * @throws InvalidSettingValueException
   *     if the string representation of the value set to this setting is
   *     invalid.
   */
  @Computed({"type", "value"})
  public final <E extends Enum<E>> List<E> getEnums(final Class<E> enumType)
      throws TypeMismatchException, NoSuchElementException,
      IllegalArgumentException {
    final String[] values = getStrings();
    final List<E> result = new ArrayList<>();
    for (final String val : values) {
      if (val != null) {
        try {
          final E e = Enum.valueOf(enumType, val);
          result.add(e);
        } catch (final IllegalArgumentException e) {
          throw new InvalidSettingValueException(name, val);
        }
      }
    }
    return result;
  }

  /**
   * Sets the values of this setting as enumeration objects.
   *
   * <p>The values of this setting will be stored as the names of the
   * enumeration objects.
   *
   * @param <E>
   *     the type of the enumeration class.
   * @param values
   *     the enumeration objects to set as the new values, whose elements cannot
   *     be {@code null}.
   * @return this object.
   * @throws NullPointerException
   *     if any element of {@code values} is {@code null}.
   */
  @SafeVarargs
  public final <E extends Enum<E>> Setting setEnums(final E... values) {
    final String[] names = new String[values.length];
    for (int i = 0; i < values.length; ++i) {
      names[i] = values[i].name();
    }
    setStrings(names);
    return this;
  }

  //  /**
  //   * Sets the values of this setting as enumeration objects.
  //   * <p>
  //   * The values of this setting will be stored as the names of the enumeration
  //   * objects.
  //   *
  //   * @param values
  //   *          the collection of enumeration objects to set as the new values,
  //   *          whose elements cannot be {@code null}.
  //   * @return this object.
  //   * @throws NullPointerException
  //   *           if any element of {@code values} is {@code null}.
  //   */
  //  public final <E extends Enum<E>> Setting setEnums(final Collection<E> values) {
  //    final int n = values.size();
  //    final String[] names = new String[n];
  //    int i = 0;
  //    for (final E e : values) {
  //      names[i++] = e.name();
  //    }
  //    setStrings(names);
  //    return this;
  //  }

  /**
   * Get the creating time of this setting.
   *
   * @return the creating time of this setting.
   */
  public final Instant getCreateTime() {
    return createTime;
  }

  /**
   * Sets the creating time of this setting.
   *
   * @param createTime
   *     the new creating time.
   */
  public final void setCreateTime(@Nullable final Instant createTime) {
    this.createTime = createTime;
  }

  /**
   * Get the last modify time of this setting.
   *
   * @return the last modify time of this setting.
   */
  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  /**
   * Sets the last modify time of this setting.
   *
   * @param modifyTime
   *     the new last modify time.
   */
  public final void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  /**
   * Tests whether this setting is valid.
   *
   * @return {@code true} if this setting is valid; {@code false} otherwise.
   */
  @Computed({"value", "nullable", "multiple"})
  public boolean isValid() {
    //  validate the type and values
    if ((value != null) && (parsedValue == null)) {
      try {
        parse();
      } catch (final InvalidSettingValueException e) {
        parsedValue = null;
        return false;
      }
    }
    //  validate the nullable
    if ((!nullable) && (value == null)) {
      return false;
    }
    //  validate the multiple
    return !((!multiple) && (count > 1));
  }

  /**
   * Compares this {@link Setting} to another {@link Setting}.
   *
   * <p>Comparing two {@link Setting}s will compare their names <b>ignoring the
   * case</b>.
   *
   * @param other
   *     the other {@link Setting} to be compared to this one.
   * @return 0 if the two {@link Setting}s are considered as equal; a positive
   *     integer if this {@link Setting} is considered as greater than the other
   *     one; and a negative integer if the this {@link Setting} is considered
   *     as less than the other one.
   */
  @Override
  public int compareTo(@Nullable final Setting other) {
    if (other == null) {
      return +1;
    } else if (name == null) {
      return (other.name == null ? 0 : -1);
    } else if (other.name == null) {
      return +1;
    } else {
      return name.compareToIgnoreCase(other.name);
    }
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Setting other = (Setting) o;
    return Equality.equals(readonly, other.readonly)
        && Equality.equals(nullable, other.nullable)
        && Equality.equals(multiple, other.multiple)
        && Equality.equals(encrypted, other.encrypted)
        && Equality.equals(name, other.name)
        && Equality.equals(type, other.type)
        && Equality.equals(description, other.description)
        && Equality.equals(value, other.value)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, readonly);
    result = Hash.combine(result, multiplier, nullable);
    result = Hash.combine(result, multiplier, multiple);
    result = Hash.combine(result, multiplier, encrypted);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("type", type)
        .append("readonly", readonly)
        .append("nullable", nullable)
        .append("multiple", multiple)
        .append("encrypted", encrypted)
        .append("description", description)
        .append("value", value)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .toString();
  }
}
