package com.mikey.aop.application;


import com.mikey.aop.sorting.algorithms.GenericSort;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;
import com.sun.javafx.scene.DirtyBits;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that maintains all of the global constants used for animating sorting algorithms.
 * @author Michael Nicholson
 */
public class SORTING_CONSTANT {
//    public static Integer WIDTH = 700;
//    public static Integer HEIGHT = 500;
//    public static final int BLOCK_HEIGHT = 50;
//    public static final int BLOCK_WIDTH = 60;

    public static Height height = new Height(500);
    public static Width width = new Width(700);
    public static BlockHeight blockHeight = new BlockHeight(50);
    public static BlockWidth blockWidth = new BlockWidth(50);

    public static int arraySize;
    public static int maxElement;

    public static final int OFFSET = 50;
    public static int ANIMATION_LENGTH = 10;
    public static final Color SWAPPING_COLOUR = Color.GREEN;
    public static final Color COMPARISON_COLOUR = Color.ORANGE;
    public static final Color MARKED_COLOUR = Color.RED;
    public static final int FONT_SIZE = 12;
    public static GenericSort genericSort;
    public static AtomicBoolean APPLICATION_EXIT = new AtomicBoolean(false);
    public static boolean ARRAY_REPRESENTATION_IS_ON = false;
    public static Color BACKGROUND_COLOR = Color.rgb(230, 230, 230);
}
