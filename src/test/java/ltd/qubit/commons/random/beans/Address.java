////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Objects;
import java.util.StringJoiner;

public class Address {

  private Street street;

  private String zipCode;

  private String city;

  private String country;

  public Address() {
  }

  public Street getStreet() {
    return this.street;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public String getCity() {
    return this.city;
  }

  public String getCountry() {
    return this.country;
  }

  public void setStreet(final Street street) {
    this.street = street;
  }

  public void setZipCode(final String zipCode) {
    this.zipCode = zipCode;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Address address = (Address) o;
    return Objects.equals(street, address.street)
        && Objects.equals(zipCode, address.zipCode)
        && Objects.equals(city, address.city)
        && Objects.equals(country, address.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, zipCode, city, country);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
            .add("street=" + street)
            .add("zipCode='" + zipCode + "'")
            .add("city='" + city + "'")
            .add("country='" + country + "'")
            .toString();
  }
}
