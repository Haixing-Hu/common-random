////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Date;
import java.util.List;

import ltd.qubit.commons.annotation.Exclude;

public class Person extends Human implements Comparable<Person> {

  protected transient String email;

  protected Gender gender;

  protected Address address;

  protected Date birthDate;

  protected String phoneNumber;

  protected List<String> nicknames;

  protected Person parent;

  @Exclude
  protected String excluded;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public List<String> getNicknames() {
    return nicknames;
  }

  public void setNicknames(final List<String> nicknames) {
    this.nicknames = nicknames;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(final Date birthdate) {
    this.birthDate = birthdate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getExcluded() {
    return excluded;
  }

  public void setExcluded(final String excluded) {
    this.excluded = excluded;
  }

  public Person getParent() {
    return parent;
  }

  public void setParent(final Person parent) {
    this.parent = parent;
  }

  @Override
  public int compareTo(final Person person) {
    return name.compareTo(person.getName());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Person person = (Person) o;

    if (email != null ? !email.equals(person.email) : person.email != null) {
      return false;
    }
    if (gender != person.gender) {
      return false;
    }
    if (address != null ? !address.equals(person.address)
                        : person.address != null) {
      return false;
    }
    return !(phoneNumber != null ? !phoneNumber.equals(person.phoneNumber)
                                 : person.phoneNumber != null);

  }

  @Override
  public int hashCode() {
    int result = email != null ? email.hashCode() : 0;
    result = 31 * result + (gender != null ? gender.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
    return result;
  }
}
