package com.mikey.aop.stringmatching.enumerations;

/**
 * An enumeration for the next state of the marking. This is used to mark an index in the pattern and an index in the
 * text.
 * @author Michael Nicholson
 */
public enum SortingQueueNodeMarking {

    /**
     * The pattern index and the text index are to be marked with the comparison colour.
     */
    COMPARING,

    /**
     * The pattern index and the text index are to be marked with the equal colour.
     */
    SHOW_EQUAL,

    /**
     * The pattern index and the text index are to be marked with the not equal colour.
     */
    SHOW_NOT_EQUAL,

    /**
     * There is no marking being done. This can be used for moving the pattern or for showing a message on the
     * AACurrentTextArea
     */
    NONE

}
