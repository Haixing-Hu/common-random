////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers;

import java.text.DecimalFormatSymbols;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.faker.CityRandomizer;
import ltd.qubit.commons.random.randomizers.faker.CompanyRandomizer;
import ltd.qubit.commons.random.randomizers.faker.CountryRandomizer;
import ltd.qubit.commons.random.randomizers.faker.CreditCardNumberRandomizer;
import ltd.qubit.commons.random.randomizers.faker.EmailRandomizer;
import ltd.qubit.commons.random.randomizers.faker.FakerBasedRandomizer;
import ltd.qubit.commons.random.randomizers.faker.FirstNameRandomizer;
import ltd.qubit.commons.random.randomizers.faker.FullNameRandomizer;
import ltd.qubit.commons.random.randomizers.faker.Ipv4AddressRandomizer;
import ltd.qubit.commons.random.randomizers.faker.Ipv6AddressRandomizer;
import ltd.qubit.commons.random.randomizers.faker.IsbnRandomizer;
import ltd.qubit.commons.random.randomizers.faker.LastNameRandomizer;
import ltd.qubit.commons.random.randomizers.faker.LatitudeRandomizer;
import ltd.qubit.commons.random.randomizers.faker.LongitudeRandomizer;
import ltd.qubit.commons.random.randomizers.faker.MacAddressRandomizer;
import ltd.qubit.commons.random.randomizers.faker.ParagraphRandomizer;
import ltd.qubit.commons.random.randomizers.faker.PhoneNumberRandomizer;
import ltd.qubit.commons.random.randomizers.faker.RegularExpressionRandomizer;
import ltd.qubit.commons.random.randomizers.faker.SentenceRandomizer;
import ltd.qubit.commons.random.randomizers.faker.StateRandomizer;
import ltd.qubit.commons.random.randomizers.faker.StreetRandomizer;
import ltd.qubit.commons.random.randomizers.faker.WordRandomizer;
import ltd.qubit.commons.random.randomizers.faker.ZipCodeRandomizer;

import static org.assertj.core.api.BDDAssertions.then;

class RandomizersTest extends AbstractRandomizerTest<FakerBasedRandomizer<?>> {

  static Object[] generateRandomizers() {
    return new Object[]{
        new CityRandomizer(),
        new CompanyRandomizer(),
        new CountryRandomizer(),
        new CreditCardNumberRandomizer(),
        new EmailRandomizer(),
        new FirstNameRandomizer(),
        new FullNameRandomizer(),
        new Ipv4AddressRandomizer(),
        new Ipv6AddressRandomizer(),
        new IsbnRandomizer(),
        new LastNameRandomizer(),
        new LatitudeRandomizer(),
        new LongitudeRandomizer(),
        new MacAddressRandomizer(),
        new PhoneNumberRandomizer(),
        new RegularExpressionRandomizer("\\d+[A-Z]{5}"),
        new ParagraphRandomizer(),
        new SentenceRandomizer(),
        new StateRandomizer(),
        new StreetRandomizer(),
        new WordRandomizer(),
        new ZipCodeRandomizer()
    };
  }

  @ParameterizedTest
  @MethodSource("generateRandomizers")
  void generatedNumberShouldNotBeNull(final Randomizer<?> randomizer) {
    // when
    final Object randomNumber = randomizer.getRandomValue();

    then(randomNumber).isNotNull();
  }

  static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
    final char SEPARATOR = new DecimalFormatSymbols(FakerBasedRandomizer.DEFAULT_LOCALE).getDecimalSeparator();
    return new Object[][]{
        {new CityRandomizer(SEED), "Breannaberg"},
        {new CompanyRandomizer(SEED), "Hegmann, Hansen and Mills"},
        {new CountryRandomizer(SEED), "Peru"},
        {new CreditCardNumberRandomizer(SEED), "1211-1221-1234-2201"},
        {new EmailRandomizer(SEED), "jacob.hansen@hotmail.com"},
        {new FirstNameRandomizer(SEED), "Jacob"},
        {new FullNameRandomizer(SEED), "Breanna Mills"},
        {new Ipv4AddressRandomizer(SEED), "16.188.76.229"},
        {new Ipv6AddressRandomizer(SEED),
            "b3f4:4994:c9e8:b21a:c493:e923:f711:1115"},
        {new IsbnRandomizer(SEED), "9781785147906"},
        {new LastNameRandomizer(SEED), "Durgan"},
        {new LatitudeRandomizer(SEED),
            "40" + SEPARATOR + "171357"},
        {new LongitudeRandomizer(SEED),
            "80" + SEPARATOR + "342713"},
        {new MacAddressRandomizer(SEED), "b3:f4:49:94:c9:e8"},
        {new ParagraphRandomizer(SEED),
            "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. "
                + "Suscipit cupiditate doloribus debitis dolor. Cumque sapiente "
                + "occaecati. Quos maiores quae."},
        {new PhoneNumberRandomizer(SEED), "1-069-574-7539"},
        {new RegularExpressionRandomizer("\\d+[A-Z]{5}", SEED), "8UYSMT"},
        {new SentenceRandomizer(SEED), "Dolor totam assumenda eius autem."},
        {new StateRandomizer(SEED), "North Carolina"},
        {new StreetRandomizer(SEED), "Hegmann Locks"},
        {new WordRandomizer(SEED), "repellat"},
        {new ZipCodeRandomizer(SEED), "06957-4753"}
    };
  }

  //  FIXME：there is a bug in datafaker: the RandomService class does not use the passed in random object.
  @Disabled
//  @ParameterizedTest
  @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
  void shouldGenerateTheSameValueForTheSameSeed(final Randomizer<?> randomizer,
      final Object expected) {
    //when
    final Object actual = randomizer.getRandomValue();

    then(actual).isEqualTo(expected);
  }

  static Object[][] generateSeededRandomizersWithLocaleAndTheirExpectedValues() {
    final char SEPARATOR = new DecimalFormatSymbols(LOCALE).getDecimalSeparator();
    return new Object[][]{
        {new CityRandomizer(SEED, LOCALE), "Versailles"},
        {new CompanyRandomizer(SEED, LOCALE), "Masson et Lambert"},
        {new CountryRandomizer(SEED, LOCALE), "Peru"},
        {new CreditCardNumberRandomizer(SEED, LOCALE),
            "1211-1221-1234-2201"},
        {new EmailRandomizer(SEED, LOCALE), "alice.masson@hotmail.fr"},
        {new FirstNameRandomizer(SEED, LOCALE), "Alice"},
        {new FullNameRandomizer(SEED, LOCALE), "Masson Emilie"},
        {new Ipv4AddressRandomizer(SEED, LOCALE), "16.188.76.229"},
        {new Ipv6AddressRandomizer(SEED, LOCALE),
            "b3f4:4994:c9e8:b21a:c493:e923:f711:1115"},
        {new IsbnRandomizer(SEED, LOCALE), "9781839255908"},
        {new LastNameRandomizer(SEED, LOCALE), "Faure"},
        {new LatitudeRandomizer(SEED, LOCALE),
            "40" + SEPARATOR + "171357"},
        // should really be "40.171357", seems like a bug in java-faker
        {new LongitudeRandomizer(SEED, LOCALE),
            "80" + SEPARATOR + "342713"},
        // should really be "80.342713", seems like a bug in java-faker
        {new MacAddressRandomizer(SEED, LOCALE), "b3:f4:49:94:c9:e8"},
        {new ParagraphRandomizer(SEED, LOCALE),
            "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. "
                + "Suscipit cupiditate doloribus debitis dolor. Cumque sapiente "
                + "occaecati. Quos maiores quae."},
        {new PhoneNumberRandomizer(SEED, LOCALE), "03 06 95 74 75"},
        {new SentenceRandomizer(SEED, LOCALE),
            "Dolor totam assumenda eius autem."},
        {new StateRandomizer(SEED, LOCALE), "Lorraine"},
        {new StreetRandomizer(SEED, LOCALE), "Rue de Presbourg"},
        {new WordRandomizer(SEED, LOCALE), "repellat"},
        {new ZipCodeRandomizer(SEED, LOCALE), "06957"}
    };
  }

  //  FIXME：there is a bug in datafaker: the RandomService class does not use the passed in random object.
  @Disabled
//  @ParameterizedTest
  @MethodSource("generateSeededRandomizersWithLocaleAndTheirExpectedValues")
  void shouldGenerateTheSameValueForTheSameSeedForSameLocale(
      final Randomizer<?> randomizer, final Object expected) {
    //when
    final Object actual = randomizer.getRandomValue();

    then(actual).isEqualTo(expected);
  }
}
