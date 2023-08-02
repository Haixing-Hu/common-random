////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class ListSubclass extends ArrayList<Integer> {

  private static final long serialVersionUID = -279477524266849751L;

  private String name;
  private Set<String> aliases;

  public final String getName() {
    return name;
  }

  public final ListSubclass setName(final String name) {
    this.name = name;
    return this;
  }

  public final Set<String> getAliases() {
    return aliases;
  }

  public final ListSubclass setAliases(final Set<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final ListSubclass other = (ListSubclass) o;
    return super.equals(other)
            && Objects.equals(name, other.name)
            && Objects.equals(aliases, other.aliases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, aliases);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ListSubclass.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("aliases=" + aliases)
            .toString();
  }
}
