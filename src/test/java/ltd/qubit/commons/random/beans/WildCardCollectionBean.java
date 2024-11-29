////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

public class WildCardCollectionBean {

  /*
   * Interfaces
   */
  private Collection<?> unboundedWildCardTypedCollection;
  private Collection<? extends Runnable> boundedWildCardTypedCollection;

  private Set<?> unboundedWildCardTypedSet;
  private Set<? extends Runnable> boundedWildCardTypedSet;

  private SortedSet<?> unboundedWildCardTypedSortedSet;
  private SortedSet<? extends Runnable> boundedWildCardTypedSortedSet;

  private NavigableSet<?> unboundedWildCardTypedNavigableSet;
  private NavigableSet<? extends Runnable> boundedWildCardTypedNavigableSet;

  private List<?> unboundedWildCardTypedList;
  private List<? extends Runnable> boundedWildCardTypedList;
  // https://github.com/j-easy/easy-random/issues/208
  private List<Comparable<? extends Object>> nestedBoundedWildCardTypedList;

  private Queue<?> unboundedWildCardTypedQueue;
  private Queue<? extends Runnable> boundedWildCardTypedQueue;

  private BlockingQueue<?> unboundedWildCardTypedBlockingQueue;
  private BlockingQueue<? extends Runnable> boundedWildCardTypedBlockingQueue;

  private TransferQueue<?> unboundedWildCardTypedTransferQueue;
  private TransferQueue<? extends Runnable> boundedWildCardTypedTransferQueue;

  private Deque<?> unboundedWildCardTypedDeque;
  private Deque<? extends Runnable> boundedWildCardTypedDeque;

  private BlockingDeque<?> unboundedWildCardTypedBlockingDeque;
  private BlockingDeque<? extends Runnable> boundedWildCardTypedBlockingDeque;

  /*
   * Classes
   */
  private ArrayList<?> unboundedWildCardTypedArrayList;
  private ArrayList<? extends Runnable> boundedWildCardTypedArrayList;

  private LinkedList<?> unboundedWildCardTypedLinkedList;
  private LinkedList<? extends Runnable> boundedWildCardTypedLinkedList;

  private Vector<?> unboundedWildCardTypedVector;
  private Vector<? extends Runnable> boundedWildCardTypedVector;

  private Stack<?> unboundedWildCardTypedStack;
  private Stack<? extends Runnable> boundedWildCardTypedStack;

  private HashSet<?> unboundedWildCardTypedHashSet;
  private HashSet<? extends Runnable> boundedWildCardTypedHashSet;

  private LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet;
  private LinkedHashSet<? extends Runnable> boundedWildCardTypedLinkedHashSet;

  private TreeSet<?> unboundedWildCardTypedTreeSet;
  private TreeSet<? extends Runnable> boundedWildCardTypedTreeSet;

  private ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet;
  private ConcurrentSkipListSet<? extends Runnable> boundedWildCardTypedConcurrentSkipListSet;

  private ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue;
  private ArrayBlockingQueue<? extends Runnable> boundedWildCardTypedArrayBlockingQueue;

  private LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue;
  private LinkedBlockingQueue<? extends Runnable> boundedWildCardTypedLinkedBlockingQueue;

  private ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue;
  private ConcurrentLinkedQueue<? extends Runnable> boundedWildCardTypedConcurrentLinkedQueue;

  private LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue;
  private LinkedTransferQueue<? extends Runnable> boundedWildCardTypedLinkedTransferQueue;

  private PriorityQueue<?> unboundedWildCardTypedPriorityQueue;
  private PriorityQueue<? extends Runnable> boundedWildCardTypedPriorityQueue;

  private PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue;
  private PriorityBlockingQueue<? extends Runnable> boundedWildCardTypedPriorityBlockingQueue;

  private ArrayDeque<?> unboundedWildCardTypedArrayDeque;
  private ArrayDeque<? extends Runnable> boundedWildCardTypedArrayDeque;

  private LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque;
  private LinkedBlockingDeque<? extends Runnable> boundedWildCardTypedLinkedBlockingDeque;

  private ConcurrentLinkedDeque<?> unboundedWildCardTypedConcurrentLinkedDeque;
  private ConcurrentLinkedDeque<? extends Runnable> boundedWildCardTypedConcurrentLinkedDeque;

  public WildCardCollectionBean() {
  }

  public Collection<?> getUnboundedWildCardTypedCollection() {
    return this.unboundedWildCardTypedCollection;
  }

  public Collection<? extends Runnable> getBoundedWildCardTypedCollection() {
    return this.boundedWildCardTypedCollection;
  }

  public Set<?> getUnboundedWildCardTypedSet() {
    return this.unboundedWildCardTypedSet;
  }

  public Set<? extends Runnable> getBoundedWildCardTypedSet() {
    return this.boundedWildCardTypedSet;
  }

  public SortedSet<?> getUnboundedWildCardTypedSortedSet() {
    return this.unboundedWildCardTypedSortedSet;
  }

  public SortedSet<? extends Runnable> getBoundedWildCardTypedSortedSet() {
    return this.boundedWildCardTypedSortedSet;
  }

  public NavigableSet<?> getUnboundedWildCardTypedNavigableSet() {
    return this.unboundedWildCardTypedNavigableSet;
  }

  public NavigableSet<? extends Runnable> getBoundedWildCardTypedNavigableSet() {
    return this.boundedWildCardTypedNavigableSet;
  }

  public List<?> getUnboundedWildCardTypedList() {
    return this.unboundedWildCardTypedList;
  }

  public List<? extends Runnable> getBoundedWildCardTypedList() {
    return this.boundedWildCardTypedList;
  }

  public List<Comparable<?>> getNestedBoundedWildCardTypedList() {
    return this.nestedBoundedWildCardTypedList;
  }

  public Queue<?> getUnboundedWildCardTypedQueue() {
    return this.unboundedWildCardTypedQueue;
  }

  public Queue<? extends Runnable> getBoundedWildCardTypedQueue() {
    return this.boundedWildCardTypedQueue;
  }

  public BlockingQueue<?> getUnboundedWildCardTypedBlockingQueue() {
    return this.unboundedWildCardTypedBlockingQueue;
  }

  public BlockingQueue<? extends Runnable> getBoundedWildCardTypedBlockingQueue() {
    return this.boundedWildCardTypedBlockingQueue;
  }

  public TransferQueue<?> getUnboundedWildCardTypedTransferQueue() {
    return this.unboundedWildCardTypedTransferQueue;
  }

  public TransferQueue<? extends Runnable> getBoundedWildCardTypedTransferQueue() {
    return this.boundedWildCardTypedTransferQueue;
  }

  public Deque<?> getUnboundedWildCardTypedDeque() {
    return this.unboundedWildCardTypedDeque;
  }

  public Deque<? extends Runnable> getBoundedWildCardTypedDeque() {
    return this.boundedWildCardTypedDeque;
  }

  public BlockingDeque<?> getUnboundedWildCardTypedBlockingDeque() {
    return this.unboundedWildCardTypedBlockingDeque;
  }

  public BlockingDeque<? extends Runnable> getBoundedWildCardTypedBlockingDeque() {
    return this.boundedWildCardTypedBlockingDeque;
  }

  public ArrayList<?> getUnboundedWildCardTypedArrayList() {
    return this.unboundedWildCardTypedArrayList;
  }

  public ArrayList<? extends Runnable> getBoundedWildCardTypedArrayList() {
    return this.boundedWildCardTypedArrayList;
  }

  public LinkedList<?> getUnboundedWildCardTypedLinkedList() {
    return this.unboundedWildCardTypedLinkedList;
  }

  public LinkedList<? extends Runnable> getBoundedWildCardTypedLinkedList() {
    return this.boundedWildCardTypedLinkedList;
  }

  public Vector<?> getUnboundedWildCardTypedVector() {
    return this.unboundedWildCardTypedVector;
  }

  public Vector<? extends Runnable> getBoundedWildCardTypedVector() {
    return this.boundedWildCardTypedVector;
  }

  public Stack<?> getUnboundedWildCardTypedStack() {
    return this.unboundedWildCardTypedStack;
  }

  public Stack<? extends Runnable> getBoundedWildCardTypedStack() {
    return this.boundedWildCardTypedStack;
  }

  public HashSet<?> getUnboundedWildCardTypedHashSet() {
    return this.unboundedWildCardTypedHashSet;
  }

  public HashSet<? extends Runnable> getBoundedWildCardTypedHashSet() {
    return this.boundedWildCardTypedHashSet;
  }

  public LinkedHashSet<?> getUnboundedWildCardTypedLinkedHashSet() {
    return this.unboundedWildCardTypedLinkedHashSet;
  }

  public LinkedHashSet<? extends Runnable> getBoundedWildCardTypedLinkedHashSet() {
    return this.boundedWildCardTypedLinkedHashSet;
  }

  public TreeSet<?> getUnboundedWildCardTypedTreeSet() {
    return this.unboundedWildCardTypedTreeSet;
  }

  public TreeSet<? extends Runnable> getBoundedWildCardTypedTreeSet() {
    return this.boundedWildCardTypedTreeSet;
  }

  public ConcurrentSkipListSet<?> getUnboundedWildCardTypedConcurrentSkipListSet() {
    return this.unboundedWildCardTypedConcurrentSkipListSet;
  }

  public ConcurrentSkipListSet<? extends Runnable> getBoundedWildCardTypedConcurrentSkipListSet() {
    return this.boundedWildCardTypedConcurrentSkipListSet;
  }

  public ArrayBlockingQueue<?> getUnboundedWildCardTypedArrayBlockingQueue() {
    return this.unboundedWildCardTypedArrayBlockingQueue;
  }

  public ArrayBlockingQueue<? extends Runnable> getBoundedWildCardTypedArrayBlockingQueue() {
    return this.boundedWildCardTypedArrayBlockingQueue;
  }

  public LinkedBlockingQueue<?> getUnboundedWildCardTypedLinkedBlockingQueue() {
    return this.unboundedWildCardTypedLinkedBlockingQueue;
  }

  public LinkedBlockingQueue<? extends Runnable> getBoundedWildCardTypedLinkedBlockingQueue() {
    return this.boundedWildCardTypedLinkedBlockingQueue;
  }

  public ConcurrentLinkedQueue<?> getUnboundedWildCardTypedConcurrentLinkedQueue() {
    return this.unboundedWildCardTypedConcurrentLinkedQueue;
  }

  public ConcurrentLinkedQueue<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedQueue() {
    return this.boundedWildCardTypedConcurrentLinkedQueue;
  }

  public LinkedTransferQueue<?> getUnboundedWildCardTypedLinkedTransferQueue() {
    return this.unboundedWildCardTypedLinkedTransferQueue;
  }

  public LinkedTransferQueue<? extends Runnable> getBoundedWildCardTypedLinkedTransferQueue() {
    return this.boundedWildCardTypedLinkedTransferQueue;
  }

  public PriorityQueue<?> getUnboundedWildCardTypedPriorityQueue() {
    return this.unboundedWildCardTypedPriorityQueue;
  }

  public PriorityQueue<? extends Runnable> getBoundedWildCardTypedPriorityQueue() {
    return this.boundedWildCardTypedPriorityQueue;
  }

  public PriorityBlockingQueue<?> getUnboundedWildCardTypedPriorityBlockingQueue() {
    return this.unboundedWildCardTypedPriorityBlockingQueue;
  }

  public PriorityBlockingQueue<? extends Runnable> getBoundedWildCardTypedPriorityBlockingQueue() {
    return this.boundedWildCardTypedPriorityBlockingQueue;
  }

  public ArrayDeque<?> getUnboundedWildCardTypedArrayDeque() {
    return this.unboundedWildCardTypedArrayDeque;
  }

  public ArrayDeque<? extends Runnable> getBoundedWildCardTypedArrayDeque() {
    return this.boundedWildCardTypedArrayDeque;
  }

  public LinkedBlockingDeque<?> getUnboundedWildCardTypedLinkedBlockingDeque() {
    return this.unboundedWildCardTypedLinkedBlockingDeque;
  }

  public LinkedBlockingDeque<? extends Runnable> getBoundedWildCardTypedLinkedBlockingDeque() {
    return this.boundedWildCardTypedLinkedBlockingDeque;
  }

  public ConcurrentLinkedDeque<?> getUnboundedWildCardTypedConcurrentLinkedDeque() {
    return this.unboundedWildCardTypedConcurrentLinkedDeque;
  }

  public ConcurrentLinkedDeque<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedDeque() {
    return this.boundedWildCardTypedConcurrentLinkedDeque;
  }

  public void setUnboundedWildCardTypedCollection(
          final Collection<?> unboundedWildCardTypedCollection) {
    this.unboundedWildCardTypedCollection = unboundedWildCardTypedCollection;
  }

  public void setBoundedWildCardTypedCollection(
          final Collection<? extends Runnable> boundedWildCardTypedCollection) {
    this.boundedWildCardTypedCollection = boundedWildCardTypedCollection;
  }

  public void setUnboundedWildCardTypedSet(final Set<?> unboundedWildCardTypedSet) {
    this.unboundedWildCardTypedSet = unboundedWildCardTypedSet;
  }

  public void setBoundedWildCardTypedSet(
          final Set<? extends Runnable> boundedWildCardTypedSet) {
    this.boundedWildCardTypedSet = boundedWildCardTypedSet;
  }

  public void setUnboundedWildCardTypedSortedSet(
          final SortedSet<?> unboundedWildCardTypedSortedSet) {
    this.unboundedWildCardTypedSortedSet = unboundedWildCardTypedSortedSet;
  }

  public void setBoundedWildCardTypedSortedSet(
          final SortedSet<? extends Runnable> boundedWildCardTypedSortedSet) {
    this.boundedWildCardTypedSortedSet = boundedWildCardTypedSortedSet;
  }

  public void setUnboundedWildCardTypedNavigableSet(
          final NavigableSet<?> unboundedWildCardTypedNavigableSet) {
    this.unboundedWildCardTypedNavigableSet = unboundedWildCardTypedNavigableSet;
  }

  public void setBoundedWildCardTypedNavigableSet(
          final NavigableSet<? extends Runnable> boundedWildCardTypedNavigableSet) {
    this.boundedWildCardTypedNavigableSet = boundedWildCardTypedNavigableSet;
  }

  public void setUnboundedWildCardTypedList(
          final List<?> unboundedWildCardTypedList) {
    this.unboundedWildCardTypedList = unboundedWildCardTypedList;
  }

  public void setBoundedWildCardTypedList(
          final List<? extends Runnable> boundedWildCardTypedList) {
    this.boundedWildCardTypedList = boundedWildCardTypedList;
  }

  public void setNestedBoundedWildCardTypedList(
          final List<Comparable<? extends Object>> nestedBoundedWildCardTypedList) {
    this.nestedBoundedWildCardTypedList = nestedBoundedWildCardTypedList;
  }

  public void setUnboundedWildCardTypedQueue(
          final Queue<?> unboundedWildCardTypedQueue) {
    this.unboundedWildCardTypedQueue = unboundedWildCardTypedQueue;
  }

  public void setBoundedWildCardTypedQueue(
          final Queue<? extends Runnable> boundedWildCardTypedQueue) {
    this.boundedWildCardTypedQueue = boundedWildCardTypedQueue;
  }

  public void setUnboundedWildCardTypedBlockingQueue(
          final BlockingQueue<?> unboundedWildCardTypedBlockingQueue) {
    this.unboundedWildCardTypedBlockingQueue = unboundedWildCardTypedBlockingQueue;
  }

  public void setBoundedWildCardTypedBlockingQueue(
          final BlockingQueue<? extends Runnable> boundedWildCardTypedBlockingQueue) {
    this.boundedWildCardTypedBlockingQueue = boundedWildCardTypedBlockingQueue;
  }

  public void setUnboundedWildCardTypedTransferQueue(
          final TransferQueue<?> unboundedWildCardTypedTransferQueue) {
    this.unboundedWildCardTypedTransferQueue = unboundedWildCardTypedTransferQueue;
  }

  public void setBoundedWildCardTypedTransferQueue(
          final TransferQueue<? extends Runnable> boundedWildCardTypedTransferQueue) {
    this.boundedWildCardTypedTransferQueue = boundedWildCardTypedTransferQueue;
  }

  public void setUnboundedWildCardTypedDeque(
          final Deque<?> unboundedWildCardTypedDeque) {
    this.unboundedWildCardTypedDeque = unboundedWildCardTypedDeque;
  }

  public void setBoundedWildCardTypedDeque(
          final Deque<? extends Runnable> boundedWildCardTypedDeque) {
    this.boundedWildCardTypedDeque = boundedWildCardTypedDeque;
  }

  public void setUnboundedWildCardTypedBlockingDeque(
          final BlockingDeque<?> unboundedWildCardTypedBlockingDeque) {
    this.unboundedWildCardTypedBlockingDeque = unboundedWildCardTypedBlockingDeque;
  }

  public void setBoundedWildCardTypedBlockingDeque(
          final BlockingDeque<? extends Runnable> boundedWildCardTypedBlockingDeque) {
    this.boundedWildCardTypedBlockingDeque = boundedWildCardTypedBlockingDeque;
  }

  public void setUnboundedWildCardTypedArrayList(
          final ArrayList<?> unboundedWildCardTypedArrayList) {
    this.unboundedWildCardTypedArrayList = unboundedWildCardTypedArrayList;
  }

  public void setBoundedWildCardTypedArrayList(
          final ArrayList<? extends Runnable> boundedWildCardTypedArrayList) {
    this.boundedWildCardTypedArrayList = boundedWildCardTypedArrayList;
  }

  public void setUnboundedWildCardTypedLinkedList(
          final LinkedList<?> unboundedWildCardTypedLinkedList) {
    this.unboundedWildCardTypedLinkedList = unboundedWildCardTypedLinkedList;
  }

  public void setBoundedWildCardTypedLinkedList(
          final LinkedList<? extends Runnable> boundedWildCardTypedLinkedList) {
    this.boundedWildCardTypedLinkedList = boundedWildCardTypedLinkedList;
  }

  public void setUnboundedWildCardTypedVector(
          final Vector<?> unboundedWildCardTypedVector) {
    this.unboundedWildCardTypedVector = unboundedWildCardTypedVector;
  }

  public void setBoundedWildCardTypedVector(
          final Vector<? extends Runnable> boundedWildCardTypedVector) {
    this.boundedWildCardTypedVector = boundedWildCardTypedVector;
  }

  public void setUnboundedWildCardTypedStack(
          final Stack<?> unboundedWildCardTypedStack) {
    this.unboundedWildCardTypedStack = unboundedWildCardTypedStack;
  }

  public void setBoundedWildCardTypedStack(
          final Stack<? extends Runnable> boundedWildCardTypedStack) {
    this.boundedWildCardTypedStack = boundedWildCardTypedStack;
  }

  public void setUnboundedWildCardTypedHashSet(
          final HashSet<?> unboundedWildCardTypedHashSet) {
    this.unboundedWildCardTypedHashSet = unboundedWildCardTypedHashSet;
  }

  public void setBoundedWildCardTypedHashSet(
          final HashSet<? extends Runnable> boundedWildCardTypedHashSet) {
    this.boundedWildCardTypedHashSet = boundedWildCardTypedHashSet;
  }

  public void setUnboundedWildCardTypedLinkedHashSet(
          final LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet) {
    this.unboundedWildCardTypedLinkedHashSet = unboundedWildCardTypedLinkedHashSet;
  }

  public void setBoundedWildCardTypedLinkedHashSet(
          final LinkedHashSet<? extends Runnable> boundedWildCardTypedLinkedHashSet) {
    this.boundedWildCardTypedLinkedHashSet = boundedWildCardTypedLinkedHashSet;
  }

  public void setUnboundedWildCardTypedTreeSet(
          final TreeSet<?> unboundedWildCardTypedTreeSet) {
    this.unboundedWildCardTypedTreeSet = unboundedWildCardTypedTreeSet;
  }

  public void setBoundedWildCardTypedTreeSet(
          final TreeSet<? extends Runnable> boundedWildCardTypedTreeSet) {
    this.boundedWildCardTypedTreeSet = boundedWildCardTypedTreeSet;
  }

  public void setUnboundedWildCardTypedConcurrentSkipListSet(
          final ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet) {
    this.unboundedWildCardTypedConcurrentSkipListSet =
        unboundedWildCardTypedConcurrentSkipListSet;
  }

  public void setBoundedWildCardTypedConcurrentSkipListSet(
          final ConcurrentSkipListSet<? extends Runnable>
              boundedWildCardTypedConcurrentSkipListSet) {
    this.boundedWildCardTypedConcurrentSkipListSet =
        boundedWildCardTypedConcurrentSkipListSet;
  }

  public void setUnboundedWildCardTypedArrayBlockingQueue(
          final ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue) {
    this.unboundedWildCardTypedArrayBlockingQueue = unboundedWildCardTypedArrayBlockingQueue;
  }

  public void setBoundedWildCardTypedArrayBlockingQueue(
          final ArrayBlockingQueue<? extends Runnable>
              boundedWildCardTypedArrayBlockingQueue) {
    this.boundedWildCardTypedArrayBlockingQueue = boundedWildCardTypedArrayBlockingQueue;
  }

  public void setUnboundedWildCardTypedLinkedBlockingQueue(
          final LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue) {
    this.unboundedWildCardTypedLinkedBlockingQueue =
        unboundedWildCardTypedLinkedBlockingQueue;
  }

  public void setBoundedWildCardTypedLinkedBlockingQueue(
          final LinkedBlockingQueue<? extends Runnable>
              boundedWildCardTypedLinkedBlockingQueue) {
    this.boundedWildCardTypedLinkedBlockingQueue = boundedWildCardTypedLinkedBlockingQueue;
  }

  public void setUnboundedWildCardTypedConcurrentLinkedQueue(
          final ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue) {
    this.unboundedWildCardTypedConcurrentLinkedQueue =
        unboundedWildCardTypedConcurrentLinkedQueue;
  }

  public void setBoundedWildCardTypedConcurrentLinkedQueue(
          final ConcurrentLinkedQueue<? extends Runnable>
              boundedWildCardTypedConcurrentLinkedQueue) {
    this.boundedWildCardTypedConcurrentLinkedQueue =
        boundedWildCardTypedConcurrentLinkedQueue;
  }

  public void setUnboundedWildCardTypedLinkedTransferQueue(
          final LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue) {
    this.unboundedWildCardTypedLinkedTransferQueue =
        unboundedWildCardTypedLinkedTransferQueue;
  }

  public void setBoundedWildCardTypedLinkedTransferQueue(
          final LinkedTransferQueue<? extends Runnable>
              boundedWildCardTypedLinkedTransferQueue) {
    this.boundedWildCardTypedLinkedTransferQueue =
        boundedWildCardTypedLinkedTransferQueue;
  }

  public void setUnboundedWildCardTypedPriorityQueue(
          final PriorityQueue<?> unboundedWildCardTypedPriorityQueue) {
    this.unboundedWildCardTypedPriorityQueue = unboundedWildCardTypedPriorityQueue;
  }

  public void setBoundedWildCardTypedPriorityQueue(
          final PriorityQueue<? extends Runnable> boundedWildCardTypedPriorityQueue) {
    this.boundedWildCardTypedPriorityQueue = boundedWildCardTypedPriorityQueue;
  }

  public void setUnboundedWildCardTypedPriorityBlockingQueue(
          final PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue) {
    this.unboundedWildCardTypedPriorityBlockingQueue =
        unboundedWildCardTypedPriorityBlockingQueue;
  }

  public void setBoundedWildCardTypedPriorityBlockingQueue(
          final PriorityBlockingQueue<? extends Runnable>
              boundedWildCardTypedPriorityBlockingQueue) {
    this.boundedWildCardTypedPriorityBlockingQueue =
        boundedWildCardTypedPriorityBlockingQueue;
  }

  public void setUnboundedWildCardTypedArrayDeque(
          final ArrayDeque<?> unboundedWildCardTypedArrayDeque) {
    this.unboundedWildCardTypedArrayDeque = unboundedWildCardTypedArrayDeque;
  }

  public void setBoundedWildCardTypedArrayDeque(
          final ArrayDeque<? extends Runnable> boundedWildCardTypedArrayDeque) {
    this.boundedWildCardTypedArrayDeque = boundedWildCardTypedArrayDeque;
  }

  public void setUnboundedWildCardTypedLinkedBlockingDeque(
          final LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque) {
    this.unboundedWildCardTypedLinkedBlockingDeque =
        unboundedWildCardTypedLinkedBlockingDeque;
  }

  public void setBoundedWildCardTypedLinkedBlockingDeque(
          final LinkedBlockingDeque<? extends Runnable>
              boundedWildCardTypedLinkedBlockingDeque) {
    this.boundedWildCardTypedLinkedBlockingDeque =
        boundedWildCardTypedLinkedBlockingDeque;
  }

  public void setUnboundedWildCardTypedConcurrentLinkedDeque(
          final ConcurrentLinkedDeque<?>
              unboundedWildCardTypedConcurrentLinkedDeque) {
    this.unboundedWildCardTypedConcurrentLinkedDeque =
        unboundedWildCardTypedConcurrentLinkedDeque;
  }

  public void setBoundedWildCardTypedConcurrentLinkedDeque(
          final ConcurrentLinkedDeque<? extends Runnable>
              boundedWildCardTypedConcurrentLinkedDeque) {
    this.boundedWildCardTypedConcurrentLinkedDeque =
        boundedWildCardTypedConcurrentLinkedDeque;
  }
}
