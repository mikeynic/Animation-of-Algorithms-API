package com.mikey.aop.stringmatching.components;

import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.SM_CONSTANT;
import com.mikey.aop.stringmatching.datastructures.*;
import com.mikey.aop.stringmatching.enumerations.SortingQueueNodeMarking;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.logging.ConsoleHandler;

/**
 * SMCanvas extends the JavaFx Canvas class.
 * This class is used to reflect the changes in the data structure as an animation on the screen.
 * @author Michael Nicholson
 */
public class SMCanvas extends Canvas {

    private static SMCanvas instance;
    private String text, pattern;
    private SMCanvasHelper cvsHelper;
    private SMQueueThread queueThread;
    private SMQueue queue;

// Initialisation methods ----------------------------------------------------------------------------------------------
    /**
     * Enforces the singleton design pattern.
     * @return The singleton instance of the SMCanvas
     */
    public static synchronized SMCanvas getInstance(){
        if(instance == null)
            instance = new SMCanvas();
        return instance;
    }

    /**
     * This is the sole constructor of the class and contains setup code for the operation queues and threads.
     */
    public SMCanvas() {
        super(SM_CONSTANT.width.getWidth(), SM_CONSTANT.height.getHeight());
        cvsHelper = new SMCanvasHelper();

        heightProperty().addListener((observable, oldValue, newValue) -> {
            prefHeight(newValue.intValue());
            cvsHelper.drawBackground();
            cvsHelper.drawTextAndPatternAtPixelIndex(text, pattern, 0);
        });

        widthProperty().addListener((observable, oldValue, newValue) -> {
            prefWidth(newValue.intValue());
            cvsHelper.drawBackground();
            cvsHelper.drawTextAndPatternAtPixelIndex(text, pattern, 0);
        });

        queueThread = new SMQueueThread();
        this.queue = new SMQueue();
        queueThread.start();
    }

    /**
     * Set-up method for the text.
     * @param text The text being used for string matching.
     * @param pattern The pattern being used for string matching.
     */
    public void setTextAndPattern(String text, String pattern) {
        this.text = text;
        this.pattern = pattern;

        SM_CONSTANT.maxSize = Math.max(text.length(), pattern.length());
    }

    /**
     * Helper method to reflect the initial states of the text and pattern on the canvas.
     */
    public void initialiseDrawing(){
        cvsHelper.drawBackground();
        if(text != null && pattern != null){
            animateChange(0, 0, SortingQueueNodeMarking.NONE);
        }
    }

// Aspect methods ------------------------------------------------------------------------------------------------------

    /**
     * This is the method that the StringMatchingAspect calls in order to enqueue a pattern movement operation
     * to the internal queue.
     * @param oldPatternIndex The initial index of the pattern in the text.
     * @param newPatternIndex The final index of the pattern in the text.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     * @throws ArrayIndexOutOfBoundsException   Thrown when either of the indices supplied fall outside the range of the
     *                                          text array.
     */
    public void showMovePatternToIndex(int oldPatternIndex, int newPatternIndex, String message) throws ArrayIndexOutOfBoundsException{
        try{
            char o = text.toCharArray()[oldPatternIndex];
            if(newPatternIndex > text.toCharArray().length-pattern.toCharArray().length)
                throw new ArrayIndexOutOfBoundsException(newPatternIndex); //when the index > (text length - pattern length)
            char n = text.toCharArray()[newPatternIndex];
        }catch (IndexOutOfBoundsException e){e.printStackTrace();}

        SMMoving moving;
        if(newPatternIndex == 0)
            moving = new SMMoving(0, 0);
        else
            moving = new SMMoving(oldPatternIndex, newPatternIndex);
        moving.setMessage(message);
        queue.add(moving);
        showMovePatternToIndexHelper(oldPatternIndex, newPatternIndex, message);
    }

    /**
     * This is the method that the StringMatchingAspect calls in order to enqueue a comparison operation
     * to the internal queue.
     * @param patternIndex The index of the character being compared in the pattern character array.
     * @param textIndex The index of the character being compared in the text character array.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     * @throws ArrayIndexOutOfBoundsException   Thrown when the patternIndex falls outside of the range of the pattern.
     *                                          Also thrown when the textIndex falls outside of the range of the text.
     */
    public void showComparison(int patternIndex, int textIndex, String message) throws ArrayIndexOutOfBoundsException{
        markingErrorHandle(patternIndex, textIndex);
        SMComparing comparing = new SMComparing(patternIndex, textIndex);
        comparing.setMessage(message);
        queue.add(comparing);
        showComparisonHelper(patternIndex, textIndex, message);
    }

    /**
     * This is the method that the StringMatchingAspect calls in order to enqueue a show equal operation
     * to the internal queue.
     * @param patternIndex The index of the character being shown to be equal in the pattern character array.
     * @param textIndex The index of the character being being shown to be equal in the text character array.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     * @throws ArrayIndexOutOfBoundsException   Thrown when the patternIndex falls outside of the range of the pattern.
     *                                          Also thrown when the textIndex falls outside of the range of the text.
     */
    public void showEqual(int patternIndex, int textIndex, String message) throws ArrayIndexOutOfBoundsException{
        markingErrorHandle(patternIndex, textIndex);
        SMEqual equal = new SMEqual(patternIndex, textIndex);
        equal.setMessage(message);
        queue.add(equal);
        showEqualHelper(patternIndex, textIndex, message);
    }

    /**
     * This is the method that the StringMatchingAspect calls in order to enqueue a not equal operation
     * to the internal queue.
     * @param patternIndex The index of the character being shown to be not equal in the pattern character array.
     * @param textIndex The index of the character being being shown to be not equal in the text character array.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     * @throws ArrayIndexOutOfBoundsException   Thrown when the patternIndex falls outside of the range of the pattern.
     *                                          Also thrown when the textIndex falls outside of the range of the text.
     */
    public void showNotEqual(int patternIndex, int textIndex, String message) throws ArrayIndexOutOfBoundsException{
        markingErrorHandle(patternIndex, textIndex);
        SMNotEqual notEqual = new SMNotEqual(patternIndex, textIndex);
        notEqual.setMessage(message);
        queue.add(notEqual);
        showNotEqualHelper(patternIndex, textIndex, message);
    }

    private void markingErrorHandle(int patternIndex, int textIndex) throws ArrayIndexOutOfBoundsException{
        try{
            char p = pattern.toCharArray()[patternIndex];
            char t = text.toCharArray()[textIndex];
            int pLen = pattern.toCharArray().length;
            if(textIndex - patternIndex < 0)
                throw new ArrayIndexOutOfBoundsException(textIndex); // makes sure the pattern is not compared an impossible index.
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    /**
     * This is the method that the StringMatchingAspect calls in order to enqueue a message to be shown on the
     * AACurrentTextArea.
     * @param message The message to be shown in the AACurrentTextArea.
     */
    public void enqueueMessage(String message){
        SMQueueNode node = new SMQueueNode();
        node.setMessage(message);
        queue.add(node);
        Runnable r = () -> enqueueMessageHelper(message);
        queueThread.enqueueRunnable(r);
    }

// Helper methods ------------------------------------------------------------------------------------------------------
    /**
     * This is the method that draws the animation of the pattern moving from the old index to the new index in the
     * text. This drawing is reflected on the string matching canvas.
     * A Runnable is enqueued to the SMQueueThread to then be executed.
     * @param oldPatternIndex   The initial index in the text.
     * @param newPatternIndex   The final index in the text.
     * @param message           An optional parameter for the overloading of methods in the GenericStringMatch class
     *                          used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                          handled accordingly.
     */
    public void showMovePatternToIndexHelper(int oldPatternIndex, int newPatternIndex, String message) {
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                int prevPixel;
                int nextPixel;
                if (newPatternIndex == 0) {
                    prevPixel = 0;
                    nextPixel = 0;
                } else {
                    prevPixel = oldPatternIndex * SM_CONSTANT.blockWidth.getBlockWidth();
                    nextPixel = newPatternIndex * SM_CONSTANT.blockWidth.getBlockWidth();
                }
                while (prevPixel <= nextPixel) {
                    int finalPrevPixel = prevPixel;
                    Runnable animation = () -> {
                        cvsHelper.drawBackground();
                        cvsHelper.drawTextAndPatternAtPixelIndex(text, pattern, finalPrevPixel);
                    };
                    Platform.runLater(animation);
                    try{Thread.sleep(SM_CONSTANT.frameLength);}catch (Exception e){System.out.println(e);}
                    prevPixel++;
                }
            };
            queueThread.enqueueToExecutor(executor);
        };
        queueThread.enqueueRunnable(r);
    }

    /**
     * This is the method that draws the animation of an index in the pattern being shown to be compared to an index in
     * the text. A Runnable is enqueued to the SMQueueThread to then be executed to reflect this change.
     * @param patternIndex      The index in the pattern.
     * @param textIndex         The index in the text.
     * @param message           An optional parameter for the overloading of methods in the GenericStringMatch class
     *                          used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                          handled accordingly.
     */
    public void showComparisonHelper(int patternIndex, int textIndex, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                Runnable animation = () -> animateChange(textIndex, patternIndex, SortingQueueNodeMarking.COMPARING);
                Platform.runLater(animation);
                try{Thread.sleep(SM_CONSTANT.frameLength*40);}catch (Exception e){System.out.println(e);}
            };
            queueThread.enqueueToExecutor(executor);
        };
        queueThread.enqueueRunnable(r);
    }

    /**
     * This is the method that draws the animation of an index in the pattern being shown to be equal to an index in
     * the text. A Runnable is enqueued to the SMQueueThread to then be executed to reflect this change.
     * @param patternIndex      The index in the pattern.
     * @param textIndex         The index in the text.
     * @param message           An optional parameter for the overloading of methods in the GenericStringMatch class
     *                          used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                          handled accordingly.
     */
    public void showEqualHelper(int patternIndex, int textIndex, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                Runnable animation = () -> animateChange(textIndex, patternIndex, SortingQueueNodeMarking.SHOW_EQUAL);
                Platform.runLater(animation);
                try{Thread.sleep(SM_CONSTANT.frameLength*40);}catch (Exception e){System.out.println(e);}
            };
            queueThread.enqueueToExecutor(executor);
        };
        queueThread.enqueueRunnable(r);
    }

    /**
     * This is the method that draws the animation of an index in the pattern being shown to be not equal to an index in
     * the text. A Runnable is enqueued to the SMQueueThread to then be executed to reflect this change.
     * @param patternIndex      The index in the pattern.
     * @param textIndex         The index in the text.
     * @param message           An optional parameter for the overloading of methods in the GenericStringMatch class
     *                          used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                          handled accordingly.
     */
    public void showNotEqualHelper(int patternIndex, int textIndex, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                Runnable animation = () -> animateChange(textIndex, patternIndex, SortingQueueNodeMarking.SHOW_NOT_EQUAL);
                Platform.runLater(animation);
                try{Thread.sleep(SM_CONSTANT.frameLength*40);}catch (Exception e){System.out.println(e);}
            };
            queueThread.enqueueToExecutor(executor);
        };
        queueThread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to show a message on the AACurrentTextArea.
     * The Runnable is then enqueued to the internal SMQueueThread to then be executed.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     */
    public void enqueueMessageHelper(String message){
        Runnable executor = () -> Platform.runLater(() -> AACurrentTextArea.getInstance().setCurrentMessage(message));
        queueThread.enqueueToExecutor(executor);
    }

// Controller methods --------------------------------------------------------------------------------------------------
    /**
     * Method to map the play button to the play method in the internal executor thread that controls the play/pause
     * functionality of the GUI.
     */
    public void play(){
        queueThread.play();
    }

    /**
     * Method to map the pause button to the pause method in the internal executor thread that controls the play/pause
     * functionality of the GUI.
     */
    public void pause(){
        queueThread.pause();
    }

    /**
     * Method to map the step button to the step method in the internal executor thread that controls the play/pause
     * functionality of the GUI.
     */
    public void step(){
        queueThread.step();
    }

    /**
     * This method called from the onResetButtonPress method in the SMController resets the SMCanvas internal queues
     * and threads to their initial states. The GUI also reflects this change by showing the initial state of the
     * data pattern and text.
     */
    public void reset(){
        queueThread.resetQueue();
        initialiseDrawing();
        int queuePointer = 0;
        while(queuePointer < queue.size()){
            SMQueueNode node = queue.get(queuePointer);
            String message = node.getMessage();

            if(node instanceof SMComparing){
                SMComparing comparing = (SMComparing) node;
                showComparisonHelper(comparing.getComparingPatternIndex(), comparing.getComparingTextIndex(), message);
            }
            else if(node instanceof SMEqual){
                SMEqual equal = (SMEqual) node;
                showEqualHelper(equal.getShowEqualPatternIndex(), equal.getShowEqualTextIndex(), message);
            }
            else if(node instanceof SMNotEqual){
                SMNotEqual notEqual = (SMNotEqual) node;
                showNotEqualHelper(notEqual.getShowNotEqualPatternIndex(), notEqual.getShowNotEqualTextIndex(), message);
            }
            else if(node instanceof SMMoving){
                SMMoving moving = (SMMoving) node;
                showMovePatternToIndexHelper(moving.getMovingStartIndex(), moving.getMovingEndIndex(), message);
            }
            else{
                // node only contains a message and is no instance of a child class.
                Runnable r = () -> enqueueMessageHelper(message);
                queueThread.enqueueRunnable(r);
            }
            queuePointer++;
        }
    }
// animation call ------------------------------------------------------------------------------------------------------
    /**
     * This is a helper method that draws the rectangle and corresponding character with the correct colour.
     * @param textIndex The index in the text that the pattern will first be drawn at.
     * @param patternIndex The index in the pattern that the marking will be drawn.
     * @param stateMarking  The marking of the index in the pattern specified.
     */
    public void animateChange(int textIndex, int patternIndex, SortingQueueNodeMarking stateMarking){
        cvsHelper.drawBackground();
        switch (stateMarking){
            case COMPARING:
                cvsHelper.drawTextStringWithColour(text, textIndex, Color.ORANGE);
                cvsHelper.drawPatternProgression(pattern, patternIndex, textIndex, stateMarking);
                break;
            case SHOW_EQUAL:
                cvsHelper.drawTextStringWithColour(text, textIndex, Color.GREEN);
                cvsHelper.drawPatternProgression(pattern, patternIndex, textIndex, stateMarking);
                break;
            case SHOW_NOT_EQUAL:
                cvsHelper.drawTextStringWithColour(text, textIndex, Color.RED);
                cvsHelper.drawPatternProgression(pattern, patternIndex, textIndex, stateMarking);
                break;
            case NONE:
                cvsHelper.drawTextAndPatternAtPixelIndex(text, pattern, 0);
                break;
        }
    }
}
