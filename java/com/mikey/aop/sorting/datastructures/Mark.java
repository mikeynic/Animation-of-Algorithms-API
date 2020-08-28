package com.mikey.aop.sorting.datastructures;

import com.mikey.aop.sorting.enumerations.MarkedAction;
import javafx.scene.paint.Color;

/**
 * An object to store the index of an element that is being marked.
 * @author Michael Nicholson
 */
public class Mark extends SortingQueueNode{

    private int markIndex;
    private MarkedAction markedAction;
    private Color markedColour;

    /**
     * Sole constructor of this object.
     * @param markIndex     The index being marked.
     * @param markedAction  How the index is being marked i.e. unmarked, default, custom.
     * @param markedColour  The colour of the marking if specified.
     */
    public Mark(int markIndex, MarkedAction markedAction, Color markedColour) {
        this.markIndex = markIndex;
        this.markedAction = markedAction;
        this.markedColour = markedColour;
    }

    /**
     * Getter for the index being marked.
     * @return The index being marked.
     */
    public int getMarkIndex() {
        return markIndex;
    }

    /**
     * Getter for the marking action.
     * @return The marking action.
     */
    public MarkedAction getMarkedAction() {
        return markedAction;
    }

    /**
     * Getter for the colour of marking.
     * @return The colour of marking.
     */
    public Color getMarkedColour() {
        return markedColour;
    }
}
