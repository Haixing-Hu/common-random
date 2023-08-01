////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

/**
 * Exception thrown when Easy Random is unable to create an instance of a given
 * type.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ObjectCreationException extends RuntimeException {

  private static final long serialVersionUID = -9049778508557432869L;

  public ObjectCreationException(final String message) {
    super(message);
  }

  public ObjectCreationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
