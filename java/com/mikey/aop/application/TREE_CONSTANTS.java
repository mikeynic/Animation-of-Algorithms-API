package com.mikey.aop.application;

import com.mikey.aop.trees.algorithms.GenericTreeAlgorithm;
import javafx.scene.paint.Color;


/**
 * Class that maintains all of the global constants used for animating tree algorithms.
 * @author Michael Nicholson
 */
public class TREE_CONSTANTS {

    public static final int WIDTH = 750;
    public static final int HEIGHT = 500;
    public static final int NODE_DIAMETER = 40;
    public static final int FONT_SIZE = 20;
    public static int SWAP_FRAME_LENGTH = 10;
//    public static int FRAME_LENGTH = SWAP_FRAME_LENGTH*70;

    public static final Color DEFAULT_MARKING_COLOR = Color.RED;

    public static GenericTreeAlgorithm genericTreeAlgorithm;

    public static boolean APPLICATION_EXIT = false;
}
