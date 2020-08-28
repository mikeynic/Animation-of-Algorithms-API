package com.mikey.aop.stringmatching.datastructures;

/**
 * This class stores a beginning index in the text for the pattern to move from and an end index in the text for the
 * pattern to move to. These are used to show the movement from the beginning to the end index.
 * These are accessed using getters.
 * @author Michael Nicholson
 */
public class SMMoving extends SMQueueNode{

    int movingStartIndex;
    int movingEndIndex;

    /**
     * The sole constructor for this class. This sets up the private indices stored within this object.
     * @param movingStartIndex      The index at which the pattern will begin movement from in the text.
     * @param movingEndIndex        The index in the text at which the movement of the pattern will end.
     */
    public SMMoving(int movingStartIndex, int movingEndIndex) {
        this.movingStartIndex = movingStartIndex;
        this.movingEndIndex = movingEndIndex;
    }

    /**
     * Getter for the start index.
     * @return The start index in the text.
     */
    public int getMovingStartIndex() {
        return movingStartIndex;
    }

    /**
     * Getter for the end index.
     * @return The end index in the text.
     */
    public int getMovingEndIndex() {
        return movingEndIndex;
    }
}
