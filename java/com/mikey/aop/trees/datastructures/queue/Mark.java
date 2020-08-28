package com.mikey.aop.trees.datastructures.queue;

import javafx.scene.paint.Color;

/**
 * This class is used for storing the index in the tree that will be marked.
 * @author Michael Nicholson
 */
public class Mark extends QueueNode{

    private int index;
    private Color color;
    private boolean hasColour;

    /**
     * This sets up the marking index and specifies that the marking is of the default color
     * @param index The index in the tree array that will be marked.
     */
    public Mark(int index) {
        hasColour = false;
        this.index = index;
    }

    /**
     * This sets up the index being marked. The color of the marking is custom and is specified on instantiation.
     * @param index The index being marked.
     * @param color The color of the marking.
     */
    public Mark(int index, Color color) {
        hasColour = true;
        this.index = index;
        this.color = color;
    }

    /**
     * Getter for the index being marked.
     * @return The index being marked in the tree array.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Getter for the index being marked.
     * @return The index being marked in the tree array.
     */
    public Color getColor() {
        return color;
    }

    public boolean isColoured() {
        return hasColour;
    }
}
