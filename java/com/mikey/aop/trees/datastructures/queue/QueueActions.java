package com.mikey.aop.trees.datastructures.queue;

import java.util.LinkedList;

/**
 * This class enforces the type of object that the QueueActions linked list contains. Each entry in this linked list
 * will use polymorphism to take the shape of a specific operation. The objects stored in this list can therefore
 * be any of: QueueNode (this just contains a message), Deletion, Insertion, Mark, SetArray, Swap or Unmark.
 * @author Michael Nicholson
 */
public class QueueActions extends LinkedList<QueueNode> {
}
