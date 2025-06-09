////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

/**
 * 原始TYPE值及其类的包装器。
 *
 * @author 胡海星
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

  /**
   * 创建一个新的{@link PrimitiveEnum}。
   *
   * @param type
   *     原始类型。
   * @param clazz
   *     包装类。
   */
  PrimitiveEnum(final Class<?> type, final Class<?> clazz) {
    this.type = type;
    this.clazz = clazz;
  }

  /**
   * 获取原始类型。
   *
   * @return 原始类型。
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * 获取包装类。
   *
   * @return 包装类。
   */
  public Class<?> getClazz() {
    return clazz;
  }
}
