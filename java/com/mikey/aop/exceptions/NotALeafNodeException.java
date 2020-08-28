package com.mikey.aop.exceptions;

/**
 * Custom exception that is thrown when a user tries to insert or delete at a non-leaf node position.
 * @author Michael Nicholson
 */
public class NotALeafNodeException extends Exception{

    /**
     * The sole constructor for this class. This allows the exception to be thrown with a specific error message.
     * @param index The index of the non-leaf node.
     */
    public NotALeafNodeException(int index) {
        super("Cannot perform operation on index=" + index + " because it is not a leaf in the tree.");
    }
}
