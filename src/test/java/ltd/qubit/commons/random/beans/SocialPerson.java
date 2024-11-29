////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Set;

public class SocialPerson extends Person {

  private Set<Person> friends;

  public SocialPerson() {
  }

  public Set<Person> getFriends() {
    return friends;
  }

  public void setFriends(final Set<Person> friends) {
    this.friends = friends;
  }
}
