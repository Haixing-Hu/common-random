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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CompositeMapBean {

  private Map<Person, List<String>> personToNicknames;
  private Map<Integer, Set<String>> personToAccounts;
  private HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>>
      reallyStrangeCompositeDataStructure;

  public CompositeMapBean() {
  }

  public Map<Person, List<String>> getPersonToNicknames() {
    return this.personToNicknames;
  }

  public Map<Integer, Set<String>> getPersonToAccounts() {
    return this.personToAccounts;
  }

  public HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>>
      getReallyStrangeCompositeDataStructure() {
    return this.reallyStrangeCompositeDataStructure;
  }

  public void setPersonToNicknames(final Map<Person, List<String>> personToNicknames) {
    this.personToNicknames = personToNicknames;
  }

  public void setPersonToAccounts(final Map<Integer, Set<String>> personToAccounts) {
    this.personToAccounts = personToAccounts;
  }

  public void setReallyStrangeCompositeDataStructure(
          final HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>>
              reallyStrangeCompositeDataStructure) {
    this.reallyStrangeCompositeDataStructure = reallyStrangeCompositeDataStructure;
  }
}
