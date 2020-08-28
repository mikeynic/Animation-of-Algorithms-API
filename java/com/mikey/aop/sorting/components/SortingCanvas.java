package com.mikey.aop.sorting.components;

import com.google.common.annotations.VisibleForTesting;
import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.SORTING_CONSTANT;
import com.mikey.aop.sorting.datastructures.*;
import com.mikey.aop.sorting.enumerations.MarkedAction;
import com.mikey.aop.sorting.enumerations.MarkingState;
import com.mikey.aop.sorting.threading.HandlerQueueThread;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

/**
 * SortingCanvas extends the JavaFx Canvas class.
 * This class is used to reflect the changes in the data structure as an animation on the screen.
 * @author Michael Nicholson
 */
public class SortingCanvas extends Canvas {

    private static SortingCanvas instance;
    private SortingCanvasHelper sortingCanvasHelper;

    private Index[] arr;
    private HandlerQueueThread handlerQueueThread;

    public SortingQueue queueActions;

// Initialisation methods ----------------------------------------------------------------------------------------------
    /**
     * Enforces the singleton design pattern.
     * @return The singleton  instance of the SortingCanvas.
     */
    public static synchronized SortingCanvas getInstance(){
        if(instance == null){
            instance = new SortingCanvas();
        }
        return instance;
    }

    /**
     * This is the sole constructor of the class and contains setup code for the operation queues and threads.
     */
    public SortingCanvas() {
        super(SORTING_CONSTANT.width.getWidth(), SORTING_CONSTANT.height.getHeight());
        handlerQueueThread = new HandlerQueueThread();
        handlerQueueThread.setDaemon(true);
        handlerQueueThread.start();
        queueActions = new SortingQueue();
        this.sortingCanvasHelper = new SortingCanvasHelper(this.getGraphicsContext2D());

        heightProperty().addListener((observable, oldValue, newValue) -> {
            prefHeight(newValue.intValue());
            sortingCanvasHelper.drawBackground();
            sortingCanvasHelper.drawArray(arr);
        });

        widthProperty().addListener((observable, oldValue, newValue) -> {
            prefWidth(newValue.intValue());
            sortingCanvasHelper.drawBackground();
            sortingCanvasHelper.drawArray(arr);
        });
    }

// Controller methods---------------------------------------------------------------------------------------------------
    /**
     * Method to map the play button to the play method in the internal thread that controls play/pause functionality
     * on the GUI.
     * */
    public void play(){
        handlerQueueThread.play();
    }

    /**
     * Method to map the pause button to the pause method in the internal thread that controls play/pause functionality
     * on the GUI.
     * */
    public void pause(){
        handlerQueueThread.pause();
    }

    /**
     * Method to map the step button to the step method in the internal thread that controls play/pause functionality
     * on the GUI.
     * */
    public void step(){
        handlerQueueThread.step();
    }

    /**
     * This method called from the onResetButtonPress method in SortingController
     * resets the SortingCanvas internal queues and threads to their initial states. The GUI also reflects this change
     * by showing the initial state of the array.
     */
    public void reset(){
        handlerQueueThread.resetQueue();
        int qPointer = 0;

        while(qPointer < queueActions.size()){
            SortingQueueNode node = queueActions.get(qPointer);
            String message = node.getMessage();

            if(node instanceof SetArray){
                SetArray setArray = (SetArray) node;
                setArrHelper(setArray.getArray());
            }
            else if(node instanceof Swap){
                Swap swap = (Swap) node;
                enqueueSwapHelper(swap.getIndex1(), swap.getIndex2(), message);
            }
            else if(node instanceof Compare){
                Compare compare = (Compare) node;
                enqueueComparisonHelper(compare.getCompareIndex1(), compare.getCompareIndex2(), message);
            }
            else if(node instanceof Mark){
                Mark mark = (Mark) node;
                doMarkingHelper(mark.getMarkIndex(), mark.getMarkedAction(), mark.getMarkedColour(), message);
            }
            else{
                // node only contains a message and is no instance of a child class.
                Runnable r = () -> enqueueMessageHelper(message);
                handlerQueueThread.enqueueRunnable(r);
            }

            qPointer++;
        }
    }

    /**
     * Helper method to draw a new frame on the canvas that shows the current array representation.
     */
    public void drawArrayRepresentation(){
        sortingCanvasHelper.drawBackground();
        sortingCanvasHelper.drawArray(arr);
    }

// Aspect methods -----------------------------------------------------------------------------------------------------
    /**
     * This is the method that the SortingAspect calls in order to enqueue a SetArray operation in to the internal
     * queue.
     * @param array Is the array that is used to create an array of Index objects that represent the array with added
     *              information.
     */
    public void setArr(int[] array) {
        int max = 0;
        for(int i=0; i< array.length; i++){
            if(array[i] > max)
                max = array[i];
        }
        SORTING_CONSTANT.maxElement = max;
        SORTING_CONSTANT.arraySize = array.length;

        SortingQueueNode node = new SetArray(array);
        queueActions.add(node);
        setArrHelper(array);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a Message operation in to the internal
     * queue.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     */
    public void enqueueMessage(String message){
        SortingQueueNode node = new SortingQueueNode();
        node.setMessage(message);
        queueActions.add(node);
        Runnable r = () -> enqueueMessageHelper(message);
        handlerQueueThread.enqueueRunnable(r);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a Swap operation in to the internal
     * queue.
     * @param index1    Is an index of one of the elements in the swap. Order does not matter.
     * @param index2    Is an index of one of the elements in the swap. Order does not matter.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   This will be thrown when either of the indices supplied for the swap do
     *                                          not fall within the range of the array.
     */
    public void enqueueSwap(int index1, int index2, String message) throws ArrayIndexOutOfBoundsException{
        try{Index error1 = arr[index1];Index error2 = arr[index2];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        SortingQueueNode node = new Swap(index1, index2);
        node.setMessage(message);
        queueActions.add(node);
        enqueueSwapHelper(index1, index2, message);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a Compare operation in to the internal
     * queue.
     * @param index1    Is an index of one of the elements in the comparison. Order does not matter.
     * @param index2    Is an index of one of the elements in the comparison. Order does not matter.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   Thrown if one of the two supplied indices do not fall within the range
     *                                          of the tree array.
     */
    public void enqueueComparison(int index1, int index2, String message) throws ArrayIndexOutOfBoundsException{
        try{Index error1 = arr[index1];Index error2 = arr[index2];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        SortingQueueNode node = new Compare(index1, index2);
        node.setMessage(message);
        queueActions.add(node);
        enqueueComparisonHelper(index1, index2, message);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a marking operation of the default colour
     * in to the internal queue.
     * @param markIndex Is the index of one the element being marked.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     * @throws ArrayIndexOutOfBoundsException Thrown if the supplied index does not fall within the range of the tree array.
     */
    public void enqueueMark(int markIndex, String message) throws ArrayIndexOutOfBoundsException{
        try{Index error = arr[markIndex];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        SortingQueueNode node = new Mark(markIndex, MarkedAction.BEING_MARKED, null);
        node.setMessage(message);
        queueActions.add(node);
        doMarkingHelper(markIndex, MarkedAction.BEING_MARKED, null, message);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a marking operation of a specified colour
     * in to the internal queue.
     * @param markIndex Is the index of one the element being marked.
     * @param colour    Is the custom colour of the marking.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     * @throws ArrayIndexOutOfBoundsException Thrown if the supplied index does not fall within the range of the tree array.
     */
    public void enqueueMark(int markIndex, Color colour, String message) throws ArrayIndexOutOfBoundsException{
        try{Index error = arr[markIndex];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        SortingQueueNode node = new Mark(markIndex, MarkedAction.BEING_CUSTOM_MARKED, colour);
        node.setMessage(message);
        queueActions.add(node);
        doMarkingHelper(markIndex, MarkedAction.BEING_CUSTOM_MARKED, colour, message);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue an unmark operation in to the internal queue.
     * @param unmarkedIndex Is the index of the element being unmarked.
     * @param message       Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                      reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     * @throws ArrayIndexOutOfBoundsException Thrown if the supplied index does not fall within the range of the tree array.
     */
    public void enqueueUnmark(int unmarkedIndex, String message) throws ArrayIndexOutOfBoundsException{
        try{Index error = arr[unmarkedIndex];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
        SortingQueueNode node = new Mark(unmarkedIndex, MarkedAction.BEING_UNMARKED, null);
        node.setMessage(message);
        queueActions.add(node);
        doMarkingHelper(unmarkedIndex, MarkedAction.BEING_UNMARKED, null, message);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue an unmark operation for all indices in the
     * array in to the internal queue.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     */
    public void enqueueUnmarkAll(String message){
        SortingQueueNode node = new Mark(0, MarkedAction.BEING_UNMARKED_ALL, null);
        node.setMessage(message);
        queueActions.add(node);
        doMarkingHelper(0, MarkedAction.BEING_UNMARKED_ALL, null, message);
    }

// Helper methods ------------------------------------------------------------------------------------------------------
    /**
     * This is the method that creates a Runnable for the operation to reflect the changes in the GUI.
     * The Runnable is enqueued to the HandlerQueueThread to then be executed.
     * @param array Is the array that is used to create an array of Index objects that represent the array with added
     *              information.
     */
    private void setArrHelper(int[] array) {
        int[] userArr = new int[array.length];
        System.arraycopy(array, 0, userArr, 0, array.length);
        this.arr = new Index[array.length];
        for (int i=0; i<arr.length; i++){
            this.arr[i] = new Index();
            this.arr[i].setValue(array[i]);
            this.arr[i].setIndex(i);
        }
        Platform.runLater(() -> {
            sortingCanvasHelper.drawBackground();
            sortingCanvasHelper.drawArray(arr);
        });

        Runnable r = () -> {
            Runnable executor = () -> {
                Runnable animation = this::drawArrayRepresentation;
                Platform.runLater(animation);
            };
            handlerQueueThread.enqueueToExecutor(executor);
        };
        handlerQueueThread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to animate the swapping of two elements in the array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index1    Is an index of one of the elements in the swap. Order does not matter.
     * @param index2    Is an index of one of the elements in the swap. Order does not matter.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     */
    private void enqueueSwapHelper(int index1, int index2, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                int previousPixel1 = SORTING_CONSTANT.OFFSET + SORTING_CONSTANT.blockWidth.getBlockWidth()*index1;
                int previousPixel2 = SORTING_CONSTANT.OFFSET + SORTING_CONSTANT.blockWidth.getBlockWidth()*index2;
                final int nextPixel1 = previousPixel2;
                final int nextPixel2 = previousPixel1;
                arr[index1].setSwapping(true);
                arr[index2].setSwapping(true);
                /*MarkingState markingState1 = arr[index1].getMarked();
                MarkingState markingState2 = arr[index2].getMarked();
                arr[index1].setMarked(MarkingState.NOT_MARKED);
                arr[index2].setMarked(MarkingState.NOT_MARKED);*/
                while(previousPixel1 != nextPixel1 && previousPixel2 != nextPixel2){
                    int finalPreviousPixel = previousPixel1;
                    int finalPreviousPixel1 = previousPixel2;
                    Runnable animation = () -> {
                        sortingCanvasHelper.drawBackground();
                        sortingCanvasHelper.setGcColours(Color.BLACK);
                        sortingCanvasHelper.drawArray(arr, index1, index2);
                        sortingCanvasHelper.drawRectAndText(arr[index1], finalPreviousPixel);
                        sortingCanvasHelper.drawRectAndText(arr[index2], finalPreviousPixel1);
                    };
                    Platform.runLater(animation);
                    try{Thread.sleep(SORTING_CONSTANT.ANIMATION_LENGTH);}catch (Exception e){e.printStackTrace();}
                    if (previousPixel1 > nextPixel1){ previousPixel1--; } else { previousPixel1++; }
                    if (previousPixel2 > nextPixel2){ previousPixel2--; } else { previousPixel2++; }
                }
                /*arr[index1].setMarked(markingState1);
                arr[index2].setMarked(markingState2);*/
                arr[index1].setSwapping(false);
                arr[index2].setSwapping(false);

                // this marks the element rather than the index
                Index temp = arr[index1];
                arr[index1] = arr[index2];
                arr[index2] = temp;
                // this marks the index rather than the element
                /*Index temp1 = arr[index1].getCopy();
                MarkingState marking1 = arr[index1].getMarked();
                Color color1 = arr[index1].getColor();
                Index temp2 = arr[index2].getCopy();
                MarkingState marking2 = arr[index2].getMarked();
                Color color2 = arr[index2].getColor();
                arr[index1] = temp2;
                arr[index1].setMarked(marking1);
                arr[index1].setColor(color1);
                arr[index2] = temp1;
                arr[index2].setMarked(marking2);
                arr[index2].setColor(color2);*/
                Runnable animation = () -> {
                    sortingCanvasHelper.drawBackground();
                    sortingCanvasHelper.drawArray(arr);
                };
                Platform.runLater(animation);
                try{Thread.sleep(SORTING_CONSTANT.ANIMATION_LENGTH*40);}catch (Exception e){e.printStackTrace();}
            };
            handlerQueueThread.enqueueToExecutor(executor);
        };
        handlerQueueThread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to animate the comparison of two elements in the array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index1    Is an index of one of the elements in the comparison. Order does not matter.
     * @param index2    Is an index of one of the elements in the comparison. Order does not matter.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     */
    private void enqueueComparisonHelper(int index1, int index2, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                Runnable animation = () -> {
                    arr[index1].setComparing(true);
                    arr[index2].setComparing(true);
                    drawArrayRepresentation();
                    arr[index1].setComparing(false);
                    arr[index2].setComparing(false);
                };
                Platform.runLater(animation);
                try{Thread.sleep(SORTING_CONSTANT.ANIMATION_LENGTH*40);}catch (Exception e){e.printStackTrace();}
            };
            handlerQueueThread.enqueueToExecutor(executor);
        };
        handlerQueueThread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to animate the marking of an element in the array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param markIndex Is the index of one the element being marked.
     * @param ma        Specifies the marking action of this method call since there are a lot of combinations
     *                  that this could be
     * @param colour    Is the custom colour of the marking.
     * @param message   Is the optional parameter for the overloading of methods in the GenericSort class used to
     *                  reflect changes in the AACurrentTextArea. This can be null and will be handled accordingly.
     */
    private void doMarkingHelper(int markIndex, MarkedAction ma, Color colour, String message){
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                switch (ma){
                    case BEING_UNMARKED:
                        arr[markIndex].setMarked(MarkingState.NOT_MARKED);
                        break;
                    case BEING_MARKED:
                        arr[markIndex].setMarked(MarkingState.DEFAULT_MARKED);
                        break;
                    case BEING_CUSTOM_MARKED:
                        arr[markIndex].setMarked(MarkingState.CUSTOM_MARKED);
                        arr[markIndex].setColor(colour);
                        break;
                    case BEING_UNMARKED_ALL:
                        for(Index x : arr)
                            x.setMarked(MarkingState.NOT_MARKED);
                        break;
                }
                Runnable animation = () -> {
                    int indexPixel = SORTING_CONSTANT.OFFSET + SORTING_CONSTANT.blockWidth.getBlockWidth()*markIndex;
                    sortingCanvasHelper.drawBackground();
                    sortingCanvasHelper.setGcColours(Color.BLACK);
                    sortingCanvasHelper.drawArray(arr, markIndex, indexPixel);
                    sortingCanvasHelper.drawRectAndText(arr[markIndex], indexPixel);
                };
                Platform.runLater(animation);
                if(ma != MarkedAction.BEING_UNMARKED)
                    try{Thread.sleep(SORTING_CONSTANT.ANIMATION_LENGTH*20);}catch (Exception e){e.printStackTrace();}
            };
            handlerQueueThread.enqueueToExecutor(executor);
        };
        handlerQueueThread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to show a message on the AACurrentTextArea.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     */
    private void enqueueMessageHelper(String message){
        Runnable executor = () -> Platform.runLater(() -> AACurrentTextArea.getInstance().setCurrentMessage(message));
        handlerQueueThread.enqueueToExecutor(executor);
    }
}