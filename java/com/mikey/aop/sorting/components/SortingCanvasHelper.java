package com.mikey.aop.sorting.components;

import com.google.common.annotations.VisibleForTesting;
import com.mikey.aop.application.SORTING_CONSTANT;
import com.mikey.aop.sorting.datastructures.Index;
import com.mikey.aop.sorting.enumerations.MarkingState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class is used to help to draw the components on the canvas.
 * @author Michael Nicholson
 */
public class SortingCanvasHelper {

    public GraphicsContext gc;

    /**
     * This is the sole constructor for this class.
     * @param gc is the graphics context that all changes will be drawn to.
     */
    public SortingCanvasHelper(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * This is a helper method that sets the colours in the graphics context.
     * @param color Is the color that the graphics context colours will be set to draw with.
     */
    public void setGcColours(Color color){
        gc.setFill(color);
        gc.setStroke(color);
    }

    /**
     * This is a helper method to draw the correctly coloured background.
     * This is drawn when a new frame is being built.
     */
    public void drawBackground(){
        gc.setFill(SORTING_CONSTANT.BACKGROUND_COLOR);
        gc.fillRect(0,0, SORTING_CONSTANT.width.getWidth(), SORTING_CONSTANT.height.getHeight());
    }

    /**
     * This is the method to draw the array representation of the current state of the array.
     * @param arr Is the array of Index objects. These have additional information such as marking colours and fill
     *            colours.
     */
    public void drawArray(Index[] arr){
        setGcColours(Color.BLACK);
        int pixelIndex = SORTING_CONSTANT.OFFSET;
        for (Index index : arr) {
            drawRectAndText(index, pixelIndex);
            pixelIndex += SORTING_CONSTANT.blockWidth.getBlockWidth();
        }
    }

    /**
     * Draws the array with two indices missing. This is so the missing indices can be animated as swapping.
     * @param arr Is the array of Index objects. These have additional information such as marking colours and fill
     *            colours.
     * @param index1 An index that is not being drawn by this function. The order does not matter.
     * @param index2 An index that is not being drawn by this function. The order does not matter.
     */
    public void drawArray(Index[] arr,  int index1, int index2){
        setGcColours(Color.BLACK);
        int pixelIndex = SORTING_CONSTANT.OFFSET;
        for(int i=0; i<arr.length; i++){
            if(i != index1 && i != index2){
                drawRectAndText(arr[i], pixelIndex);
                setGcColours(Color.BLACK);
            }
            pixelIndex += SORTING_CONSTANT.blockWidth.getBlockWidth();
        }
    }

    /**
     * Finds the color needed to fill the rectangle with text.
     * @param index The index that is being inspected for swapping or marking.
     * @return The color used for drawing the rectangle and text.
     */
    public Color selectColour(Index index){
        Color color;
        if (index.isComparing())
            color = SORTING_CONSTANT.COMPARISON_COLOUR;
        else if (index.isSwapping())
            color = SORTING_CONSTANT.SWAPPING_COLOUR;
        else
            color = Color.BLACK;
        return color;
    }

    /**
     * Finds the colour needed for the marking on the index.
     * @param index The index that is being inspected for marking.
     * @return The colour used for marking the rectangle around the perimeter
     */
    public Color selectMarkingColour(Index index){
        MarkingState state = index.getMarked();
        Color markingColour = Color.WHITE;
        if(state == MarkingState.CUSTOM_MARKED){
            markingColour = index.getColor();
        }
        if(state == MarkingState.DEFAULT_MARKED){
            markingColour = SORTING_CONSTANT.MARKED_COLOUR;
        }
        return markingColour;
    }

    /**
     * Draws a rectangle with the correct markings and fillings at the x ordinate specified.
     * @param index     The index being drawn on the canvas
     * @param xOrd      The x ordinate of the rectangle being drawn
     */
    public void drawRectAndText(Index index, int xOrd){
        Color fillColour = selectColour(index);
        Color markingColour = selectMarkingColour(index);
        setGcColours(fillColour);

        int height = SORTING_CONSTANT.height.getHeight();
        int bHeight = SORTING_CONSTANT.blockHeight.getBlockHeight();
        int bWidth = SORTING_CONSTANT.blockWidth.getBlockWidth();
        int value = index.getValue();
        int yOrd = height - bHeight * value;
        int fSize = SORTING_CONSTANT.FONT_SIZE;
        gc.setFont(new Font(fSize));
        if(SORTING_CONSTANT.ARRAY_REPRESENTATION_IS_ON){
            gc.strokeText("" + index.getValue(), xOrd + (bWidth/2 - (fSize/2)), bHeight*2+fSize);
            if(markingColour != Color.WHITE){
                setGcColours(Color.WHITE);
                gc.fillRect(xOrd, bHeight, bWidth, bHeight);
                setGcColours(markingColour);
                gc.fillRect(xOrd+1, bHeight+1, bWidth-2, bHeight-2);
                setGcColours(fillColour);
                gc.fillRect(xOrd+9, bHeight+9, bWidth-18, bHeight-18);
            }
            else{
                setGcColours(markingColour);
                gc.fillRect(xOrd, bHeight, bWidth, bHeight);
                setGcColours(fillColour);
                gc.fillRect(xOrd+1, bHeight+1, bWidth-2, bHeight-2);
            }
        }
        else{
            gc.strokeText("" + index.getValue(), xOrd + (bWidth/2 - (fSize/2)), yOrd-fSize);
            if(markingColour != Color.WHITE){
                setGcColours(Color.WHITE);
                gc.fillRect(xOrd, yOrd, bWidth, bHeight*value);
                setGcColours(markingColour);
                gc.fillRect(xOrd+1, yOrd+1, bWidth-2, bHeight*value-2);
                setGcColours(fillColour);
                gc.fillRect(xOrd+9, yOrd+9, bWidth-18, bHeight*value-18);
            }else{
                setGcColours(markingColour);
                gc.fillRect(xOrd, yOrd, bWidth, bHeight*value);
                setGcColours(fillColour);
                gc.fillRect(xOrd+1, yOrd+1, bWidth-2, bHeight*value-2);
            }
        }
    }
}
