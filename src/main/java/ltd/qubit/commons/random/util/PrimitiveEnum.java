////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

/**
 * Wrapper for primitive TYPE values and their classes.
 *
 * @author Sam Van Overmeire
 */
enum PrimitiveEnum {

  BYTE(Byte.TYPE, Byte.class),
  SHORT(Short.TYPE, Short.class),
  INTEGER(Integer.TYPE, Integer.class),
  LONG(Long.TYPE, Long.class),
  FLOAT(Float.TYPE, Float.class),
  DOUBLE(Double.TYPE, Double.class),
  BOOLEAN(Boolean.TYPE, Boolean.class),
  CHARACTER(Character.TYPE, Character.class);

  private final Class<?> type;
  private final Class<?> clazz;

  PrimitiveEnum(final Class<?> type, final Class<?> clazz) {
    this.type = type;
    this.clazz = clazz;
  }

  public Class<?> getType() {
    return type;
  }

  public Class<?> getClazz() {
    return clazz;
  }
}
