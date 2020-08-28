package com.mikey.aop.trees.datastructures.queue;

/**
 * This class is used for storing the index in the tree that will be unmarked.
 * @author Michael Nicholson
 */
public class Unmark extends QueueNode{

    private int index;

    /**
     * The sole constructor for this class.
     * @param index The index being unmarked.
     */
    public Unmark(int index) {
        this.index = index;
    }

    /**
     * Getter for the index being unmarked.
     * @return The index being unmarked.
     */
    public int getIndex() {
        return index;
    }
}
