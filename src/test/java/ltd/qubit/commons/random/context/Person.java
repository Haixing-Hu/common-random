////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.context;

public class Person {
  private String firstName;
  private String lastName;
  private String nickname;
  private Pet pet;
  private Country country;
  private City city;

  public Person() {
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getNickname() {
    return this.nickname;
  }

  public Pet getPet() {
    return this.pet;
  }

  public Country getCountry() {
    return this.country;
  }

  public City getCity() {
    return this.city;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public void setNickname(final String nickname) {
    this.nickname = nickname;
  }

  public void setPet(final Pet pet) {
    this.pet = pet;
  }

  public void setCountry(final Country country) {
    this.country = country;
  }

  public void setCity(final City city) {
    this.city = city;
  }
}
