package com.mikey.aop.trees.datastructures;

import javafx.scene.paint.Color;

/**
 * This class is used for storing all relevant data needed for each index in the tree array. This is then stored as a
 * traditional representation of a tree with left and right child pointers. This allows an easy way of accessing
 * children through recursion.
 * @author Michael Nicholson
 */
public class CanvasNode {

    private CanvasNode left;
    private CanvasNode right;
    private CanvasNode parent;
    private int indexInArray;
    private int indexInPrinting;
    private int depthInPrinting;
    int xOrd, yOrd;

    private int value;
    private boolean marked;
    private Color markingColour;


    /**
     * The sole constructor for this class. This sets up the value of the element.
     * @param value The value of the element in the tree array.
     */
    public CanvasNode(int value) {
        this.value = value;
    }

    /**
     * Getter for the value of the element in the tree array that this class represents.
     * @return The value of this node.
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for the left child pointer of this node.
     * @return A pointer for the left child of this node. May be null.
     */
    public CanvasNode getLeft() {
        return left;
    }

    /**
     * Getter for the right child pointer of this node.
     * @return A pointer for the right child of this node. May be null.
     */
    public CanvasNode getRight() {
        return right;
    }

    /**
     * Getter for the pointer of this nodes parent.
     * @return A pointer for to this nodes parent.
     */
    public CanvasNode getParent() {
        return parent;
    }

    /**
     * Setter for the left child of this node.
     * @param left The left child of this node.
     */
    public void setLeft(CanvasNode left) {
        this.left = left;
    }

    /**
     * Setter for the right child of this node.
     * @param right The right child of this node.
     */
    public void setRight(CanvasNode right) {
        this.right = right;
    }

    /**
     * Setter for the pointer of this nodes parent.
     * @param parent Pointer to this nodes parent.
     */
    public void setParent(CanvasNode parent) {
        this.parent = parent;
    }

    /**
     * Setter for the value of this node - is needed when nodes swap. It is easier to swap values than create deep
     * copies of each node and exchange information.
     * @param value The new value of this node.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Setter of the coordinates of this node.
     * @param xOrd The x ordinate of this node on the canvas.
     * @param yOrd The y ordinate of this node on the canvas.
     */
    public void setXYOrd(int xOrd, int yOrd) {
        this.xOrd = xOrd;
        this.yOrd = yOrd;
    }

    /**
     * Setter for the x ordinate of this node on the canvas.
     * @param xOrd The x ordinate of this node on the canvas.
     */
    public void setXOrd(int xOrd) {
        this.xOrd = xOrd;
    }

    /**
     * Getter for the x ordinate of this node.
     * @return The x ordinate of this node.
     */
    public int getXOrd() {
        return xOrd;
    }

    /**
     * Getter for the y ordinate of this node.
     * @return The y ordinate of this node.
     */
    public int getYOrd() {
        return yOrd;
    }

    /**
     * Getter to see if the node is marked or not.
     * @return True if the node is marked, false if the node is not marked.
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * Setter for the boolean allows us to see if the node is marked or not.
     * @param marked True if the nodes is marked, false if the node is not marked.
     */
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    /**
     * Getter for the index of this node in the array representation of the tree.
     * @return The index in the array representation of the tree.
     */
    public int getIndexInArray() {
        return indexInArray;
    }

    /**
     * Setter for the index of this node in the array representation of the tree.
     * @param indexInArray The index in the array representation of the tree.
     */
    public void setIndexInArray(int indexInArray) {
        this.indexInArray = indexInArray;
    }

    /**
     * Getter for the colour that this node is marked with if it is marked.
     * @return The colour that this node is marked with.
     */
    public Color getMarkingColour() {
        return markingColour;
    }

    /**
     * Setter for the colour of this node when marked.
     * @param markingColour The colour of this node when marked.
     */
    public void setMarkingColour(Color markingColour) {
        this.markingColour = markingColour;
    }

    /**
     * Getter for the index of this node when printing the tree from left to right rather than from top to bottom.
     * This allows us to expand or shrink the tree.
     * @return The index of this node in the tree when counted from left to right.
     */
    public int getIndexInPrinting() {
        return indexInPrinting;
    }

    /**
     * Setter for the index of this node when printing the tree from left to right rather than from top to bottom.
     * This allows us to expand or shrink the tree when set as different coordinates.
     * @param indexInPrinting The index of this CanvasNode when printed on the TreeCanvas.
     */
    public void setIndexInPrinting(int indexInPrinting) {
        this.indexInPrinting = indexInPrinting;
    }

    /**
     * Getter for the depth of this node in the tree. Used when printing the tree on the canvas.
     * @return The depth of this node in the tree.
     */
    public int getDepthInPrinting() {
        return depthInPrinting;
    }

    /**
     * Setter for the depth of this node in the tree. Used when printing the tree on the canvas.
     * @param depthInPrinting The depth of this node in the tree (from top to bottom).
     */
    public void setDepthInPrinting(int depthInPrinting) {
        this.depthInPrinting = depthInPrinting;
    }
}
