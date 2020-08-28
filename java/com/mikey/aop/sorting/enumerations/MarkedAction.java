package com.mikey.aop.sorting.enumerations;

/**
 * An enumeration for the next state of a marking.
 * @author Michael Nicholson
 */
public enum MarkedAction {

    /**
     * The element is going to be marked.
     */
    BEING_MARKED,

    /**
     * The element is going to be unmarked.
     */
    BEING_UNMARKED,

    /**
     * The element is going to be marked a specified colour.
     */
    BEING_CUSTOM_MARKED,

    /**
     * All elements in the array will be unmarked.
     */
    BEING_UNMARKED_ALL

}
