package com.mikey.aop.trees.datastructures.queue;

/**
 * The type of the nodes used in the QueueActions data structure. Each QueueNode can only ever be one of
 * these objects: SetArray, Mark, Compare or Swap. Each of these options can be accompanied by a message.
 * @author Michael Nicholson
 */
public class QueueNode {

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
