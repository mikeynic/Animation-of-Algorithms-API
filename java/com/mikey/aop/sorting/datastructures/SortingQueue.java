package com.mikey.aop.sorting.datastructures;

import java.util.LinkedList;

/**
 * Class definition to enforce the type of the internal queue used for storing sorting operations. Each entry in this
 * linked list will use polymorphism to take the shape of a specific operation. The objects stored in this list can
 * therefore be any of: SortingQueueNode (this only stores a message), Mark, Compare, Swap or SetArray.
 * @author Michael Nicholson
 */
public class SortingQueue extends LinkedList<SortingQueueNode> {
}
