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
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

class BeanValidationAnnotatedBean {

  @AssertFalse
  private boolean unsupported;

  @AssertTrue
  private boolean active;

  @DecimalMax("30.00")
  private BigDecimal maxDiscount;

  @DecimalMin("5.00")
  private BigDecimal minDiscount;

  @DecimalMax("1.00")
  @DecimalMin("0.01")
  private BigDecimal discount;

  @Future
  private Date eventDate;

  @Future
  private LocalDateTime eventLocalDateTime;

  @FutureOrPresent
  private Date futureOrPresent;

  @Past
  private Date birthday;

  @Past
  private LocalDateTime birthdayLocalDateTime;

  @Past
  private Instant pastInstant;

  @PastOrPresent
  private Date pastOrPresent;

  @Max(10)
  private int maxQuantity;

  @Min(5)
  private int minQuantity;

  @Positive
  private int positive;

  @PositiveOrZero
  private int positiveOrZero;

  @Negative
  private int negative;

  @NegativeOrZero
  private int negativeOrZero;

  @NotBlank
  private String notBlank;

  @Email
  private String email;

  @NotNull
  private String username;

  @Null
  private String unusedString;

  @Size(min = 2, max = 10)
  private String briefMessage;

  @Size(min = 2, max = 10)
  private Collection<String> sizedCollection;

  @Size(min = 2, max = 10)
  private List<String> sizedList;

  @Size(min = 2, max = 10)
  private Set<String> sizedSet;

  @Size(min = 2, max = 10)
  private Map<String, Integer> sizedMap;

  @Size(min = 2, max = 10)
  private String[] sizedArray;

  @Size(min = 2)
  private String sizedString;

  @Pattern(regexp = "[a-z]{4}")
  private String regexString;

  @NotEmpty
  private String notEmptyString;

  @NotEmpty
  private List<String> notEmptyList;

  @NotEmpty
  private String[] notEmptyArray;

  @NotEmpty
  private Map<String, String> notEmptyMap;

  public BeanValidationAnnotatedBean() {
  }

  public boolean isUnsupported() {
    return this.unsupported;
  }

  public boolean isActive() {
    return this.active;
  }

  public BigDecimal getMaxDiscount() {
    return this.maxDiscount;
  }

  public BigDecimal getMinDiscount() {
    return this.minDiscount;
  }

  public BigDecimal getDiscount() {
    return this.discount;
  }

  public Date getEventDate() {
    return this.eventDate;
  }

  public LocalDateTime getEventLocalDateTime() {
    return this.eventLocalDateTime;
  }

  public Date getFutureOrPresent() {
    return this.futureOrPresent;
  }

  public Date getBirthday() {
    return this.birthday;
  }

  public LocalDateTime getBirthdayLocalDateTime() {
    return this.birthdayLocalDateTime;
  }

  public Instant getPastInstant() {
    return this.pastInstant;
  }

  public Date getPastOrPresent() {
    return this.pastOrPresent;
  }

  public int getMaxQuantity() {
    return this.maxQuantity;
  }

  public int getMinQuantity() {
    return this.minQuantity;
  }

  public int getPositive() {
    return this.positive;
  }

  public int getPositiveOrZero() {
    return this.positiveOrZero;
  }

  public int getNegative() {
    return this.negative;
  }

  public int getNegativeOrZero() {
    return this.negativeOrZero;
  }

  public String getNotBlank() {
    return this.notBlank;
  }

  public String getEmail() {
    return this.email;
  }

  public String getUsername() {
    return this.username;
  }

  public String getUnusedString() {
    return this.unusedString;
  }

  public String getBriefMessage() {
    return this.briefMessage;
  }

  public Collection<String> getSizedCollection() {
    return this.sizedCollection;
  }

  public List<String> getSizedList() {
    return this.sizedList;
  }

  public Set<String> getSizedSet() {
    return this.sizedSet;
  }

  public Map<String, Integer> getSizedMap() {
    return this.sizedMap;
  }

  public String[] getSizedArray() {
    return this.sizedArray;
  }

  public String getSizedString() {
    return this.sizedString;
  }

  public String getRegexString() {
    return this.regexString;
  }

  public void setUnsupported(final boolean unsupported) {
    this.unsupported = unsupported;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  public void setMaxDiscount(final BigDecimal maxDiscount) {
    this.maxDiscount = maxDiscount;
  }

  public void setMinDiscount(final BigDecimal minDiscount) {
    this.minDiscount = minDiscount;
  }

  public void setDiscount(final BigDecimal discount) {
    this.discount = discount;
  }

  public void setEventDate(final Date eventDate) {
    this.eventDate = eventDate;
  }

  public void setEventLocalDateTime(final LocalDateTime eventLocalDateTime) {
    this.eventLocalDateTime = eventLocalDateTime;
  }

  public void setFutureOrPresent(final Date futureOrPresent) {
    this.futureOrPresent = futureOrPresent;
  }

  public void setBirthday(final Date birthday) {
    this.birthday = birthday;
  }

  public void setBirthdayLocalDateTime(final LocalDateTime birthdayLocalDateTime) {
    this.birthdayLocalDateTime = birthdayLocalDateTime;
  }

  public void setPastInstant(final Instant pastInstant) {
    this.pastInstant = pastInstant;
  }

  public void setPastOrPresent(final Date pastOrPresent) {
    this.pastOrPresent = pastOrPresent;
  }

  public void setMaxQuantity(final int maxQuantity) {
    this.maxQuantity = maxQuantity;
  }

  public void setMinQuantity(final int minQuantity) {
    this.minQuantity = minQuantity;
  }

  public void setPositive(final int positive) {
    this.positive = positive;
  }

  public void setPositiveOrZero(final int positiveOrZero) {
    this.positiveOrZero = positiveOrZero;
  }

  public void setNegative(final int negative) {
    this.negative = negative;
  }

  public void setNegativeOrZero(final int negativeOrZero) {
    this.negativeOrZero = negativeOrZero;
  }

  public void setNotBlank(final String notBlank) {
    this.notBlank = notBlank;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public void setUnusedString(final String unusedString) {
    this.unusedString = unusedString;
  }

  public void setBriefMessage(final String briefMessage) {
    this.briefMessage = briefMessage;
  }

  public void setSizedCollection(final Collection<String> sizedCollection) {
    this.sizedCollection = sizedCollection;
  }

  public void setSizedList(final List<String> sizedList) {
    this.sizedList = sizedList;
  }

  public void setSizedSet(final Set<String> sizedSet) {
    this.sizedSet = sizedSet;
  }

  public void setSizedMap(final Map<String, Integer> sizedMap) {
    this.sizedMap = sizedMap;
  }

  public void setSizedArray(final String[] sizedArray) {
    this.sizedArray = sizedArray;
  }

  public void setSizedString(final String sizedString) {
    this.sizedString = sizedString;
  }

  public void setRegexString(final String regexString) {
    this.regexString = regexString;
  }

  public final String getNotEmptyString() {
    return notEmptyString;
  }

  public final void setNotEmptyString(final String notEmptyString) {
    this.notEmptyString = notEmptyString;
  }

  public final List<String> getNotEmptyList() {
    return notEmptyList;
  }

  public final void setNotEmptyList(final List<String> notEmptyList) {
    this.notEmptyList = notEmptyList;
  }

  public final String[] getNotEmptyArray() {
    return notEmptyArray;
  }

  public final void setNotEmptyArray(final String[] notEmptyArray) {
    this.notEmptyArray = notEmptyArray;
  }

  public final Map<String, String> getNotEmptyMap() {
    return notEmptyMap;
  }

  public final void setNotEmptyMap(final Map<String, String> notEmptyMap) {
    this.notEmptyMap = notEmptyMap;
  }
}
