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

public class WildCardMapBean {

  /*
   * Interfaces
   */

  private Map<?, ?> unboundedWildCardTypedMap;
  private Map<? extends Number, ? extends Runnable> boundedWildCardTypedMap;

  private SortedMap<?, ?> unboundedWildCardTypedSortedMap;
  private SortedMap<? extends Number, ? extends Runnable> boundedWildCardTypedSortedMap;

  private NavigableMap<?, ?> unboundedWildCardTypedNavigableMap;
  private NavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedNavigableMap;

  private ConcurrentMap<?, ?> unboundedWildCardTypedConcurrentMap;
  private ConcurrentMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentMap;

  private ConcurrentNavigableMap<?, ?> unboundedWildCardTypedConcurrentNavigableMap;
  private ConcurrentNavigableMap<? extends Number, ? extends Runnable>
      boundedWildCardTypedConcurrentNavigableMap;

  /*
   * Classes
   */

  private HashMap<?, ?> unboundedWildCardTypedHashMap;
  private HashMap<? extends Number, ? extends Runnable> boundedWildCardTypedHashMap;

  private Hashtable<?, ?> unboundedWildCardTypedHashtable;
  private Hashtable<? extends Number, ? extends Runnable> boundedWildCardTypedHashtable;

  private LinkedHashMap<?, ?> unboundedWildCardTypedHinkedHashMap;
  private LinkedHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedLinkedHashMap;

  private WeakHashMap<?, ?> unboundedWildCardTypedWeakHashMap;
  private WeakHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedWeakHashMap;

  private IdentityHashMap<?, ?> unboundedWildCardTypedIdentityHashMap;
  private IdentityHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedIdentityHashMap;

  private TreeMap<?, ?> unboundedWildCardTypedTreeMap;
  private TreeMap<? extends Number, ? extends Runnable> boundedWildCardTypedTreeMap;

  private ConcurrentSkipListMap<?, ?> unboundedWildCardTypedConcurrentSkipListMap;
  private ConcurrentSkipListMap<? extends Number, ? extends Runnable>
      boundedWildCardTypedConcurrentSkipListMap;

  public WildCardMapBean() {}

  public Map<?, ?> getUnboundedWildCardTypedMap() {
    return this.unboundedWildCardTypedMap;
  }

  public Map<? extends Number, ? extends Runnable> getBoundedWildCardTypedMap() {
    return this.boundedWildCardTypedMap;
  }

  public SortedMap<?, ?> getUnboundedWildCardTypedSortedMap() {
    return this.unboundedWildCardTypedSortedMap;
  }

  public SortedMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedSortedMap() {
    return this.boundedWildCardTypedSortedMap;
  }

  public NavigableMap<?, ?> getUnboundedWildCardTypedNavigableMap() {
    return this.unboundedWildCardTypedNavigableMap;
  }

  public NavigableMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedNavigableMap() {
    return this.boundedWildCardTypedNavigableMap;
  }

  public ConcurrentMap<?, ?> getUnboundedWildCardTypedConcurrentMap() {
    return this.unboundedWildCardTypedConcurrentMap;
  }

  public ConcurrentMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedConcurrentMap() {
    return this.boundedWildCardTypedConcurrentMap;
  }

  public ConcurrentNavigableMap<?, ?>
      getUnboundedWildCardTypedConcurrentNavigableMap() {
    return this.unboundedWildCardTypedConcurrentNavigableMap;
  }

  public ConcurrentNavigableMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedConcurrentNavigableMap() {
    return this.boundedWildCardTypedConcurrentNavigableMap;
  }

  public HashMap<?, ?> getUnboundedWildCardTypedHashMap() {
    return this.unboundedWildCardTypedHashMap;
  }

  public HashMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedHashMap() {
    return this.boundedWildCardTypedHashMap;
  }

  public Hashtable<?, ?> getUnboundedWildCardTypedHashtable() {
    return this.unboundedWildCardTypedHashtable;
  }

  public Hashtable<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedHashtable() {
    return this.boundedWildCardTypedHashtable;
  }

  public LinkedHashMap<?, ?> getUnboundedWildCardTypedHinkedHashMap() {
    return this.unboundedWildCardTypedHinkedHashMap;
  }

  public LinkedHashMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedLinkedHashMap() {
    return this.boundedWildCardTypedLinkedHashMap;
  }

  public WeakHashMap<?, ?> getUnboundedWildCardTypedWeakHashMap() {
    return this.unboundedWildCardTypedWeakHashMap;
  }

  public WeakHashMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedWeakHashMap() {
    return this.boundedWildCardTypedWeakHashMap;
  }

  public IdentityHashMap<?, ?> getUnboundedWildCardTypedIdentityHashMap() {
    return this.unboundedWildCardTypedIdentityHashMap;
  }

  public IdentityHashMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedIdentityHashMap() {
    return this.boundedWildCardTypedIdentityHashMap;
  }

  public TreeMap<?, ?> getUnboundedWildCardTypedTreeMap() {
    return this.unboundedWildCardTypedTreeMap;
  }

  public TreeMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedTreeMap() {
    return this.boundedWildCardTypedTreeMap;
  }

  public ConcurrentSkipListMap<?, ?> getUnboundedWildCardTypedConcurrentSkipListMap() {
    return this.unboundedWildCardTypedConcurrentSkipListMap;
  }

  public ConcurrentSkipListMap<? extends Number, ? extends Runnable>
      getBoundedWildCardTypedConcurrentSkipListMap() {
    return this.boundedWildCardTypedConcurrentSkipListMap;
  }

  public void setUnboundedWildCardTypedMap(
          final Map<?, ?> unboundedWildCardTypedMap) {
    this.unboundedWildCardTypedMap = unboundedWildCardTypedMap;
  }

  public void setBoundedWildCardTypedMap(
          final Map<? extends Number, ? extends Runnable> boundedWildCardTypedMap) {
    this.boundedWildCardTypedMap = boundedWildCardTypedMap;
  }

  public void setUnboundedWildCardTypedSortedMap(
          final SortedMap<?, ?> unboundedWildCardTypedSortedMap) {
    this.unboundedWildCardTypedSortedMap = unboundedWildCardTypedSortedMap;
  }

  public void setBoundedWildCardTypedSortedMap(
          final SortedMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedSortedMap) {
    this.boundedWildCardTypedSortedMap = boundedWildCardTypedSortedMap;
  }

  public void setUnboundedWildCardTypedNavigableMap(
          final NavigableMap<?, ?> unboundedWildCardTypedNavigableMap) {
    this.unboundedWildCardTypedNavigableMap = unboundedWildCardTypedNavigableMap;
  }

  public void setBoundedWildCardTypedNavigableMap(
          final NavigableMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedNavigableMap) {
    this.boundedWildCardTypedNavigableMap = boundedWildCardTypedNavigableMap;
  }

  public void setUnboundedWildCardTypedConcurrentMap(
          final ConcurrentMap<?, ?> unboundedWildCardTypedConcurrentMap) {
    this.unboundedWildCardTypedConcurrentMap = unboundedWildCardTypedConcurrentMap;
  }

  public void setBoundedWildCardTypedConcurrentMap(
          final ConcurrentMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedConcurrentMap) {
    this.boundedWildCardTypedConcurrentMap = boundedWildCardTypedConcurrentMap;
  }

  public void setUnboundedWildCardTypedConcurrentNavigableMap(
          final ConcurrentNavigableMap<?, ?>
              unboundedWildCardTypedConcurrentNavigableMap) {
    this.unboundedWildCardTypedConcurrentNavigableMap =
        unboundedWildCardTypedConcurrentNavigableMap;
  }

  public void setBoundedWildCardTypedConcurrentNavigableMap(
          final ConcurrentNavigableMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedConcurrentNavigableMap) {
    this.boundedWildCardTypedConcurrentNavigableMap = boundedWildCardTypedConcurrentNavigableMap;
  }

  public void setUnboundedWildCardTypedHashMap(
          final HashMap<?, ?> unboundedWildCardTypedHashMap) {
    this.unboundedWildCardTypedHashMap = unboundedWildCardTypedHashMap;
  }

  public void setBoundedWildCardTypedHashMap(
          final HashMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedHashMap) {
    this.boundedWildCardTypedHashMap = boundedWildCardTypedHashMap;
  }

  public void setUnboundedWildCardTypedHashtable(
          final Hashtable<?, ?> unboundedWildCardTypedHashtable) {
    this.unboundedWildCardTypedHashtable = unboundedWildCardTypedHashtable;
  }

  public void setBoundedWildCardTypedHashtable(
          final Hashtable<? extends Number, ? extends Runnable>
              boundedWildCardTypedHashtable) {
    this.boundedWildCardTypedHashtable = boundedWildCardTypedHashtable;
  }

  public void setUnboundedWildCardTypedHinkedHashMap(
          final LinkedHashMap<?, ?> unboundedWildCardTypedHinkedHashMap) {
    this.unboundedWildCardTypedHinkedHashMap = unboundedWildCardTypedHinkedHashMap;
  }

  public void setBoundedWildCardTypedLinkedHashMap(
          final LinkedHashMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedLinkedHashMap) {
    this.boundedWildCardTypedLinkedHashMap = boundedWildCardTypedLinkedHashMap;
  }

  public void setUnboundedWildCardTypedWeakHashMap(
          final WeakHashMap<?, ?> unboundedWildCardTypedWeakHashMap) {
    this.unboundedWildCardTypedWeakHashMap = unboundedWildCardTypedWeakHashMap;
  }

  public void setBoundedWildCardTypedWeakHashMap(
          final WeakHashMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedWeakHashMap) {
    this.boundedWildCardTypedWeakHashMap = boundedWildCardTypedWeakHashMap;
  }

  public void setUnboundedWildCardTypedIdentityHashMap(
          final IdentityHashMap<?, ?> unboundedWildCardTypedIdentityHashMap) {
    this.unboundedWildCardTypedIdentityHashMap = unboundedWildCardTypedIdentityHashMap;
  }

  public void setBoundedWildCardTypedIdentityHashMap(
          final IdentityHashMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedIdentityHashMap) {
    this.boundedWildCardTypedIdentityHashMap = boundedWildCardTypedIdentityHashMap;
  }

  public void setUnboundedWildCardTypedTreeMap(
          final TreeMap<?, ?> unboundedWildCardTypedTreeMap) {
    this.unboundedWildCardTypedTreeMap = unboundedWildCardTypedTreeMap;
  }

  public void setBoundedWildCardTypedTreeMap(
          final TreeMap<? extends Number, ? extends Runnable> boundedWildCardTypedTreeMap) {
    this.boundedWildCardTypedTreeMap = boundedWildCardTypedTreeMap;
  }

  public void setUnboundedWildCardTypedConcurrentSkipListMap(
          final ConcurrentSkipListMap<?, ?> unboundedWildCardTypedConcurrentSkipListMap) {
    this.unboundedWildCardTypedConcurrentSkipListMap = unboundedWildCardTypedConcurrentSkipListMap;
  }

  public void setBoundedWildCardTypedConcurrentSkipListMap(
          final ConcurrentSkipListMap<? extends Number, ? extends Runnable>
              boundedWildCardTypedConcurrentSkipListMap) {
    this.boundedWildCardTypedConcurrentSkipListMap = boundedWildCardTypedConcurrentSkipListMap;
  }
}
