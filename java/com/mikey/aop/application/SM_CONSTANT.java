package com.mikey.aop.application;

import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;

/**
 * Class that maintains all of the global constants used for animating string matching algorithms.
 * @author Michael Nicholson
 */
public class SM_CONSTANT {
//    public final static int WIDTH = 1000;
//    public static final int HEIGHT = 300;

    public static Width width = new Width(1000);
    public static Height height = new Height(300);
    public static BlockWidth blockWidth = new BlockWidth(45);
//    public static BlockHeight blockHeight = new BlockHeight(45);

    public static int maxSize;
    public static final int fontSize = blockWidth.getBlockWidth()/2;
    public static final int offset = 10;
    public static int frameLength = 10;

    public static GenericStringMatch genericStringMatch;

    public static boolean APPLICATION_EXIT = false;
}
