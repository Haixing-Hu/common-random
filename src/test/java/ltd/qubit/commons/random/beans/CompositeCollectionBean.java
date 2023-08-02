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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class CompositeCollectionBean {

  private List<List<String>> listOfLists;
  private ArrayList<LinkedList<Person>> typedListOfLists;

  private Set<Set<String>> setOfSets;
  private HashSet<LinkedHashSet<String>> typedSetOfSets;

  private Queue<Queue<String>> queueOfQueues;
  private LinkedBlockingQueue<Queue<String>> typedQueueOdQueues;

  public CompositeCollectionBean() {
  }

  public List<List<String>> getListOfLists() {
    return this.listOfLists;
  }

  public ArrayList<LinkedList<Person>> getTypedListOfLists() {
    return this.typedListOfLists;
  }

  public Set<Set<String>> getSetOfSets() {
    return this.setOfSets;
  }

  public HashSet<LinkedHashSet<String>> getTypedSetOfSets() {
    return this.typedSetOfSets;
  }

  public Queue<Queue<String>> getQueueOfQueues() {
    return this.queueOfQueues;
  }

  public LinkedBlockingQueue<Queue<String>> getTypedQueueOdQueues() {
    return this.typedQueueOdQueues;
  }

  public void setListOfLists(final List<List<String>> listOfLists) {
    this.listOfLists = listOfLists;
  }

  public void setTypedListOfLists(
          final ArrayList<LinkedList<Person>> typedListOfLists) {
    this.typedListOfLists = typedListOfLists;
  }

  public void setSetOfSets(final Set<Set<String>> setOfSets) {
    this.setOfSets = setOfSets;
  }

  public void setTypedSetOfSets(final HashSet<LinkedHashSet<String>> typedSetOfSets) {
    this.typedSetOfSets = typedSetOfSets;
  }

  public void setQueueOfQueues(final Queue<Queue<String>> queueOfQueues) {
    this.queueOfQueues = queueOfQueues;
  }

  public void setTypedQueueOdQueues(
          final LinkedBlockingQueue<Queue<String>> typedQueueOdQueues) {
    this.typedQueueOdQueues = typedQueueOdQueues;
  }
}
