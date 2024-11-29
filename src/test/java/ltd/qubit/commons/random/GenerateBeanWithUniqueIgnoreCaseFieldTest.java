////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.beans.BeanWithUniqueIgnoreCaseField;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GenerateBeanWithUniqueIgnoreCaseFieldTest {

  private static final int LOOPS = 1000000;

  @Test
  public void testIgnoreCase() {
    final RandomBeanGenerator generator = new RandomBeanGenerator();
    final Set<String> codeSet = new HashSet<>();
    for (int i = 0; i < LOOPS; ++i) {
      final BeanWithUniqueIgnoreCaseField bean = generator.nextObject(BeanWithUniqueIgnoreCaseField.class);
      final String code = bean.getCode();
      assertFalse(codeSet.contains(code.toUpperCase()), "Duplicate code: " + code);
      codeSet.add(code.toUpperCase());
    }
  }

}
