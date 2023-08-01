////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeVariableCollectionBean<T, V> {

  private Collection<T> collection;
  private List<T> list;
  private Set<T> set;
  private Map<T, V> map;

  public TypeVariableCollectionBean() {
  }

  public Collection<T> getCollection() {
    return this.collection;
  }

  public List<T> getList() {
    return this.list;
  }

  public Set<T> getSet() {
    return this.set;
  }

  public Map<T, V> getMap() {
    return this.map;
  }

  public void setCollection(final Collection<T> collection) {
    this.collection = collection;
  }

  public void setList(final List<T> list) {
    this.list = list;
  }

  public void setSet(final Set<T> set) {
    this.set = set;
  }

  public void setMap(final Map<T, V> map) {
    this.map = map;
  }
}
