////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.util.Arrays;
import java.util.List;

import ltd.qubit.commons.annotation.Priority;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriorityComparatorTest {

  private PriorityComparator priorityComparator;

  private Foo foo;

  private Bar bar;

  @BeforeEach
  void setUp() {
    priorityComparator = new PriorityComparator();
    foo = new Foo();
    bar = new Bar();
  }

  @Test
  void testCompare() {
    assertThat(priorityComparator.compare(foo, bar)).isGreaterThan(0);

    final List<Object> objects = Arrays.asList(foo, bar);
    objects.sort(priorityComparator);
    // objects must be sorted in decreasing priority order: 2 > 1
    assertThat(objects).containsExactly(bar, foo);
  }

  @Priority(1)
  private class Foo {

  }

  @Priority(2)
  private class Bar {

  }
}
