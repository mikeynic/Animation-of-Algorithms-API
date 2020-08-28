package com.mikey.aop.stringmatching.components;

import com.mikey.aop.application.SM_CONSTANT;
import com.mikey.aop.stringmatching.enumerations.SortingQueueNodeMarking;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class is used to provide helper methods for the drawing of the pattern and text.
 * @author Michael Nicholson
 */
public class SMCanvasHelper {

    private final int offset = SM_CONSTANT.offset;

    private GraphicsContext gc;

// Initialisation methods ----------------------------------------------------------------------------------------------
    /**
     * This is the sole constructor of this class.
     * The graphics context that this will call drawings on is set up.
     */
    private GraphicsContext getGc(){
        gc =  gc == null ? SMCanvas.getInstance().getGraphicsContext2D() : gc;
        return gc;
    }

// Public methods ------------------------------------------------------------------------------------------------------
    /**
     * This is a helper method that sets the colours in the graphics context.
     * @param color Is the color that the graphics context colours will be set to draw with.
     */
    public void setGcColours(Color color){
        getGc().setFill(color);
        getGc().setStroke(color);
    }

    /**
     * This is a helper method to draw the correctly coloured background.
     * This is drawn when a new frame is being built.
     */
    public void drawBackground(){
        getGc().setFill(Color.rgb(230, 230, 230));
        getGc().fillRect(0,0, SM_CONSTANT.width.getWidth(), SM_CONSTANT.height.getHeight());
    }

    /**
     * This is a helper method to draw the text with a single index highlighted in a specified colour.
     * @param text          The text being drawn.
     * @param textIndex     The index in the text being coloured.
     * @param color         The color of the index in the text being coloured.
     */
    public void drawTextStringWithColour(String text, int textIndex, Color color){
        drawTextHelper(text, textIndex, color);
    }

    /**
     * This method draws the text and the pattern at a pixel specified. This is so that the pattern can have the effect
     * of moving from one index to the next when lots of calls are made to this method.
     * @param text              The text being drawn.
     * @param pattern           The pattern being drawn.
     * @param patternPixel      The pixel at which the drawing of the pattern will start.
     */
    public void drawTextAndPatternAtPixelIndex(String text, String pattern, int patternPixel) {
        char[] pArr = pattern.toCharArray();
        char[] tArr = text.toCharArray();
        int xOrd = offset;
        int yOrd = offset;

        for (char c : tArr){
            drawColouredBoxWithText(xOrd, yOrd, c, Color.WHITE);
            xOrd += SM_CONSTANT.blockWidth.getBlockWidth();
        }

        xOrd = patternPixel + offset;
        yOrd = offset*2 + SM_CONSTANT.blockWidth.getBlockWidth();

        for (char c : pArr) {
            drawColouredBoxWithText(xOrd, yOrd, c, Color.WHITE);
            xOrd += SM_CONSTANT.blockWidth.getBlockWidth();
        }
    }

    /**
     * This method draws the pattern at a specified index in the text. In addition, of one of the indices in
     * the pattern, and another in the text, will be marked with a colour specified by the
     * SortingQueueNodeMarking parameter.
     * @param pattern           The pattern being drawn.
     * @param patternIndex      The index in the pattern being coloured.
     * @param textIndex         The index in the text being marked with the same colour.
     * @param marking           Specifies the type of markings that will occur at the patternIndex and textIndex.
     */
    public void drawPatternProgression(String pattern, int patternIndex, int textIndex, SortingQueueNodeMarking marking){
        switch (marking){
            case SHOW_EQUAL:
                patternProgressionHelper(pattern, textIndex, patternIndex, Color.GREEN);
                break;
            case COMPARING:
                patternProgressionHelper(pattern, textIndex, patternIndex, Color.ORANGE);
                break;
            case SHOW_NOT_EQUAL:
                patternProgressionHelper(pattern, textIndex, patternIndex, Color.RED);
                break;
        }
    }

// Private methods -----------------------------------------------------------------------------------------------------
    /**
     * This is a helper method to draw the pattern with an index being marked. This index will be drawn with the
     * colour specified as the colorModifier parameter.
     * @param pattern       The pattern being drawn.
     * @param textIndex     The index in the text that the pattern should be drawn at.
     * @param patternIndex  The index in the pattern that is being marked.
     * @param colorModifier The color that the specified index in the pattern is being marked with.
     */
    private void patternProgressionHelper(String pattern, int textIndex, int patternIndex, Color colorModifier){
        char[] arr = pattern.toCharArray();
        int xOrd = offset + (textIndex - patternIndex)*SM_CONSTANT.blockWidth.getBlockWidth();
        int yOrd = offset*2 + SM_CONSTANT.blockWidth.getBlockWidth();
        Color color;
        for(int i=0; i<pattern.length(); i++){
            if(i < patternIndex) {
//                color = Color.GREEN;
                color = Color.WHITE;
            }
            else if(i == patternIndex)
                color = colorModifier;
            else
                color = Color.WHITE;
            drawColouredBoxWithText(xOrd, yOrd, arr[i], color);
            xOrd += SM_CONSTANT.blockWidth.getBlockWidth();
        }
    }

    /**
     * Helper method for drawing the text with a coloured index.
     * @param text      The text being drawn.
     * @param index     The index being coloured in the text.
     * @param color     The colour that the specified index in the text will be coloured.
     */
    private void drawTextHelper(String text, int index, Color color){
        int xOrd = SM_CONSTANT.offset;
        int yOrd = offset;
        char[] arr = text.toCharArray();
        for(int i=0; i<arr.length; i++){
            if(i == index)
                this.drawColouredBoxWithText(xOrd, yOrd, arr[i], color);
            else
                this.drawColouredBoxWithText(xOrd, yOrd, arr[i], Color.WHITE);

            xOrd += SM_CONSTANT.blockWidth.getBlockWidth();
        }
    }

    /**
     * Helper method to draw the boxes for each character in the pattern and the text.
     * @param x         The x ordinate that the box will be drawn at.
     * @param y         The y ordinate that the box will be drawn at.
     * @param character The character that will be drawn inside the box.
     * @param color     The color that this individual box will be drawn with.
     */
    private void drawColouredBoxWithText(int x, int y, char character, Color color){
        int width = SM_CONSTANT.blockWidth.getBlockWidth();
        int height = SM_CONSTANT.blockWidth.getBlockWidth();
        setGcColours(color);
        int xStart = x;
        int xEnd = x+width;
        int yStart = y;
        int yEnd = y+height;
        int fontSize = xEnd-xStart;
        getGc().setFont(new Font(fontSize));
        getGc().fillRect(x, y, width, height);
        setGcColours(Color.BLACK);
        getGc().strokeRect(x, y, width, height);
        gc.setFont(new Font(xEnd-xStart-10));
        getGc().fillText(character + "", x+0.25*fontSize, y+0.8*fontSize);
    }
}
