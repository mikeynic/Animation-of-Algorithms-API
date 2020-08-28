package com.mikey.aop.sorting.datastructures;

/**
 * The type of the nodes used in the SortingQueue. Each SortingQueueNode can only ever be one of these objects:
 * SetArray, Mark, Compare, Swap or nothing. Each of these options can be accompanied by a message.
 * @author Michael Nicholson
 */
public class SortingQueueNode {

    private String message;

    /**
     * Getter for the message stored in this object.
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message stored in this object.
     * @param message The message to be stored in this object.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
