package com.mikey.aop.sorting.algorithms;

import javafx.scene.paint.Color;

/**
 * This is the class that is to be extended by the user in order to animate a sorting algorithm.
 * The user will have to implement the sort method which should contain the logic and method calls to drive the
 * animation.
 * @author Michael Nicholson
 */
public abstract class GenericSort {
    /**
     * This needs to be implemented by the user.
     * This is the driving method of the algorithm that is called by the backend.
     * The logic can be called from methods elsewhere in the class.
     */
    public abstract void sort();

    /**
     * User callable method.
     * Should be called at the start of the sort method.
     * The user must call this to set-up the initial data structure.
     * Without a call to showInitial, the other methods called will not reflect changes in the on screen data structure.
     * @param arr Is the array passed to the backend to set the initial state of the data structure that will be
     *            animated.
     */
    public void showInitial(int[] arr){}

    /**
     * User callable method.
     * This method should be called in order to mark an element.
     * If the element is swapped with another element, the marking will persist on this element.
     * @param index This is the current index of the element in the array that will be marked.
     */
    public void showMark(int index){}

    /**
     * User callable method.
     * This method should be called in order to mark an element.
     * If the element is swapped with another element, the marking will persist on this element.
     * @param index     This is the current index of the element in the array that will be marked.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showMark(int index, String message){}

    /**
     * User callable method.
     * This method should be called in order to mark an element.
     * If the element is swapped with another element, the marking will persist on this element.
     * @param index     This is the current index of the element in the array that will be marked.
     * @param color     This is an additional argument used to specify the element at the index will be marked with.
     */
    public void showMark(int index, Color color){}

    /**
     * User callable method.
     * This method should be called in order to mark an element.
     * If the element is swapped with another element, the marking will persist on this element.
     * @param index     This is the current index of the element in the array that will be marked.
     * @param color     This is an additional argument used to specify the element at the index will be marked with.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showMark(int index, Color color, String message){}

    /**
     * User callable method.
     * This method should be called in order to unmark an element.
     * If the element is currently unmarked, it will stay unmarked.
     * @param index This is the current index of the element in the array that will be unmarked.
     */
    public void showUnMark(int index){}

    /**
     * User callable method.
     * This method should be called in order to unmark an element.
     * If the element is currently unmarked, it will stay unmarked.
     * @param index     This is the current index of the element in the array that will be unmarked.
     * @param message   This is an additional parameter that can be specified to show a message in the AACurrentTextArea
     *                  which will be shown throughout the duration of this method call.
     */
    public void showUnMark(int index, String message){}

    /**
     * User callable method.
     * This method should be called to swap the places of two elements in the array.
     * The order of the parameters does not matter.
     * @param firstIndex This is the first index of an element in the array that the user has specified to swap.
     * @param secondIndex This is the second index of an element in the array that the user has specified to swap.
     */
    public void showSwap(int firstIndex, int secondIndex){}

    /**
     * User callable method.
     * This method should be called to swap the places of two elements in the array.
     * The order of the parameters does not matter.
     * @param firstIndex    This is the first index of an element in the array that the user has specified to swap.
     * @param secondIndex   This is the second index of an element in the array that the user has specified to swap.
     * @param message       This is an additional parameter that can be specified to show a message in the
     *                      AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showSwap(int firstIndex, int secondIndex, String message){}

    /**
     * User callable method.
     * This method should be called to show the comparison of two indices in the array.
     * The order of the parameters does not matter.
     * @param firstIndex    This is the first index of an element in the array that the user has specified to be
     *                      compared.
     * @param secondIndex   This is the second index of an element in the array that the user has specified to be
     *                      compared.
     */
    public void showComparison(int firstIndex, int secondIndex){}

    /**
     * User callable method.
     * This method should be called to show the comparison of two indices in the array.
     * The order of the parameters does not matter.
     * @param firstIndex    This is the first index of an element in the array that the user has specified to be
     *                      compared.
     * @param secondIndex   This is the second index of an element in the array that the user has specified to be
     *                      compared.
     * @param message       This is an additional parameter that can be specified to show a message in the
     *                      AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showComparison(int firstIndex, int secondIndex, String message){}

    /**
     * This is a method call that specifies the un-marking of all currently marked elements in the animated
     * visualisation
     */
    public void showUnMarkAll(){}

    /**
     * This is a method call that specifies the un-marking of all currently marked elements in the animated
     * visualisation that shows a message whilst executing.
     * @param message       This is an additional parameter that can be specified to show a message in the
     *                      AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showUnMarkAll(String message){}

    /**
     * This is a method call dedicated to showing a method in the AACurrentTextArea.
     * If there is an operation call after this method call, the text that is shown in the AACurrentTextArea will be
     * immediately dropped to the top of the AALoggedTextArea.
     * @param message The message that is to be shown in the AACurrentTextArea.
     */
    public void showMessage(String message){}
}
