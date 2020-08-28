package com.mikey.aop.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This creates the width with a property that can be bound.
 * @author Michael Nicholson
 */
public class Width {
    private IntegerProperty width;

    /**
     * Getter for the width.
     * @return The width that this object represents.
     */
    public int getWidth() {
        return width.get();
    }

    /**
     * Setter for the width of this object.
     * @param width The new width of this object.
     */
    public void setWidth(int width) {
        this.width.set(width);
    }

    /**
     * This is a getter for the property object that represents the width.
     * @return The property that can be bound.
     */
    public IntegerProperty widthProperty(){
        return width;
    }

    /**
     * This is the sole constructor for this class
     * @param height This is the initial width that this object represents.
     */
    public Width(Integer height) {
        this.width = new SimpleIntegerProperty(height);
    }
}
