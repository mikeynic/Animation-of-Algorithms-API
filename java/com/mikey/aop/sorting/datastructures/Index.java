package com.mikey.aop.sorting.datastructures;


import com.mikey.aop.sorting.enumerations.MarkingState;
import javafx.scene.paint.Color;

/**
 * This class contains information an element in the primitive array that was specified by the user.
 * @author Michael Nicholson
 */
public class Index extends SortingQueueNode{

    private boolean comparing;
    private boolean swapping;

    private MarkingState marked;
    private int value;
    private Integer index;
    private Color color;

    /**
     * Default constructor for the Index class.
     */
    public Index() {
    }

    /**
     * Getter for the colour of this element.
     * @return The current colour of the element.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the colour of this element.
     * @param color The colour of the element.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter that checks if the element is currently being swapped or not.
     * @return True if the element is swapping, false if not.
     */
    public boolean isSwapping() {
        return swapping;
    }

    /**
     * Setter for the current state of swapping.
     * @param swapping True if swapping, false if not.
     */
    public void setSwapping(boolean swapping) {
        this.swapping = swapping;
    }

    /**
     * Getter for the marking state.
     * @return The marking state of the element.
     */
    public MarkingState getMarked() {
        return marked;
    }

    /**
     * Setter for the marking state.
     * @param marked The state of the element's marking.
     */
    public void setMarked(MarkingState marked) {
        this.marked = marked;
    }

    /**
     * Getter for the state of comparing.
     * @return True if comparing, false if not.
     */
    public boolean isComparing() {
        return comparing;
    }

    /**
     * Setter for the state of comparing.
     * @param comparing True if comparing, false if not.
     */
    public void setComparing(boolean comparing) {
        this.comparing = comparing;
    }

    /**
     * Getter for the element value.
     * @return The element's value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter for the element value.
     * @param value The element's value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter for the index of the element.
     * @return the index of the element.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter for the index of the element. This also ensures this is only ever set once and cannot be changed.
     * @param index The index of the element.
     */
    public void setIndex(int index) {
        // Ensures this is only set once
        if (this.index == null){
            this.index = index;
        }
    }

    /**
     * This is the constructor used for creating a copy object of this object.
     * @param marked        The marking state of the index being copied.
     * @param comparing     States whether or not the index is being compared or not.
     * @param swapping      States whether or not the index is being swapped or not.
     * @param value         The value of the element.
     * @param index         The index of the element.
     * @param color         The color of the specified marking (if any).
     */
    private Index(MarkingState marked, boolean comparing, boolean swapping, int value, Integer index, Color color) {
        this.marked = marked;
        this.comparing = comparing;
        this.swapping = swapping;
        this.value = value;
        this.index = index;
        this.color = color;
    }

    /**
     * A method to return an exact, deep copy of this object.
     * @return A deep, exact copy of this object.
     */
    public Index getCopy(){
        return new Index(this.marked, this.comparing, this.swapping, this.value, this.index, this.color);
    }
}
