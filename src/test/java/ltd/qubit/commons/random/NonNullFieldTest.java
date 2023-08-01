////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Field;
import java.util.HashSet;

import ltd.qubit.commons.random.beans.person.User;
import ltd.qubit.commons.reflect.FieldUtils;
import ltd.qubit.commons.reflect.Option;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NonNullFieldTest {

  static final int TEST_COUNT = 10000;

  @Test
  public void testSetNonNullField() {
    final RandomBeanGenerator g = new RandomBeanGenerator();
    final Field emailField = FieldUtils.getField(User.class, Option.BEAN_FIELD, "email");
    g.getParameters().addNonNullField(emailField);
    final HashSet<String> keys = new HashSet<>();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final User user = g.nextObject(User.class);
      assertNotNull(user.getEmail());
    }
  }
}
