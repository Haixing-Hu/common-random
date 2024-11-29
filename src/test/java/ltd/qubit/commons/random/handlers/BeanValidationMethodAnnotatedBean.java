////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

class BeanValidationMethodAnnotatedBean {

  private boolean unsupported;

  private boolean active;

  private BigDecimal maxDiscount;

  private BigDecimal minDiscount;

  private BigDecimal discount;

  private int positive;

  private int positiveOrZero;

  private int negative;

  private int negativeOrZero;

  private String notBlank;

  private String email;

  private Date eventDate;
  private LocalDateTime eventLocalDateTime;

  private Date birthday;
  private LocalDateTime birthdayLocalDateTime;

  private Date pastOrPresent;
  private Date futureOrPresent;

  private Instant pastInstant;

  private int maxQuantity;

  private int minQuantity;

  private String username;

  private String unusedString;

  private String briefMessage;

  private Collection<String> sizedCollection;

  private List<String> sizedList;

  private Set<String> sizedSet;

  private Map<String, Integer> sizedMap;

  private String[] sizedArray;

  private String sizedString;

  private String regexString;

  @AssertFalse
  public boolean isUnsupported() {
    return unsupported;
  }

  public void setUnsupported(final boolean unsupported) {
    this.unsupported = unsupported;
  }

  @AssertTrue
  public boolean isActive() {
    return active;
  }

  public void setActive(final boolean active) {
    this.active = active;
  }

  @DecimalMax("30.00")
  public BigDecimal getMaxDiscount() {
    return maxDiscount;
  }

  public void setMaxDiscount(final BigDecimal maxDiscount) {
    this.maxDiscount = maxDiscount;
  }

  @DecimalMin("5.00")
  public BigDecimal getMinDiscount() {
    return minDiscount;
  }

  @DecimalMax("1.00")
  @DecimalMin("0.01")
  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(final BigDecimal discount) {
    this.discount = discount;
  }

  public void setMinDiscount(final BigDecimal minDiscount) {
    this.minDiscount = minDiscount;
  }

  @Positive
  public int getPositive() {
    return positive;
  }

  public void setPositive(final int positive) {
    this.positive = positive;
  }

  @PositiveOrZero
  public int getPositiveOrZero() {
    return positiveOrZero;
  }

  public void setPositiveOrZero(final int positiveOrZero) {
    this.positiveOrZero = positiveOrZero;
  }

  @Negative
  public int getNegative() {
    return negative;
  }

  public void setNegative(final int negative) {
    this.negative = negative;
  }

  @NegativeOrZero
  public int getNegativeOrZero() {
    return negativeOrZero;
  }

  public void setNegativeOrZero(final int negativeOrZero) {
    this.negativeOrZero = negativeOrZero;
  }

  @Future
  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(final Date eventDate) {
    this.eventDate = eventDate;
  }

  @Future
  public LocalDateTime getEventLocalDateTime() {
    return eventLocalDateTime;
  }

  public void setEventLocalDateTime(final LocalDateTime eventLocalDateTime) {
    this.eventLocalDateTime = eventLocalDateTime;
  }

  @FutureOrPresent
  public Date getFutureOrPresent() {
    return futureOrPresent;
  }

  public void setFutureOrPresent(final Date futureOrPresent) {
    this.futureOrPresent = futureOrPresent;
  }

  @Past
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(final Date birthday) {
    this.birthday = birthday;
  }

  @Past
  public LocalDateTime getBirthdayLocalDateTime() {
    return birthdayLocalDateTime;
  }

  public void setBirthdayLocalDateTime(final LocalDateTime birthdayLocalDateTime) {
    this.birthdayLocalDateTime = birthdayLocalDateTime;
  }

  @Past
  public Instant getPastInstant() {
    return pastInstant;
  }

  public void setPastInstant(final Instant pastInstant) {
    this.pastInstant = pastInstant;
  }

  @PastOrPresent
  public Date getPastOrPresent() {
    return pastOrPresent;
  }

  public void setPastOrPresent(final Date pastOrPresent) {
    this.pastOrPresent = pastOrPresent;
  }

  @Max(10)
  public int getMaxQuantity() {
    return maxQuantity;
  }

  public void setMaxQuantity(final int maxQuantity) {
    this.maxQuantity = maxQuantity;
  }

  @Min(5)
  public int getMinQuantity() {
    return minQuantity;
  }

  public void setMinQuantity(final int minQuantity) {
    this.minQuantity = minQuantity;
  }

  @NotNull
  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  @Null
  public String getUnusedString() {
    return unusedString;
  }

  public void setUnusedString(final String unusedString) {
    this.unusedString = unusedString;
  }

  @Size(min = 2, max = 10)
  public String getBriefMessage() {
    return briefMessage;
  }

  public void setBriefMessage(final String briefMessage) {
    this.briefMessage = briefMessage;
  }

  @Pattern(regexp = "[a-z]{4}")
  public String getRegexString() {
    return regexString;
  }

  public void setRegexString(final String regexString) {
    this.regexString = regexString;
  }

  @NotBlank
  public String getNotBlank() {
    return notBlank;
  }

  public void setNotBlank(final String notBlank) {
    this.notBlank = notBlank;
  }

  @Email
  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  @Size(min = 2, max = 10)
  public Collection<String> getSizedCollection() {
    return sizedCollection;
  }

  public void setSizedCollection(final Collection<String> sizedCollection) {
    this.sizedCollection = sizedCollection;
  }

  @Size(min = 2, max = 10)
  public List<String> getSizedList() {
    return sizedList;
  }

  public void setSizedList(final List<String> sizedList) {
    this.sizedList = sizedList;
  }

  @Size(min = 2, max = 10)
  public Set<String> getSizedSet() {
    return sizedSet;
  }

  public void setSizedSet(final Set<String> sizedSet) {
    this.sizedSet = sizedSet;
  }

  @Size(min = 2, max = 10)
  public Map<String, Integer> getSizedMap() {
    return sizedMap;
  }

  public void setSizedMap(final Map<String, Integer> sizedMap) {
    this.sizedMap = sizedMap;
  }

  @Size(min = 2, max = 10)
  public String[] getSizedArray() {
    return sizedArray;
  }

  public void setSizedArray(final String[] sizedArray) {
    this.sizedArray = sizedArray;
  }

  @Size(min = 2)
  public String getSizedString() {
    return sizedString;
  }

  public void setSizedString(final String sizedString) {
    this.sizedString = sizedString;
  }

  @Override
  public String toString() {
    return "BeanValidationMethodAnnotatedBean{"
        + "unsupported=" + unsupported
        + ", active=" + active
        + ", maxDiscount=" + maxDiscount
        + ", minDiscount=" + minDiscount
        + ", discount=" + discount
        + ", positive=" + positive
        + ", positiveOrZero=" + positiveOrZero
        + ", negative=" + negative
        + ", negativeOrZero=" + negativeOrZero
        + ", notBlank='" + notBlank + '\''
        + ", email='" + email + '\''
        + ", eventDate=" + eventDate
        + ", eventLocalDateTime=" + eventLocalDateTime
        + ", birthday=" + birthday
        + ", birthdayLocalDateTime=" + birthdayLocalDateTime
        + ", pastOrPresent=" + pastOrPresent
        + ", futureOrPresent=" + futureOrPresent
        + ", maxQuantity=" + maxQuantity
        + ", minQuantity=" + minQuantity
        + ", username='" + username + '\''
        + ", unusedString='" + unusedString + '\''
        + ", briefMessage='" + briefMessage + '\''
        + ", sizedCollection=" + sizedCollection
        + ", sizedList=" + sizedList
        + ", sizedSet=" + sizedSet
        + ", sizedMap=" + sizedMap
        + ", sizedArray=" + Arrays.toString(sizedArray)
        + ", sizedString=" + sizedString
        + ", regexString='" + regexString + '\''
        + '}';
  }
}
