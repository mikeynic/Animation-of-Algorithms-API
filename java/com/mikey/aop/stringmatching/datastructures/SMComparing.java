package com.mikey.aop.stringmatching.datastructures;

/**
 * This class stores a single index in the text and a single index in the pattern. These are used to mark these indices
 * with the 'comparing' color. These are accessed using getters.
 * @author Michael Nicholson
 */
public class SMComparing extends SMQueueNode{

    int comparingTextIndex;
    int comparingPatternIndex;

    /**
     * Sole constructor for this class. This sets up the indices stored within this object.
     * @param comparingPatternIndex     The index in the pattern that is to be compared.
     * @param comparingTextIndex        The index in the text that is to be compared.
     */
    public SMComparing(int comparingPatternIndex, int comparingTextIndex) {
        this.comparingTextIndex = comparingTextIndex;
        this.comparingPatternIndex = comparingPatternIndex;
    }

    /**
     * Getter for the index in the text that is to be compared.
     * @return The index in the text to be compared.
     */
    public int getComparingTextIndex() {
        return comparingTextIndex;
    }

    /**
     * Getter for the index in the pattern that is to be compared.
     * @return The index in the pattern to be compared.
     */
    public int getComparingPatternIndex() {
        return comparingPatternIndex;
    }
}
