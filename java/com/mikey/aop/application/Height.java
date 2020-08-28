package com.mikey.aop.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This creates the height with a property that can be bound.
 * @author Michael Nicholson
 */
public class Height {
    private IntegerProperty height;

    /**
     * Getter for the height.
     * @return The height that this object represents.
     */
    public int getHeight() {
        return height.get();
    }

    /**
     * Setter for the height of this object.
     * @param height The new height of this object.
     */
    public void setHeight(int height) {
        this.height.set(height);
    }

    /**
     * This is a getter for the property object that represents the height.
     * @return The property that can be bound.
     */
    public IntegerProperty heightProperty(){
        return height;
    }

    /**
     * This is the sole constructor for this class
     * @param height This is the initial height that this object represents.
     */
    public Height(Integer height) {
        this.height = new SimpleIntegerProperty(height);
    }
}
