package com.mikey.aop.trees.datastructures.queue;

/**
 * This class is used for storing the array tree used for the set-up of the animation.
 * @author Michael Nicholson
 */
public class SetArray extends QueueNode{

    private int[] array;

    /**
     * The sole constructor for this object. This stores a deep copy of the array passed in to it to ensure no data
     * mutations occur on this.
     * @param arr The array being stored.
     */
    public SetArray(int[] arr) {
        int len = arr.length;
        this.array = new int[len];
        System.arraycopy(arr, 0, array, 0, len);
    }

    /**
     * Getter for the deep copy of the array.
     * @return The array used for the set-up of the animation.
     */
    public int[] getArray() {
        return array;
    }
}
