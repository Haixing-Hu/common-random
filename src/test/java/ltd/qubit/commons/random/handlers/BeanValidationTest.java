////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Digits;

import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.random.handlers.NullableAnnotationHandler.DEFAULT_NULL_RATIO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanValidationTest {

  static final int TEST_COUNT = 2000;
  static final double EPSILON = 0.03;

  private EasyRandom easyRandom;

  @BeforeEach
  void setUp() {
    easyRandom = new EasyRandom();
  }

  //  stop checkstyle: MagicNumberCheck
  @Disabled("Cannot pass")
  @Test
  void generatedValuesShouldBeValidAccordingToValidationConstraints() {
    final BeanValidationAnnotatedBean bean =
        easyRandom.nextObject(BeanValidationAnnotatedBean.class);

    assertThat(bean).isNotNull();

    assertThat(bean.isUnsupported()).isFalse(); // @AssertFalse boolean unsupported;

    assertThat(bean.isActive()).isTrue(); // @AssertTrue boolean active;

    assertThat(bean.getUnusedString()).isNull(); // @Null String unusedString;

    assertThat(bean.getUsername()).isNotNull(); // @NotNull String username;

    assertThat(bean.getBirthday()).isInThePast(); // @Past Date birthday;

    assertThat(bean.getBirthdayLocalDateTime())
        .isBefore(LocalDateTime.now()); // @Past LocalDateTime birthdayLocalDateTime;

    assertThat(bean.getPastOrPresent())
        .isBeforeOrEqualTo(new Date()); // @PastOrPresent Date pastOrPresent;

    assertThat(bean.getEventDate())
        .isInTheFuture(); // @Future Date eventDate;

    assertThat(bean.getEventLocalDateTime())
        .isAfter(LocalDateTime.now()); // @Future LocalDateTime eventLocalDateTime;

    assertThat(bean.getFutureOrPresent())
        .isAfterOrEqualTo(new Date()); // @FutureOrPresent Date eventDate;

    assertThat(bean.getPositive())
        .isGreaterThan(0); // @Positive int positive;

    assertThat(bean.getPositiveOrZero())
        .isGreaterThanOrEqualTo(0); // @PositiveOrZero int positiveOrZero;

    assertThat(bean.getNegative()).isLessThan(0); // @Negative int negative;

    assertThat(bean.getNegativeOrZero())
        .isLessThanOrEqualTo(0); // @NegativeOrZero int negativeOrZero;

    assertThat(bean.getNotBlank()).isNotBlank(); // @NotBlank String notBlank;

    assertThat(bean.getEmail()).isNotBlank()
                               .contains(".", "@"); // @Email String email;

    assertThat(bean.getMaxQuantity())
        .isLessThanOrEqualTo(10); // @Max(10) int maxQuantity;

    assertThat(bean.getMinQuantity())
        .isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

    assertThat(bean.getMaxDiscount())
        .isLessThanOrEqualTo(new BigDecimal("30.00"));
    // @DecimalMax("30.00") BigDecimal maxDiscount;

    assertThat(bean.getMinDiscount())
        .isGreaterThanOrEqualTo(new BigDecimal("5.00"));
    // @DecimalMin("5.00") BigDecimal minDiscount;

    assertThat(bean.getDiscount())
        .isLessThanOrEqualTo(new BigDecimal("1.00"));
    // @DecimalMax("1.00") BigDecimal discount;
    assertThat(bean.getDiscount())
        .isGreaterThanOrEqualTo(new BigDecimal("0.01"));
    // @DecimalMin("0.01") BigDecimal discount;

    assertThat(bean.getMinQuantity())
        .isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

    assertThat(bean.getBriefMessage()
                   .length()).isBetween(2, 10); // @Size(min=2, max=10) String briefMessage;
    assertThat(bean.getSizedCollection()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedCollection;
    assertThat(bean.getSizedList()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedList;
    assertThat(bean.getSizedSet()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedSet;
    assertThat(bean.getSizedMap()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedMap;
    assertThat(bean.getSizedArray().length)
        .isBetween(2, 10); // @Size(min=2, max=10) String sizedArray;
    assertThat(bean.getSizedString()
                   .length()).isBetween(2, 255); // @Size(min=2) String sizedString;

    assertThat(bean.getRegexString()).matches("[a-z]{4}");

    assertThat(bean.getNotEmptyString().length()).isGreaterThan(0);
    assertThat(bean.getNotEmptyList().size()).isGreaterThan(0);
    assertThat(bean.getNotEmptyArray().length).isGreaterThan(0);
    assertThat(bean.getNotEmptyMap().size()).isGreaterThan(0);
  }

  @Disabled("Cannot pass")
  @Test
  void generatedValuesShouldBeValidAccordingToValidationConstraintsOnMethod() {
    final BeanValidationMethodAnnotatedBean bean =
        easyRandom.nextObject(BeanValidationMethodAnnotatedBean.class);

    assertThat(bean).isNotNull();

    assertThat(bean.isUnsupported()).isFalse(); // @AssertFalse boolean unsupported;

    assertThat(bean.isActive()).isTrue(); // @AssertTrue boolean active;

    assertThat(bean.getUnusedString()).isNull(); // @Null String unusedString;

    assertThat(bean.getUsername()).isNotNull(); // @NotNull String username;

    assertThat(bean.getBirthday()).isInThePast(); // @Past Date birthday;

    assertThat(bean.getBirthdayLocalDateTime())
        .isBefore(LocalDateTime.now()); // @Past LocalDateTime birthdayLocalDateTime;

    assertThat(bean.getPastOrPresent())
        .isBeforeOrEqualTo(new Date()); // @PastOrPresent Date pastOrPresent;

    assertThat(bean.getEventDate()).isInTheFuture(); // @Future Date eventDate;

    assertThat(bean.getEventLocalDateTime())
        .isAfter(LocalDateTime.now()); // @Future LocalDateTime eventLocalDateTime;

    assertThat(bean.getFutureOrPresent())
        .isBeforeOrEqualTo(new Date()); // @FutureOrPresent Date eventDate;

    assertThat(bean.getPositive()).isGreaterThan(0); // @Positive int positive;

    assertThat(bean.getPositiveOrZero())
        .isGreaterThanOrEqualTo(0); // @PositiveOrZero int positiveOrZero;

    assertThat(bean.getNegative()).isLessThan(0); // @Negative int negative;

    assertThat(bean.getNegativeOrZero())
        .isLessThanOrEqualTo(0); // @NegativeOrZero int negativeOrZero;

    assertThat(bean.getNotBlank()).isNotBlank(); // @NotBlank String notBlank;

    assertThat(bean.getEmail()).isNotBlank()
                               .contains(".", "@"); // @Email String email;

    assertThat(bean.getMaxQuantity())
        .isLessThanOrEqualTo(10); // @Max(10) int maxQuantity;

    assertThat(bean.getMinQuantity())
        .isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

    assertThat(bean.getMaxDiscount())
        .isLessThanOrEqualTo(new BigDecimal("30.00"));
    // @DecimalMax("30.00") BigDecimal maxDiscount;

    assertThat(bean.getMinDiscount())
        .isGreaterThanOrEqualTo(new BigDecimal("5.00"));
    // @DecimalMin("5.00") BigDecimal minDiscount;

    assertThat(bean.getDiscount())
        .isLessThanOrEqualTo(new BigDecimal("1.00")); // @DecimalMax("1.00") BigDecimal discount;
    assertThat(bean.getDiscount())
        .isGreaterThanOrEqualTo(new BigDecimal("0.01")); // @DecimalMin("0.01") BigDecimal discount;

    assertThat(bean.getMinQuantity())
        .isGreaterThanOrEqualTo(5); // @Min(5) int minQuantity;

    assertThat(bean.getBriefMessage()
                   .length()).isBetween(2, 10); // @Size(min=2, max=10) String briefMessage;
    assertThat(bean.getSizedCollection()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedCollection;
    assertThat(bean.getSizedList()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedList;
    assertThat(bean.getSizedSet()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedSet;
    assertThat(bean.getSizedMap()
                   .size()).isBetween(2, 10); // @Size(min=2, max=10) String sizedMap;
    assertThat(bean.getSizedArray().length)
        .isBetween(2, 10); // @Size(min=2, max=10) String sizedArray;
    assertThat(bean.getSizedString()
                   .length()).isBetween(2, 255); // @Size(min=2) String sizedString;

    assertThat(bean.getRegexString()).matches("[a-z]{4}");
  }

  @Test
  void generatedValuesForBeanWithoutReadMethod() {
    final BeanValidationWithoutReadMethodBean bean =
        easyRandom.nextObject(BeanValidationWithoutReadMethodBean.class);

    assertThat(bean).hasNoNullFieldsOrProperties();
  }

  @Disabled("Cannot pass")
  @Test
  void shouldGenerateTheSameValueForTheSameSeed() {
    final Parameters parameters = new Parameters().seed(123L);
    final EasyRandom random = new EasyRandom(parameters);

    final BeanValidationAnnotatedBean bean =
        random.nextObject(BeanValidationAnnotatedBean.class);

    assertThat(bean.getUsername()).isEqualTo("eOMtThyhVNLWUZNRcBaQKxI");
    // uses DateRange with now as end, so test is not repeatable
    // assertThat(bean.getBirthday()).isEqualTo("2007-07-22T13:20:35.628");
    // same for birthdayLocalDateTime
    // uses DateRange with now as start, so test is not repeatable
    // assertThat(bean.getEventDate()).isEqualTo("2017-07-22T13:20:35.628");
    // same for eventLocalDateTime
    assertThat(bean.getMaxQuantity()).isEqualTo(-2055951745);
    assertThat(bean.getMinQuantity()).isEqualTo(91531906);
    assertThat(bean.getMaxDiscount())
        .isEqualTo(new BigDecimal(
            "1.2786858993971550457757757612853311002254486083984375"));
    assertThat(bean.getMinDiscount())
        .isEqualTo(new BigDecimal("76622828766383706091461017405438016323843710"
            + "1175572542764478589628103315446510748101423686509060287000660814"
            + "3292003443098160947481248487711461114361337135608579588927391230"
            + "9029258505236447376737243790447250032376912911187814333361213349"
            + "6226391925118863015267421517488006570725654526844517171464812422"
            + "9156864D"));
    assertThat(bean.getDiscount())
        .isEqualTo(new BigDecimal(
            "0.182723708049134681008496272625052370131015777587890625"));
    assertThat(bean.getMinQuantity()).isEqualTo(91531906);
    assertThat(bean.getBriefMessage()).isEqualTo("tg");
    assertThat(bean.getRegexString()).isEqualTo("vuna");
    assertThat(bean.getPositive()).isEqualTo(91531902);
    assertThat(bean.getPositiveOrZero()).isEqualTo(91531901);
    assertThat(bean.getNegative()).isEqualTo(-2055951746);
    assertThat(bean.getNegativeOrZero()).isEqualTo(-2055951746);
    assertThat(bean.getEmail()).isEqualTo("celine.schoen@hotmail.com");
    assertThat(bean.getNotBlank()).isEqualTo("tg");
  }

  @Disabled("Cannot pass")
  @Test
  void generatedBeanShouldBeValidUsingBeanValidationApi() {
    final BeanValidationAnnotatedBean bean =
        easyRandom.nextObject(BeanValidationAnnotatedBean.class);

    final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    final Validator validator = validatorFactory.getValidator();
    final Set<ConstraintViolation<BeanValidationAnnotatedBean>> violations = validator
            .validate(bean);

    assertThat(violations).isEmpty();
  }

  @Test
  void customRegistryTest() {
    // given
    class Salary {
      @Digits(integer = 2, fraction = 2) // OSS developer salary.. :-)
      private BigDecimal amount;
    }

    final Parameters parameters = new Parameters()
            .randomizerRegistry(new MyCustomBeanValidationRandomizerRegistry());
    final EasyRandom easyRandom = new EasyRandom(parameters);

    // when
    final Salary salary = easyRandom.nextObject(Salary.class);

    // then
    assertThat(salary).isNotNull();
    assertThat(salary.amount).isLessThanOrEqualTo(new BigDecimal("99.99"));
  }

  @Test
  void testNullableAnnotation() {
    final int[] nullCounts = {0, 0, 0, 0, 0};
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testNullableAnnotation: " + i);
      final NullableAnnotatedBean obj = easyRandom.nextObject(NullableAnnotatedBean.class);
      if (obj.f0 == null) {
        ++nullCounts[0];
      }
      if (obj.f1 == null) {
        ++nullCounts[1];
      }
      if (obj.f2 == null) {
        ++nullCounts[2];
      }
      if (obj.f3 == null) {
        ++nullCounts[3];
      }
      if (obj.f4 == null) {
        ++nullCounts[4];
      }
    }
    assertEquals(0, nullCounts[0]);
    assertEquals(0, nullCounts[1]);
    assertEquals(0, nullCounts[3]);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[2] / (double) TEST_COUNT, EPSILON);
    assertEquals(DEFAULT_NULL_RATIO, nullCounts[4] / (double) TEST_COUNT, EPSILON);
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f2: %f\n", nullCounts[2] / (double) TEST_COUNT);
    System.out.printf("Null ratio of f4: %f\n", nullCounts[4] / (double) TEST_COUNT);
  }

  @Test
  public void testNullableSizeAnnotatedField() {
    int nullCount = 0;
    for (int i = 0; i < TEST_COUNT; ++i) {
      System.out.println("testNullableSizeAnnotatedField: " + i);
      final NullableSizeAnnotatedBean obj = easyRandom.nextObject(NullableSizeAnnotatedBean.class);
      assertNotNull(obj);
      if (obj.f1 == null) {
        ++nullCount;
      } else {
        assertTrue(obj.f1.length() <= 3);
      }
    }
    System.out.printf("Expected null ratio: %f\n", DEFAULT_NULL_RATIO);
    System.out.printf("Null ratio of f1: %f\n", nullCount / (double) TEST_COUNT);
    assertEquals(DEFAULT_NULL_RATIO, nullCount / (double) TEST_COUNT, EPSILON);
  }
  //  resume checkstyle: MagicNumberCheck
}
