package com.mikey.aop.trees.algorithms;

import javafx.scene.paint.Color;

/**
 * This is the class that is to be extended by the user in order to animate a tree algorithm.
 * The user will have to implement the algorithm method which should contain the logic and method calls to drive the
 * animation.
 * @author Michael Nicholson
 */
public abstract class GenericTreeAlgorithm {

    /**
     * This needs to be implemented by the user.
     * This is the driving method of the algorithm that is called by the backend.
     * The logic can be called from methods elsewhere in the class.
     */
    public abstract void algorithm();

    /**
     * User callable method.
     * Should be called at the start of the sort method.
     * The user must call this to set-up the initial data structure.
     * Without a call to showInitial, the other methods called will not reflect changes in the on screen data structure.
     * @param arr Is the array passed to the backend to set the initial state of the data structure that will be
     *            animated.
     */
    public void showArray(int[] arr){}

    /**
     * User callable method.
     * This method should be called in order to mark an index in the tree.
     * If the element is swapped with another element, the marking will persist on the index.
     * The marking will be of the default marking colour.
     * @param index This is the index in the array that will be marked.
     */
    public void showMark(int index){}

    /**
     * User callable method.
     * This method should be called in order to mark an index in the tree.
     * If the element is swapped with another element, the marking will persist on the index.
     * The marking will be of the default marking colour.
     * @param index This is the index in the array that will be marked.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showMark(int index, String message){}

    /**
     * User callable method.
     * This method should be called in order to mark an index in the tree.
     * If the element is swapped with another element, the marking will persist on the index.
     * The marking will be of the specified colour.
     * @param index This is the index in the array that will be marked.
     * @param color This is the colour that the marking will be around the element.
     */
    public void showMark(int index, Color color){}

    /**
     * User callable method.
     * This method should be called in order to mark an index in the tree.
     * If the element is swapped with another element, the marking will persist on the index.
     * The marking will be of the specified colour.
     * @param index     This is the index in the array that will be marked.
     * @param color     This is the colour that the marking will be around the element.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showMark(int index, Color color, String message){}

    /**
     * User callable method.
     * This method should be called in order to unmark an index in the tree.
     * If the element is not marked, then the element will continue to be unmarked.
     * @param index This is the index in the array that will be marked.
     */
    public void showUnMark(int index){}

    /**
     * User callable method.
     * This method should be called in order to unmark an index in the tree.
     * If the element is not marked, then the element will continue to be unmarked.
     * @param index     This is the index in the array that will be marked.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showUnMark(int index, String message){}

    /**
     * User callable method.
     * This method should be called to swap the places of two elements in the tree.
     * The order of the parameters does not matter.
     * @param index1 This is the first index of an element in the array that the user has specified to swap.
     * @param index2 This is the second index of an element in the array that the user has specified to swap.
     */
    public void showSwap(int index1, int index2){}

    /**
     * User callable method.
     * This method should be called to swap the places of two elements in the tree.
     * The order of the parameters does not matter.
     * @param index1    This is the first index of an element in the array that the user has specified to swap.
     * @param index2    This is the second index of an element in the array that the user has specified to swap.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showSwap(int index1, int index2, String message){}

    /**
     * User callable method.
     * This method should be called to insert an element at a leaf node in the tree array. This method only supports
     * insertions in to the tree where there is space in the array and where the insertion index is a leaf node of the
     * tree.
     * @param element   The element being inserted in to the tree. In other words, its' value.
     * @param index     The index at which the element will be inserted in to the tree array.
     */
    public void showInsert(int element, int index){}

    /**
     * User callable method.
     * This method should be called to insert an element at a leaf node in the tree array. This method only supports
     * insertions in to the tree where there is space in the array and where the insertion index is a leaf node of the
     * tree.
     * @param element   The element being inserted in to the tree. In other words, its' value.
     * @param index     The index at which the element will be inserted in to the tree array.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showInsert(int element, int index, String message){}

    /**
     * User callable method.
     * This method should be called in order to delete a leaf node in the tree array. This method only supports
     * deletions of leaf nodes.
     * @param index The index in the array of the node being deleted.
     */
    public void showDelete(int index){}

    /**
     * User callable method.
     * This method should be called in order to delete a leaf node in the tree array. This method only supports
     * deletions of leaf nodes.
     * @param index     The index in the array of the node being deleted.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showDelete(int index, String message){}

    /**
     * This is a method call dedicated to showing a method in the AACurrentTextArea.
     * If there is an operation call after this method call, the text that is shown in the AACurrentTextArea will be
     * immediately dropped to the top of the AALoggedTextArea.
     * @param message The message that is to be shown in the AACurrentTextArea.
     */
    public void showMessage(String message){}
}
