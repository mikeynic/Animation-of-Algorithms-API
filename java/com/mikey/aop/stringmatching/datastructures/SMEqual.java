package com.mikey.aop.stringmatching.datastructures;

/**
 * This class stores a single index in the text and a single index in the pattern. These are used to mark these indices
 * with the 'equal' color. These are accessed using getters.
 * @author Michael Nicholson
 */
public class SMEqual extends SMQueueNode{

    private int showEqualTextIndex;
    private int showEqualPatternIndex;

    /**
     * The sole constructor for this class. This sets up the the indices stored within this object.
     * @param showEqualPatternIndex     The index in the pattern that will be coloured with the equal colour.
     * @param showEqualTextIndex        The index in the text that will be coloured with the equal colour.
     */
    public SMEqual(int showEqualPatternIndex, int showEqualTextIndex) {
        this.showEqualTextIndex = showEqualTextIndex;
        this.showEqualPatternIndex = showEqualPatternIndex;
    }

    /**
     * A getter for the index in the text stored in this object.
     * @return The text index.
     */
    public int getShowEqualTextIndex() {
        return showEqualTextIndex;
    }

    /**
     * A getter for the index in the pattern stored in this object.
     * @return The pattern index.
     */
    public int getShowEqualPatternIndex() {
        return showEqualPatternIndex;
    }

    /**
     * This method creates a deep copy of this, then returns it.
     * @return The deep copy of an instance of this object.
     */
    public SMEqual returnCopy(){
        return new SMEqual(showEqualPatternIndex, showEqualTextIndex);
    }
}
