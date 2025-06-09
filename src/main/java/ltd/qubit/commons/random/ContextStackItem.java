////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;

/**
 * 上下文对象，持有{@link EasyRandom#nextObject(Class)}中递归步骤的数据。
 *
 * @author 胡海星
 */
public class ContextStackItem {

  private Object object;

  private Field field;

  /**
   * 创建一个{@link ContextStackItem}。
   *
   * @param object
   *     当前正在填充的对象。
   * @param field
   *     当前正在填充的字段。
   */
  public ContextStackItem(final Object object, final Field field) {
    this.object = object;
    this.field = field;
  }

  /**
   * 获取当前正在填充的对象。
   *
   * @return 当前正在填充的对象。
   */
  public Object getObject() {
    return object;
  }

  /**
   * 设置当前正在填充的对象。
   *
   * @param object
   *     当前正在填充的对象。
   */
  public void setObject(final Object object) {
    this.object = object;
  }

  /**
   * 获取当前正在填充的字段。
   *
   * @return 当前正在填充的字段。
   */
  public Field getField() {
    return field;
  }

  /**
   * 设置当前正在填充的字段。
   *
   * @param field
   *     当前正在填充的字段。
   */
  public void setField(final Field field) {
    this.field = field;
  }
}
