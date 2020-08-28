package com.mikey.aop.trees.datastructures.queue;

/**
 * This class is used to store the index for insertion and the element that is to be inserted.
 * @author Michael Nicholson
 */
public class Insertion extends QueueNode{

    private int element;
    private int index;

    /** The sole constructor for this class.
     * @param element   The element that will be inserted in to the tree array
     * @param index     The index in the tree array that the element will be inserted at.
     */
    public Insertion(int element, int index) {
        this.element = element;
        this.index = index;
    }

    /**
     * Getter for the element being inserted.
     * @return The element to be inserted.
     */
    public int getElement() {
        return element;
    }

    /**
     * Getter for the index of the element being inserted.
     * @return The index.
     */
    public int getIndex() {
        return index;
    }
}
