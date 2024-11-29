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
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.random.beans.Organization;
import ltd.qubit.commons.random.beans.Payload;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UniqueAnnotatedFieldTest {

  static final int TEST_COUNT = 10000;
  static final double EPSILON = 0.05;

  @Disabled
  @Test
  public void testGenerateOrganization() {
    final RandomBeanGenerator g = new RandomBeanGenerator();
    final HashSet<String> keys = new HashSet<>();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final Organization o = g.nextObject(Organization.class);
      final List<Payload> payloads = o.getPayloads();
      if (payloads != null) {
        for (Payload p : payloads) {
          System.out.println("Generate a payload with key: " + p.getKey());
          assertFalse(keys.contains(p.getKey()));
          keys.add(p.getKey());
        }
      }
    }
  }
}
