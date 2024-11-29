////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.HashMap;
import java.util.Set;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

public class MapSubclass extends HashMap<String, Integer> {

  private static final long serialVersionUID = 6269433868252220288L;

  private String name;
  private Set<String> aliases;

  public final String getName() {
    return name;
  }

  public final MapSubclass setName(final String name) {
    this.name = name;
    return this;
  }

  public final Set<String> getAliases() {
    return aliases;
  }

  public final MapSubclass setAliases(final Set<String> aliases) {
    this.aliases = aliases;
    return this;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final MapSubclass other = (MapSubclass) o;
    return super.equals(other)
        && Equality.equals(name, other.name)
        && Equality.equals(aliases, other.aliases);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, aliases);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("name", name)
        .append("aliases", aliases)
        .append("size", size())
        .append("entries", entrySet())
        .toString();
  }
}
