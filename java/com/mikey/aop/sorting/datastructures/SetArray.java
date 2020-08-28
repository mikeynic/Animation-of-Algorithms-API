package com.mikey.aop.sorting.datastructures;

/**
 * An object that stores the initial array before any operations occur.
 */
public class SetArray extends SortingQueueNode{

    int[] array;

    /**
     * The sole constructor for this object.
     * @param array The array that is used in initial setup of the algorithm.
     */
    public SetArray(int[] array) {
        this.array = new int[array.length];
        System.arraycopy(array, 0, this.array, 0, array.length);
    }

    /**
     * Getter for the array.
     * @return Returns the initial array.
     */
    public int[] getArray() {
        return array;
    }
}
