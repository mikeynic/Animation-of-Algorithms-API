package com.mikey.aop.trees.datastructures.queue;

/**
 * This class is used for storing the index in the tree array for deletion.
 * @author Michael Nicholson
 */
public class  Deletion extends QueueNode{

    private int index;

    /**
     * The sole constructor for this class.
     * @param index The index of the element in the tree array that will be deleted.
     */
    public Deletion(int index) {
        this.index = index;
    }

    /**
     * Getter for the deletion index.
     * @return The index of the element to be deleted.
     */
    public int getIndex() {
        return index;
    }
}
