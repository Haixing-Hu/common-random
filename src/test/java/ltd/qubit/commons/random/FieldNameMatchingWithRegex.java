////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.FieldPredicates.named;
import static ltd.qubit.commons.random.FieldPredicates.ofType;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldNameMatchingWithRegex {

  public class Foo {
    private String name1;
    private String name2;
    private String nickname;
    private String job;
    private Bar bar;

    public Foo() {
    }

    public String getName1() {
      return this.name1;
    }

    public String getName2() {
      return this.name2;
    }

    public String getNickname() {
      return this.nickname;
    }

    public String getJob() {
      return this.job;
    }

    public Bar getBar() {
      return this.bar;
    }

    public void setName1(final String name1) {
      this.name1 = name1;
    }

    public void setName2(final String name2) {
      this.name2 = name2;
    }

    public void setNickname(final String nickname) {
      this.nickname = nickname;
    }

    public void setJob(final String job) {
      this.job = job;
    }

    public void setBar(final Bar bar) {
      this.bar = bar;
    }
  }

  public class Bar {
    private String name;
    private String address;

    public Bar() {
    }

    public String getName() {
      return this.name;
    }

    public String getAddress() {
      return this.address;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setAddress(final String address) {
      this.address = address;
    }
  }

  @Test
  void testFieldDefinitionWithNameAsRegexp() {
    // given
    final Parameters parameters = new Parameters()
            .randomize(named("name.*").and(ofType(String.class)), () -> "foo");
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Foo foo = easyRandom.nextObject(Foo.class);

    // then
    assertThat(foo.getName1()).isEqualTo("foo");
    assertThat(foo.getName2()).isEqualTo("foo");
    assertThat(foo.getBar().getName()).isEqualTo("foo");

    assertThat(foo.getNickname()).isNotEqualTo("foo");
    assertThat(foo.getJob()).isNotEqualTo("foo");
    assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
  }

  // non regression test
  @Test
  void testFieldDefinitionWithNameNotAsRegexp() {
    // given
    final Parameters parameters = new Parameters()
            .randomize(named("name").and(ofType(String.class)), () -> "foo");
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Foo foo = easyRandom.nextObject(Foo.class);

    // then
    assertThat(foo.getBar().getName()).isEqualTo("foo");

    assertThat(foo.getName1()).isNotEqualTo("foo");
    assertThat(foo.getName2()).isNotEqualTo("foo");
    assertThat(foo.getNickname()).isNotEqualTo("foo");
    assertThat(foo.getJob()).isNotEqualTo("foo");
    assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
  }
}
