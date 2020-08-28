package com.mikey.aop.stringmatching.datastructures;

/**
 * This class stores a single index in the text and a single index in the pattern. These are used to mark these indices
 * with the 'not equal' color. These are accessed using getters.
 * @author Michael Nicholson
 */
public class SMNotEqual extends SMQueueNode{

    private int showNotEqualTextIndex;
    private int showNotEqualPatternIndex;

    /**
     * The sole constructor for this class. This sets up the indices stored within this object.
     * @param showNotEqualPatternIndex  The index within the pattern that will be marked.
     * @param showNotEqualTextIndex     The index within the text that will be marked.
     */
    public SMNotEqual(int showNotEqualPatternIndex, int showNotEqualTextIndex) {
        this.showNotEqualTextIndex = showNotEqualTextIndex;
        this.showNotEqualPatternIndex = showNotEqualPatternIndex;
    }

    /**
     * Getter for the index in the text being marked.
     * @return The index being marked in the text.
     */
    public int getShowNotEqualTextIndex() {
        return showNotEqualTextIndex;
    }

    /**
     * Getter for the index in the pattern being marked.
     * @return The index being marked in the pattern.
     */
    public int getShowNotEqualPatternIndex() {
        return showNotEqualPatternIndex;
    }
}
