package com.mikey.aop.stringmatching.datastructures;

import java.util.LinkedList;

/**
 * Class definition to enforce the type of the internal queue used for storing sorting operations. Each entry in this
 * linked list will use polymorphism to take the shape of a specific operation. The objects stored in this list can
 * therefore be any of: SMQueueNode (this only stores a message), SMComparing, SMEqual, SMMoving or SMNotEqual.
 * @author Michael Nicholson
 */
public class SMQueue extends LinkedList<SMQueueNode> {
}
