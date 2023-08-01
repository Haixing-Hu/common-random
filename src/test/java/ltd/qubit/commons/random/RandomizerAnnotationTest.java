////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import ltd.qubit.commons.random.annotation.Randomizer;
import ltd.qubit.commons.random.annotation.RandomizerArgument;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomizerAnnotationTest {

  @Test
  void fieldAnnotatedWithRandomizerShouldBePopulatedWithValuesGeneratedByTheDeclaredRandomizer() {
    final Foo foo = new EasyRandom().nextObject(Foo.class);
    assertThat(foo.getName()).isEqualTo("foo");
  }

  // https://github.com/j-easy/easy-random/issues/131
  @Test
  void shouldThrowObjectGenerationException() {
    assertThatThrownBy(() -> new EasyRandom().nextObject(Bar.class))
        .isInstanceOf(ObjectCreationException.class);
  }

  @Test
  void testRandomizerArgumentAsArray() {
    final Person person = new EasyRandom().nextObject(Person.class);

    assertThat(person.getName()).isIn("foo", "bar");
    assertThat(person.getAge()).isIn(1, 2, 3);
  }

  @Test
  void testRandomizerIsReused() {
    MyStringRandomizer.resetNumConstructorCalled();

    final EasyRandom easyRandom = new EasyRandom();

    final Person firstRandomPerson = easyRandom.nextObject(Person.class);
    final Person secondRandomPerson = easyRandom.nextObject(Person.class);

    // If the randomizer would not be reused, then

    // The names would be equal, since the seed of MyStringRandomizer is a constant
    assertThat(firstRandomPerson.getName()).isNotEqualTo(secondRandomPerson.getName());

    // The constructor would have been called multiple times
    assertThat(MyStringRandomizer.getNumConstructorCalled()).isEqualTo(1);
  }

  static class Person {

    @Randomizer(
            value = MyStringRandomizer.class, args = {
            @RandomizerArgument(value = "123", type = long.class),
            @RandomizerArgument(value = "foo, bar", type = String[].class)
    })
    private String name;

    @Randomizer(
            value = MyNumbersRandomizer.class, args = {
            @RandomizerArgument(value = "1, 2, 3", type = Integer[].class)
    })
    private int age;

    public Person() {
    }

    public String getName() {
      return this.name;
    }

    public int getAge() {
      return this.age;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public void setAge(final int age) {
      this.age = age;
    }
  }

  public static class MyStringRandomizer extends AbstractRandomizer<String> {

    private final String[] words;
    private static int numConstructorCalled = 0;

    public MyStringRandomizer(final long seed, final String[] words) {
      super(seed);
      this.words = words;
      numConstructorCalled += 1;
    }

    @Override
    public String getRandomValue() {
      final int randomIndex = random.nextInt(words.length);
      return words[randomIndex];
    }

    static int getNumConstructorCalled() {
      return numConstructorCalled;
    }

    static void resetNumConstructorCalled() {
      numConstructorCalled = 0;
    }
  }

  public static class MyNumbersRandomizer extends AbstractRandomizer<Integer> {

    private final Integer[] numbers;

    public MyNumbersRandomizer(final Integer[] numbers) {
      this.numbers = numbers;
    }

    @Override
    public Integer getRandomValue() {
      final int randomIndex = random.nextInt(numbers.length);
      return numbers[randomIndex];
    }
  }

  private class Bar {
    @Randomizer(
            RandomizerWithoutDefaultConstrcutor.class)
    private String name;
  }

  public static class RandomizerWithoutDefaultConstrcutor implements
      ltd.qubit.commons.random.api.Randomizer<String> {

    public RandomizerWithoutDefaultConstrcutor(final int d) {
    }

    @Override
    public String getRandomValue() {
      return null;
    }
  }

  public static class DummyRandomizer implements
      ltd.qubit.commons.random.api.Randomizer<String> {

    @Override
    public String getRandomValue() {
      return "foo";
    }
  }

  private class Foo {
    @Randomizer(DummyRandomizer.class)
    private String name;

    public String getName() {
      return name;
    }

    @SuppressWarnings("unused")
    public void setName(final String name) {
      this.name = name;
    }
  }

}
