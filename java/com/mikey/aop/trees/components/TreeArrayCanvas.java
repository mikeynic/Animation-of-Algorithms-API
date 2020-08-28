package com.mikey.aop.trees.components;

import com.mikey.aop.application.TREE_CONSTANTS;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * TreeArrayCanvas extends the JavaFx Canvas class.
 * This class is used to reflect the changes in the data structure as an array. The representation should be updated
 * whenever an operation is performed on the tree array.
 * @author Michael Nicholson
 */
public class TreeArrayCanvas extends Canvas {

    private static TreeArrayCanvas instance;
    private GraphicsContext gc;

    /**
     * Enforces the singleton design pattern
     * @return The singleton instance of the array canvas.
     */
    public static synchronized TreeArrayCanvas getInstance(){
        if(instance == null)
            instance = new TreeArrayCanvas();
        return instance;
    }

    /**
     * The sole constructor for this class. This sets the width and height of the TreeArrayCanvas instance.
     */
    public TreeArrayCanvas() {
        super(TREE_CONSTANTS.WIDTH/6, TREE_CONSTANTS.HEIGHT);
        this.gc = this.getGraphicsContext2D();
    }

    /**
     * Helper method for drawing the background with the correct colour.
     */
    private void drawBackground(){
        setGcColour(Color.rgb(255, 255, 255));
        gc.fillRect(0,0, TREE_CONSTANTS.WIDTH/6, TREE_CONSTANTS.HEIGHT);
    }

    /**
     * This is a helper method that sets the colours in the graphics context.
     * @param color Is the color that the graphics context colours will be set to draw with.
     */
    private void setGcColour(Color color){
        gc.setStroke(color);
        gc.setFill(color);
    }

    /**
     * This is the method to draw the array representation of the current state of the tree array.
     * @param arr Is the array representing the tree.
     */
    public void drawArr(int[] arr){
        drawBackground();
        int diameter = TREE_CONSTANTS.NODE_DIAMETER;
        int fSize = TREE_CONSTANTS.FONT_SIZE;
        int x = diameter/2;
        int y = 0;
        setGcColour(Color.BLACK);
        for(int i=0; i<arr.length; i++){
            y += diameter/2;
            if(i != arr.length-1)
                gc.strokeRect(x, y, diameter, diameter);
            gc.fillText("" + i, 1, y+(fSize/2)+1);
            gc.fillText("" + arr[i], x+1, y+(fSize/2)+1);
        }
    }
}
