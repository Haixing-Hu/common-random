////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import ltd.qubit.commons.random.beans.Address;
import ltd.qubit.commons.random.beans.Website;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.TypePredicates.inPackage;
import static ltd.qubit.commons.random.TypePredicates.isAbstract;
import static ltd.qubit.commons.random.TypePredicates.isInterface;

import static org.assertj.core.api.Assertions.assertThat;

class TypeExclusionTest {

  @Test
  void testTypeExclusion() {
    // given
    final Parameters parameters = new Parameters()
            .excludeType(inPackage("ltd.qubit.commons.random.beans")
                .or(isInterface()).or(isAbstract()));
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Foo foo = easyRandom.nextObject(Foo.class);

    // then
    assertThat(foo).isNotNull();
    // types from "org.jeasy.random.beans" package should be excluded
    assertThat(foo.getAddress()).isNull();
    assertThat(foo.getWebsite()).isNull();
    // abstract types should not be randomized
    assertThat(foo.getBar()).isNull();
    assertThat(foo.getBaz()).isNull();
  }

  static class Foo {
    private String name;
    private Address address;
    private Website website;
    private Bar bar;
    private Baz baz;

    public Foo() {
    }

    public String getName() {
      return this.name;
    }

    public Address getAddress() {
      return this.address;
    }

    public Website getWebsite() {
      return this.website;
    }

    public Bar getBar() {
      return this.bar;
    }

    public Baz getBaz() {
      return this.baz;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setAddress(final Address address) {
      this.address = address;
    }

    public void setWebsite(final Website website) {
      this.website = website;
    }

    public void setBar(final Bar bar) {
      this.bar = bar;
    }

    public void setBaz(final Baz baz) {
      this.baz = baz;
    }
  }

  interface Bar {
  }

  abstract class Baz {
  }
}
