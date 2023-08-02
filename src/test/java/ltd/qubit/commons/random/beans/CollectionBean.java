////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TransferQueue;

@SuppressWarnings("rawtypes")
public class CollectionBean {

  /*
   * Interfaces
   */

  private Collection collection;
  private Collection<Person> typedCollection;

  private Set set;
  private Set<Person> typedSet;

  private SortedSet sortedSet;
  private SortedSet<Person> typedSortedSet;

  private NavigableSet navigableSet;
  private NavigableSet<Person> typedNavigableSet;

  private List list;
  private List<Person> typedList;

  private Queue queue;
  private Queue<Person> typedQueue;

  private BlockingQueue blockingQueue;
  private BlockingQueue<Person> typedBlockingQueue;

  private TransferQueue transferQueue;
  private TransferQueue<Person> typedTransferQueue;

  private Deque deque;
  private Deque<Person> typedDeque;

  private BlockingDeque blockingDeque;
  private BlockingDeque<Person> typedBlockingDeque;

  /*
   * Classes
   */

  private ArrayList arrayList;
  private ArrayList<Person> typedArrayList;

  private LinkedList linkedList;
  private LinkedList<Person> typedLinkedList;

  private Vector vector;
  private Vector<Person> typedVector;

  private Stack stack;
  private Stack<Person> typedStack;

  private HashSet hashSet;
  private HashSet<Person> typedHashSet;

  private LinkedHashSet linkedHashSet;
  private LinkedHashSet<Person> typedLinkedHashSet;

  private TreeSet treeSet;
  private TreeSet<Person> typedTreeSet;

  private ConcurrentSkipListSet concurrentSkipListSet;
  private ConcurrentSkipListSet<Person> typedConcurrentSkipListSet;

  private ArrayBlockingQueue arrayBlockingQueue;
  private ArrayBlockingQueue<Person> typedArrayBlockingQueue;

  private LinkedBlockingQueue linkedBlockingQueue;
  private LinkedBlockingQueue<Person> typedLinkedBlockingQueue;

  private ConcurrentLinkedQueue concurrentLinkedQueue;
  private ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue;

  private LinkedTransferQueue linkedTransferQueue;
  private LinkedTransferQueue<Person> typedLinkedTransferQueue;

  private PriorityQueue priorityQueue;
  private PriorityQueue<Person> typedPriorityQueue;

  private PriorityBlockingQueue priorityBlockingQueue;
  private PriorityBlockingQueue<Person> typedPriorityBlockingQueue;

  private ArrayDeque arrayDeque;
  private ArrayDeque<Person> typedArrayDeque;

  private LinkedBlockingDeque linkedBlockingDeque;
  private LinkedBlockingDeque<Person> typedLinkedBlockingDeque;

  private ConcurrentLinkedDeque concurrentLinkedDeque;
  private ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque;

  public CollectionBean() {
  }

  public Collection getCollection() {
    return this.collection;
  }

  public Collection<Person> getTypedCollection() {
    return this.typedCollection;
  }

  public Set getSet() {
    return this.set;
  }

  public Set<Person> getTypedSet() {
    return this.typedSet;
  }

  public SortedSet getSortedSet() {
    return this.sortedSet;
  }

  public SortedSet<Person> getTypedSortedSet() {
    return this.typedSortedSet;
  }

  public NavigableSet getNavigableSet() {
    return this.navigableSet;
  }

  public NavigableSet<Person> getTypedNavigableSet() {
    return this.typedNavigableSet;
  }

  public List getList() {
    return this.list;
  }

  public List<Person> getTypedList() {
    return this.typedList;
  }

  public Queue getQueue() {
    return this.queue;
  }

  public Queue<Person> getTypedQueue() {
    return this.typedQueue;
  }

  public BlockingQueue getBlockingQueue() {
    return this.blockingQueue;
  }

  public BlockingQueue<Person> getTypedBlockingQueue() {
    return this.typedBlockingQueue;
  }

  public TransferQueue getTransferQueue() {
    return this.transferQueue;
  }

  public TransferQueue<Person> getTypedTransferQueue() {
    return this.typedTransferQueue;
  }

  public Deque getDeque() {
    return this.deque;
  }

  public Deque<Person> getTypedDeque() {
    return this.typedDeque;
  }

  public BlockingDeque getBlockingDeque() {
    return this.blockingDeque;
  }

  public BlockingDeque<Person> getTypedBlockingDeque() {
    return this.typedBlockingDeque;
  }

  public ArrayList getArrayList() {
    return this.arrayList;
  }

  public ArrayList<Person> getTypedArrayList() {
    return this.typedArrayList;
  }

  public LinkedList getLinkedList() {
    return this.linkedList;
  }

  public LinkedList<Person> getTypedLinkedList() {
    return this.typedLinkedList;
  }

  public Vector getVector() {
    return this.vector;
  }

  public Vector<Person> getTypedVector() {
    return this.typedVector;
  }

  public Stack getStack() {
    return this.stack;
  }

  public Stack<Person> getTypedStack() {
    return this.typedStack;
  }

  public HashSet getHashSet() {
    return this.hashSet;
  }

  public HashSet<Person> getTypedHashSet() {
    return this.typedHashSet;
  }

  public LinkedHashSet getLinkedHashSet() {
    return this.linkedHashSet;
  }

  public LinkedHashSet<Person> getTypedLinkedHashSet() {
    return this.typedLinkedHashSet;
  }

  public TreeSet getTreeSet() {
    return this.treeSet;
  }

  public TreeSet<Person> getTypedTreeSet() {
    return this.typedTreeSet;
  }

  public ConcurrentSkipListSet getConcurrentSkipListSet() {
    return this.concurrentSkipListSet;
  }

  public ConcurrentSkipListSet<Person> getTypedConcurrentSkipListSet() {
    return this.typedConcurrentSkipListSet;
  }

  public ArrayBlockingQueue getArrayBlockingQueue() {
    return this.arrayBlockingQueue;
  }

  public ArrayBlockingQueue<Person> getTypedArrayBlockingQueue() {
    return this.typedArrayBlockingQueue;
  }

  public LinkedBlockingQueue getLinkedBlockingQueue() {
    return this.linkedBlockingQueue;
  }

  public LinkedBlockingQueue<Person> getTypedLinkedBlockingQueue() {
    return this.typedLinkedBlockingQueue;
  }

  public ConcurrentLinkedQueue getConcurrentLinkedQueue() {
    return this.concurrentLinkedQueue;
  }

  public ConcurrentLinkedQueue<Person> getTypedConcurrentLinkedQueue() {
    return this.typedConcurrentLinkedQueue;
  }

  public LinkedTransferQueue getLinkedTransferQueue() {
    return this.linkedTransferQueue;
  }

  public LinkedTransferQueue<Person> getTypedLinkedTransferQueue() {
    return this.typedLinkedTransferQueue;
  }

  public PriorityQueue getPriorityQueue() {
    return this.priorityQueue;
  }

  public PriorityQueue<Person> getTypedPriorityQueue() {
    return this.typedPriorityQueue;
  }

  public PriorityBlockingQueue getPriorityBlockingQueue() {
    return this.priorityBlockingQueue;
  }

  public PriorityBlockingQueue<Person> getTypedPriorityBlockingQueue() {
    return this.typedPriorityBlockingQueue;
  }

  public ArrayDeque getArrayDeque() {
    return this.arrayDeque;
  }

  public ArrayDeque<Person> getTypedArrayDeque() {
    return this.typedArrayDeque;
  }

  public LinkedBlockingDeque getLinkedBlockingDeque() {
    return this.linkedBlockingDeque;
  }

  public LinkedBlockingDeque<Person> getTypedLinkedBlockingDeque() {
    return this.typedLinkedBlockingDeque;
  }

  public ConcurrentLinkedDeque getConcurrentLinkedDeque() {
    return this.concurrentLinkedDeque;
  }

  public ConcurrentLinkedDeque<Person> getTypedConcurrentLinkedDeque() {
    return this.typedConcurrentLinkedDeque;
  }

  public void setCollection(final Collection collection) {
    this.collection = collection;
  }

  public void setTypedCollection(final Collection<Person> typedCollection) {
    this.typedCollection = typedCollection;
  }

  public void setSet(final Set set) {
    this.set = set;
  }

  public void setTypedSet(final Set<Person> typedSet) {
    this.typedSet = typedSet;
  }

  public void setSortedSet(final SortedSet sortedSet) {
    this.sortedSet = sortedSet;
  }

  public void setTypedSortedSet(final SortedSet<Person> typedSortedSet) {
    this.typedSortedSet = typedSortedSet;
  }

  public void setNavigableSet(final NavigableSet navigableSet) {
    this.navigableSet = navigableSet;
  }

  public void setTypedNavigableSet(final NavigableSet<Person> typedNavigableSet) {
    this.typedNavigableSet = typedNavigableSet;
  }

  public void setList(final List list) {
    this.list = list;
  }

  public void setTypedList(final List<Person> typedList) {
    this.typedList = typedList;
  }

  public void setQueue(final Queue queue) {
    this.queue = queue;
  }

  public void setTypedQueue(final Queue<Person> typedQueue) {
    this.typedQueue = typedQueue;
  }

  public void setBlockingQueue(final BlockingQueue blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  public void setTypedBlockingQueue(final BlockingQueue<Person> typedBlockingQueue) {
    this.typedBlockingQueue = typedBlockingQueue;
  }

  public void setTransferQueue(final TransferQueue transferQueue) {
    this.transferQueue = transferQueue;
  }

  public void setTypedTransferQueue(final TransferQueue<Person> typedTransferQueue) {
    this.typedTransferQueue = typedTransferQueue;
  }

  public void setDeque(final Deque deque) {
    this.deque = deque;
  }

  public void setTypedDeque(final Deque<Person> typedDeque) {
    this.typedDeque = typedDeque;
  }

  public void setBlockingDeque(final BlockingDeque blockingDeque) {
    this.blockingDeque = blockingDeque;
  }

  public void setTypedBlockingDeque(final BlockingDeque<Person> typedBlockingDeque) {
    this.typedBlockingDeque = typedBlockingDeque;
  }

  public void setArrayList(final ArrayList arrayList) {
    this.arrayList = arrayList;
  }

  public void setTypedArrayList(final ArrayList<Person> typedArrayList) {
    this.typedArrayList = typedArrayList;
  }

  public void setLinkedList(final LinkedList linkedList) {
    this.linkedList = linkedList;
  }

  public void setTypedLinkedList(final LinkedList<Person> typedLinkedList) {
    this.typedLinkedList = typedLinkedList;
  }

  public void setVector(final Vector vector) {
    this.vector = vector;
  }

  public void setTypedVector(final Vector<Person> typedVector) {
    this.typedVector = typedVector;
  }

  public void setStack(final Stack stack) {
    this.stack = stack;
  }

  public void setTypedStack(final Stack<Person> typedStack) {
    this.typedStack = typedStack;
  }

  public void setHashSet(final HashSet hashSet) {
    this.hashSet = hashSet;
  }

  public void setTypedHashSet(final HashSet<Person> typedHashSet) {
    this.typedHashSet = typedHashSet;
  }

  public void setLinkedHashSet(final LinkedHashSet linkedHashSet) {
    this.linkedHashSet = linkedHashSet;
  }

  public void setTypedLinkedHashSet(final LinkedHashSet<Person> typedLinkedHashSet) {
    this.typedLinkedHashSet = typedLinkedHashSet;
  }

  public void setTreeSet(final TreeSet treeSet) {
    this.treeSet = treeSet;
  }

  public void setTypedTreeSet(final TreeSet<Person> typedTreeSet) {
    this.typedTreeSet = typedTreeSet;
  }

  public void setConcurrentSkipListSet(
          final ConcurrentSkipListSet concurrentSkipListSet) {
    this.concurrentSkipListSet = concurrentSkipListSet;
  }

  public void setTypedConcurrentSkipListSet(
          final ConcurrentSkipListSet<Person> typedConcurrentSkipListSet) {
    this.typedConcurrentSkipListSet = typedConcurrentSkipListSet;
  }

  public void setArrayBlockingQueue(final ArrayBlockingQueue arrayBlockingQueue) {
    this.arrayBlockingQueue = arrayBlockingQueue;
  }

  public void setTypedArrayBlockingQueue(
          final ArrayBlockingQueue<Person> typedArrayBlockingQueue) {
    this.typedArrayBlockingQueue = typedArrayBlockingQueue;
  }

  public void setLinkedBlockingQueue(final LinkedBlockingQueue linkedBlockingQueue) {
    this.linkedBlockingQueue = linkedBlockingQueue;
  }

  public void setTypedLinkedBlockingQueue(
          final LinkedBlockingQueue<Person> typedLinkedBlockingQueue) {
    this.typedLinkedBlockingQueue = typedLinkedBlockingQueue;
  }

  public void setConcurrentLinkedQueue(
          final ConcurrentLinkedQueue concurrentLinkedQueue) {
    this.concurrentLinkedQueue = concurrentLinkedQueue;
  }

  public void setTypedConcurrentLinkedQueue(
          final ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue) {
    this.typedConcurrentLinkedQueue = typedConcurrentLinkedQueue;
  }

  public void setLinkedTransferQueue(final LinkedTransferQueue linkedTransferQueue) {
    this.linkedTransferQueue = linkedTransferQueue;
  }

  public void setTypedLinkedTransferQueue(
          final LinkedTransferQueue<Person> typedLinkedTransferQueue) {
    this.typedLinkedTransferQueue = typedLinkedTransferQueue;
  }

  public void setPriorityQueue(final PriorityQueue priorityQueue) {
    this.priorityQueue = priorityQueue;
  }

  public void setTypedPriorityQueue(final PriorityQueue<Person> typedPriorityQueue) {
    this.typedPriorityQueue = typedPriorityQueue;
  }

  public void setPriorityBlockingQueue(
          final PriorityBlockingQueue priorityBlockingQueue) {
    this.priorityBlockingQueue = priorityBlockingQueue;
  }

  public void setTypedPriorityBlockingQueue(
          final PriorityBlockingQueue<Person> typedPriorityBlockingQueue) {
    this.typedPriorityBlockingQueue = typedPriorityBlockingQueue;
  }

  public void setArrayDeque(final ArrayDeque arrayDeque) {
    this.arrayDeque = arrayDeque;
  }

  public void setTypedArrayDeque(final ArrayDeque<Person> typedArrayDeque) {
    this.typedArrayDeque = typedArrayDeque;
  }

  public void setLinkedBlockingDeque(final LinkedBlockingDeque linkedBlockingDeque) {
    this.linkedBlockingDeque = linkedBlockingDeque;
  }

  public void setTypedLinkedBlockingDeque(
          final LinkedBlockingDeque<Person> typedLinkedBlockingDeque) {
    this.typedLinkedBlockingDeque = typedLinkedBlockingDeque;
  }

  public void setConcurrentLinkedDeque(
          final ConcurrentLinkedDeque concurrentLinkedDeque) {
    this.concurrentLinkedDeque = concurrentLinkedDeque;
  }

  public void setTypedConcurrentLinkedDeque(
          final ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque) {
    this.typedConcurrentLinkedDeque = typedConcurrentLinkedDeque;
  }
}
