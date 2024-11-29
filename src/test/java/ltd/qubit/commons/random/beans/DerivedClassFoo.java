////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

import jakarta.validation.constraints.Email;

public class DerivedClassFoo extends BaseClassWithFinalField {

  @Email
  private String email;

  public DerivedClassFoo() {
    super("Foo");
  }

  public final String getEmail() {
    return email;
  }

  public final DerivedClassFoo setEmail(final String email) {
    this.email = email;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final DerivedClassFoo that = (DerivedClassFoo) o;
    return Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DerivedClassFoo.class.getSimpleName() + "[", "]")
            .add("name='" + getName() + "'")
            .add("email='" + email + "'")
            .toString();
  }
}
