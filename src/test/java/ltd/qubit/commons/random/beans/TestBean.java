////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

public class TestBean {
  private Exception exception; // contains a field of type Class via StackTraceElement
  private Class<?> clazz;

  public Exception getException() {
    return exception;
  }

  public void setException(final Exception exception) {
    this.exception = exception;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(final Class<?> clazz) {
    this.clazz = clazz;
  }
}
