package com.mikey.aop.sorting.datastructures;

/**
 * This class is used for storing the indices for comparison.
 * Once these indices are obtained, they shall be used to mark the nodes on the Canvas with a specified colour.
 * @author Michael Nicholson
 */
public class Compare extends SortingQueueNode {

    private int compareIndex1;
    private int compareIndex2;

    /**
     * This is the sole constructor for this class.
     * @param compareIndex1 An index used for comparison.
     * @param compareIndex2 An index used for comparison.
     */
    public Compare(int compareIndex1, int compareIndex2) {
        this.compareIndex1 = compareIndex1;
        this.compareIndex2 = compareIndex2;
    }

    /**
     * Getter for the compareIndex1
     * @return compareIndex1
     */
    public int getCompareIndex1() {
        return compareIndex1;
    }

    /**
     * Getter for the compareIndex2
     * @return compareIndex2
     */
    public int getCompareIndex2() {
        return compareIndex2;
    }
}
