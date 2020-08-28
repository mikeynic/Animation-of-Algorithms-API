package com.mikey.aop.stringmatching.algorithms;

/**
 * This is the class that is to be extended by the user in order to animate a string matching algorithm.
 * The user will have to implement the stringMatch method which should contain the logic and method calls to drive the
 * animation.
 * @author Michael Nicholson
 */
public abstract class GenericStringMatch {
    /**
     * This needs to be implemented by the user.
     * This is the driving method of the algorithm that is called by the backend.
     * The logic can be called from methods elsewhere in the class.
     */
    public abstract void stringMatch();

    /**
     * User callable method.
     * Should be called at the start of the stringMatch method.
     * The user must call this to set-up the initial data structures.
     * Without a call to showInitialStringAndText, the other methods called will not reflect changes in the on
     * screen data structure.
     * @param pattern   Is the string that is to be matched to an index in the text.
     * @param text      Is the text that may contain the pattern.
     */
    public void showInitialPatternAndText(String pattern, String text){}

    /**
     * User callable method.
     * This method should be called when the user wants the pattern to move from one index to another.
     * @param startIndex    The index in the text that the first character in the pattern is currently at.
     * @param endIndex      The index in the text that the first character in the pattern will end up at.
     */
    public void showMovePatternToIndex(int startIndex, int endIndex){}

    /**
     * User callable method.
     * This method should be called when the user wants the pattern to move from one index to another.
     * @param startIndex        The index in the text that the first character in the pattern is currently at.
     * @param endIndex          The index in the text that the first character in the pattern will end up at.
     * @param displayMessage    This is an additional parameter that can be specified to show a message in the
     *                          AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showMovePatternToIndex(int startIndex, int endIndex, String displayMessage){}

    /**
     * User callable method.
     * This method should be called when the user wants to show that an index in the pattern is being compared with an
     * Index in the text. The two indices will be coloured orange for a set amount of time before moving on to the next
     * operation.
     * @param patternIndex  The index in the pattern that is to be coloured orange.
     * @param textIndex     The index in the text that is to be coloured orange.
     */
    public void showComparison(int patternIndex, int textIndex){}

    /**
     * User callable method.
     * This method should be called when the user wants to show that an index in the pattern is being compared with an
     * Index in the text. The two indices will be coloured orange for a set amount of time before moving on to the next
     * operation.
     * @param patternIndex      The index in the pattern that is to be coloured orange.
     * @param textIndex         The index in the text that is to be coloured orange.
     * @param displayMessage    This is an additional parameter that can be specified to show a message in the
     *                          AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showComparison(int patternIndex, int textIndex, String displayMessage){}

    /**
     * User callable method.
     * This method should be called when the user want to show that an index in the pattern is equal to an
     * Index in the text. The two indices specified will be coloured green for a set amount of time before moving on
     * to the next operation.
     * @param patternIndex      The index in the pattern that is to be coloured green.
     * @param textIndex         The index in the text that is to be coloured green.
     */
    public void showEqual(int patternIndex, int textIndex){}

    /**
     * User callable method.
     * This method should be called when the user want to show that an index in the pattern is equal to an
     * Index in the text. The two indices specified will be coloured green for a set amount of time before moving on
     * to the next operation.
     * @param patternIndex      The index in the pattern that is to be coloured green.
     * @param textIndex         The index in the text that is to be coloured green.
     * @param displayMessage    This is an additional parameter that can be specified to show a message in the
     *                          AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showEqual(int patternIndex, int textIndex, String displayMessage){}

    /**
     * User callable method.
     * This method should be called when the user want to show that an index in the pattern is not equal to an
     * Index in the text. The two indices specified will be coloured red for a set amount of time before moving on
     * to the next operation.
     * @param patternIndex      The index in the pattern that is to be coloured green.
     * @param textIndex         The index in the text that is to be coloured green.
     */
    public void showNotEqual(int patternIndex, int textIndex){}

    /**
     * User callable method.
     * This method should be called when the user want to show that an index in the pattern is not equal to an
     * Index in the text. The two indices specified will be coloured red for a set amount of time before moving on
     * to the next operation.
     * @param patternIndex      The index in the pattern that is to be coloured green.
     * @param textIndex         The index in the text that is to be coloured green.
     * @param displayMessage    This is an additional parameter that can be specified to show a message in the
     *                          AACurrentTextArea which will be shown throughout the duration of this method call.
     */
    public void showNotEqual(int patternIndex, int textIndex, String displayMessage){}

    /**
     * This is a method call dedicated to showing a method in the AACurrentTextArea.
     * If there is an operation call after this method call, the text that is shown in the AACurrentTextArea will be
     * immediately dropped to the top of the AALoggedTextArea.
     * @param message The message that is to be shown in the AACurrentTextArea.
     */
    public void showMessage(String message){}
}
