////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@SuppressWarnings("rawtypes")
public class MapBean {

  /*
   * Interfaces
   */

  private Map map;
  private Map<Integer, Person> typedMap;

  private SortedMap sortedMap;
  private SortedMap<Integer, Person> typedSortedMap;

  private NavigableMap navigableMap;
  private NavigableMap<Integer, Person> typedNavigableMap;

  private ConcurrentMap concurrentMap;
  private ConcurrentMap<Integer, Person> typedConcurrentMap;

  private ConcurrentNavigableMap concurrentNavigableMap;
  private ConcurrentNavigableMap<Integer, Person> typedConcurrentNavigableMap;

  /*
   * Classes
   */

  private HashMap hashMap;
  private HashMap<Integer, Person> typedHashMap;

  private Hashtable hashtable;
  private Hashtable<Integer, Person> typedHashtable;

  private LinkedHashMap linkedHashMap;
  private LinkedHashMap<Integer, Person> typedLinkedHashMap;

  private WeakHashMap weakHashMap;
  private WeakHashMap<Integer, Person> typedWeakHashMap;

  private IdentityHashMap identityHashMap;
  private IdentityHashMap<Integer, Person> typedIdentityHashMap;

  private TreeMap treeMap;
  private TreeMap<Integer, Person> typedTreeMap;

  private ConcurrentSkipListMap concurrentSkipListMap;
  private ConcurrentSkipListMap<Integer, Person> typedConcurrentSkipListMap;

  public MapBean() {
  }

  public Map getMap() {
    return this.map;
  }

  public Map<Integer, Person> getTypedMap() {
    return this.typedMap;
  }

  public SortedMap getSortedMap() {
    return this.sortedMap;
  }

  public SortedMap<Integer, Person> getTypedSortedMap() {
    return this.typedSortedMap;
  }

  public NavigableMap getNavigableMap() {
    return this.navigableMap;
  }

  public NavigableMap<Integer, Person> getTypedNavigableMap() {
    return this.typedNavigableMap;
  }

  public ConcurrentMap getConcurrentMap() {
    return this.concurrentMap;
  }

  public ConcurrentMap<Integer, Person> getTypedConcurrentMap() {
    return this.typedConcurrentMap;
  }

  public ConcurrentNavigableMap getConcurrentNavigableMap() {
    return this.concurrentNavigableMap;
  }

  public ConcurrentNavigableMap<Integer, Person> getTypedConcurrentNavigableMap() {
    return this.typedConcurrentNavigableMap;
  }

  public HashMap getHashMap() {
    return this.hashMap;
  }

  public HashMap<Integer, Person> getTypedHashMap() {
    return this.typedHashMap;
  }

  public Hashtable getHashtable() {
    return this.hashtable;
  }

  public Hashtable<Integer, Person> getTypedHashtable() {
    return this.typedHashtable;
  }

  public LinkedHashMap getLinkedHashMap() {
    return this.linkedHashMap;
  }

  public LinkedHashMap<Integer, Person> getTypedLinkedHashMap() {
    return this.typedLinkedHashMap;
  }

  public WeakHashMap getWeakHashMap() {
    return this.weakHashMap;
  }

  public WeakHashMap<Integer, Person> getTypedWeakHashMap() {
    return this.typedWeakHashMap;
  }

  public IdentityHashMap getIdentityHashMap() {
    return this.identityHashMap;
  }

  public IdentityHashMap<Integer, Person> getTypedIdentityHashMap() {
    return this.typedIdentityHashMap;
  }

  public TreeMap getTreeMap() {
    return this.treeMap;
  }

  public TreeMap<Integer, Person> getTypedTreeMap() {
    return this.typedTreeMap;
  }

  public ConcurrentSkipListMap getConcurrentSkipListMap() {
    return this.concurrentSkipListMap;
  }

  public ConcurrentSkipListMap<Integer, Person> getTypedConcurrentSkipListMap() {
    return this.typedConcurrentSkipListMap;
  }

  public void setMap(final Map map) {
    this.map = map;
  }

  public void setTypedMap(final Map<Integer, Person> typedMap) {
    this.typedMap = typedMap;
  }

  public void setSortedMap(final SortedMap sortedMap) {
    this.sortedMap = sortedMap;
  }

  public void setTypedSortedMap(final SortedMap<Integer, Person> typedSortedMap) {
    this.typedSortedMap = typedSortedMap;
  }

  public void setNavigableMap(final NavigableMap navigableMap) {
    this.navigableMap = navigableMap;
  }

  public void setTypedNavigableMap(
          final NavigableMap<Integer, Person> typedNavigableMap) {
    this.typedNavigableMap = typedNavigableMap;
  }

  public void setConcurrentMap(final ConcurrentMap concurrentMap) {
    this.concurrentMap = concurrentMap;
  }

  public void setTypedConcurrentMap(
          final ConcurrentMap<Integer, Person> typedConcurrentMap) {
    this.typedConcurrentMap = typedConcurrentMap;
  }

  public void setConcurrentNavigableMap(
          final ConcurrentNavigableMap concurrentNavigableMap) {
    this.concurrentNavigableMap = concurrentNavigableMap;
  }

  public void setTypedConcurrentNavigableMap(
          final ConcurrentNavigableMap<Integer, Person> typedConcurrentNavigableMap) {
    this.typedConcurrentNavigableMap = typedConcurrentNavigableMap;
  }

  public void setHashMap(final HashMap hashMap) {
    this.hashMap = hashMap;
  }

  public void setTypedHashMap(final HashMap<Integer, Person> typedHashMap) {
    this.typedHashMap = typedHashMap;
  }

  public void setHashtable(final Hashtable hashtable) {
    this.hashtable = hashtable;
  }

  public void setTypedHashtable(final Hashtable<Integer, Person> typedHashtable) {
    this.typedHashtable = typedHashtable;
  }

  public void setLinkedHashMap(final LinkedHashMap linkedHashMap) {
    this.linkedHashMap = linkedHashMap;
  }

  public void setTypedLinkedHashMap(
          final LinkedHashMap<Integer, Person> typedLinkedHashMap) {
    this.typedLinkedHashMap = typedLinkedHashMap;
  }

  public void setWeakHashMap(final WeakHashMap weakHashMap) {
    this.weakHashMap = weakHashMap;
  }

  public void setTypedWeakHashMap(
          final WeakHashMap<Integer, Person> typedWeakHashMap) {
    this.typedWeakHashMap = typedWeakHashMap;
  }

  public void setIdentityHashMap(final IdentityHashMap identityHashMap) {
    this.identityHashMap = identityHashMap;
  }

  public void setTypedIdentityHashMap(
          final IdentityHashMap<Integer, Person> typedIdentityHashMap) {
    this.typedIdentityHashMap = typedIdentityHashMap;
  }

  public void setTreeMap(final TreeMap treeMap) {
    this.treeMap = treeMap;
  }

  public void setTypedTreeMap(final TreeMap<Integer, Person> typedTreeMap) {
    this.typedTreeMap = typedTreeMap;
  }

  public void setConcurrentSkipListMap(
          final ConcurrentSkipListMap concurrentSkipListMap) {
    this.concurrentSkipListMap = concurrentSkipListMap;
  }

  public void setTypedConcurrentSkipListMap(
          final ConcurrentSkipListMap<Integer, Person> typedConcurrentSkipListMap) {
    this.typedConcurrentSkipListMap = typedConcurrentSkipListMap;
  }
}
