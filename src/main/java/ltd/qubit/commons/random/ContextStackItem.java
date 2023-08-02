////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;

/**
 * Context object holding the data of a recursion step in {@link
 * EasyRandom#nextObject(Class)}.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class ContextStackItem {

  private Object object;

  private Field field;

  public ContextStackItem(final Object object, final Field field) {
    this.object = object;
    this.field = field;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(final Object object) {
    this.object = object;
  }

  public Field getField() {
    return field;
  }

  public void setField(final Field field) {
    this.field = field;
  }
}
