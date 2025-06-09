////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

/**
 * 当Easy Random无法创建给定类型的实例时抛出此异常。
 *
 * @author 胡海星
 */
public class ObjectCreationException extends RuntimeException {

  private static final long serialVersionUID = -9049778508557432869L;

  /**
   * 创建一个新的{@link ObjectCreationException}。
   *
   * @param message
   *     异常消息。
   */
  public ObjectCreationException(final String message) {
    super(message);
  }

  /**
   * 创建一个新的{@link ObjectCreationException}。
   *
   * @param message
   *     异常消息。
   * @param cause
   *     异常原因。
   */
  public ObjectCreationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
