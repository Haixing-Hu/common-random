////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.parameters;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomizerProviderTests {

  static class CustomizedRandomizerProvider implements RandomizerProvider {

    private Set<RandomizerRegistry> registries;

    public CustomizedRandomizerProvider clone() {
      Set<RandomizerRegistry> newRegistries = null;
      if (registries != null) {
        newRegistries = new HashSet<>(registries);
      }
      return new CustomizedRandomizerProvider().addRegistries(newRegistries);
    }

    @Override
    public CustomizedRandomizerProvider addRegistries(
            final Set<RandomizerRegistry> registries) {
      this.registries = registries;
      // may sort registries with a custom sort algorithm (ie, not necessarily with `@Priority`)
      return this;
    }

    @Override
    public CustomizedRandomizerProvider removeRegistry(
            final Class<? extends RandomizerRegistry> registryClass) {
      registries.removeIf((registry) -> registry.getClass() == registryClass);
      return this;
    }

    @Override
    public Randomizer<?> getByField(final Field field, final Context context) {
      // return custom randomizer based on the context
      if (context.getCurrentFieldPath().equals("name")) {
        return () -> "foo";
      }
      if (context.getCurrentFieldPath().equals("bestFriend.name")) {
        return () -> "bar";
      }
      return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Randomizer<T> getByType(final Class<T> type, final Context context) {
      for (final RandomizerRegistry registry : registries) {
        final Randomizer<?> randomizer = registry.get(type, context);
        if (randomizer != null) {
          return (Randomizer<T>) randomizer;
        }
      }
      return null;
    }
  }

  @Test
  void testCustomRandomizerProvider() {
    // given
    final Parameters parameters = new Parameters()
            .randomizerProvider(new CustomizedRandomizerProvider())
            .randomizationDepth(2);
    final EasyRandom easyRandom = new EasyRandom(parameters);
    // when
    final Foo foo = easyRandom.nextObject(Foo.class);

    // then
    assertThat(foo).isNotNull();
    assertThat(foo.getName()).isEqualTo("foo");
    assertThat(foo.getBestFriend().getName()).isEqualTo("bar");
    assertThat(foo.getBestFriend().getBestFriend().getName()).isNull();
  }

  static class Foo {
    private String name;
    private Foo bestFriend;

    public Foo() {
    }

    public String getName() {
      return this.name;
    }

    public Foo getBestFriend() {
      return this.bestFriend;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setBestFriend(final Foo bestFriend) {
      this.bestFriend = bestFriend;
    }
  }
}
