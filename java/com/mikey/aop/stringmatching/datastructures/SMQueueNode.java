package com.mikey.aop.stringmatching.datastructures;

/**
 * This class contains only one of these objects at any given point: SMMoving, SMComparing, SMEqual, SMNotEqual
 * or Nothing. Each of these can be accompanied by a message. All other objects getters will return null when called.
 */
public class SMQueueNode {

    private String message;

    /**
     * Setter for the message stored in this object.
     * @param message The message to be stored in this object.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the message stored in this object.
     * @return The message.
     */
    public String getMessage() {
        return message;
    }
}
