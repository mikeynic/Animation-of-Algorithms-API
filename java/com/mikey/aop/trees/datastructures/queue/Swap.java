package com.mikey.aop.trees.datastructures.queue;

/**
 * This class is used for storing two indices used in the swapping of two elements
 * @author Michael Nicholson
 */
public class Swap extends QueueNode{

    private int index1;
    private int index2;

    /**
     * The sole constructor for this class.
     * @param index1 An index in the array being swapped. Order is irrelevant.
     * @param index2 An index in the array being swapped. Order is irrelevant.
     */
    public Swap(int index1, int index2) {
        this.index1 = index1;
        this.index2 = index2;
    }

    /**
     * Getter for the first index.
     * @return The first index.
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * Getter for the second index.
     * @return The second index.
     */
    public int getIndex2() {
        return index2;
    }
}
