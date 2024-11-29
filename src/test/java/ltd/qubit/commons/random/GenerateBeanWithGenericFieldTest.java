////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateBeanWithGenericFieldTest {

  public static class Wrapper<T> {
    public T data;
    public int value = 0;
  }

  public static class Foo {
    public int id = 1;
    public String name = "abc";
  }

  public static class Goo extends Wrapper<Foo> {
    public String code = "goo";
  }

  public static class Too extends Goo {
    // empty
  }

  @Test
  public void testGenerateBeanWithGenericField() {
    final RandomBeanGenerator random = new RandomBeanGenerator();
    final Goo goo = random.nextObject(Goo.class);
    assertNotNull(goo.data);
    assertInstanceOf(Foo.class, goo.data);

    final Too too = random.nextObject(Too.class);
    assertNotNull(too.data);
    assertInstanceOf(Foo.class, too.data);

  }
}
