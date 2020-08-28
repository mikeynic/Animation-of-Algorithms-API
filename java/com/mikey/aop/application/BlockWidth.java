package com.mikey.aop.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This creates the block width with a property that can be bound.
 * @author Michael Nicholson
 */
public class BlockWidth {

    IntegerProperty blockWidth;

    /**
     * Getter for the block width.
     * @return The representative width of the block that this object represents.
     */
    public int getBlockWidth() {
        return blockWidth.get();
    }

    /**
     * This is a getter for the property object that represents the block width.
     * @return The property that can be bound.
     */
    public IntegerProperty blockWidthProperty() {
        return blockWidth;
    }

    /**
     * Setter for the block width.
     * @param blockWidth The new representative width of the block that this object represents.
     */
    public void setBlockWidth(int blockWidth) {
        this.blockWidth.set(blockWidth);
    }

    /**
     * This is the sole constructor for this class
     * @param blockWidth This is the initial width that this object represents.
     */
    public BlockWidth(int blockWidth) {
        this.blockWidth = new SimpleIntegerProperty(blockWidth);
    }
}

