package com.mikey.aop.trees.aspects;

import com.mikey.aop.exceptions.NotALeafNodeException;
import com.mikey.aop.trees.components.TreeCanvas;
import javafx.scene.paint.Color;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This class contains all of the join points and corresponding advice needed to retrieve the data from the
 * user callable methods in GenericTreeAlgorithm using AOP.
 * @author Michael Nicholson
 */
@Aspect
public class TreeAspect {

    /**
     * Helper method to retrieve the singleton instance of the tree canvas.
     * Used in other methods in this aspect class to reflect the relevant changes that are called.
     * @return Returns the singleton instance of the TreeCanvas
     */
    private static TreeCanvas getCanvas(){
        return TreeCanvas.getInstance();
    }

    /**
     * Targets the showArray method in GenericTreeAlgorithm.
     * Passes the data in the correct format to the setArray method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showArray as an Object array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showArray(int[]))")
    public void ShowArrayAspect(JoinPoint joinPoint){
//        System.out.println("ShowArrayAspect");
        int[] arr = (int[]) joinPoint.getArgs()[0];
        int[] copyArr = new int[arr.length];
        System.arraycopy(arr, 0, copyArr, 0, arr.length);
        getCanvas().setArray(copyArr);
    }

    /**
     * Targets all showMarkIndex method signatures in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueMarkIndexInTree method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showMarkIndex as an Object array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showMark(..))")
    public void showMarkIndexAdvice(JoinPoint joinPoint){
//        System.out.println("showMarkIndexAdvice");
        Object[] args = joinPoint.getArgs();
        int index = (int) args[0];
        int argLen = args.length;
        // int | int, String | int, Color| int, Color, String
        switch (argLen){
            case 1:
                getCanvas().enqueueMarkIndexInTree(index, null);
                break;
            case 2:
                if(args[1] instanceof String)
                    getCanvas().enqueueMarkIndexInTree(index, (String) args[1]);
                else
                    getCanvas().enqueueMarkIndexWithColorInTree(index, (Color) args[1], null);
                break;
            case 3:
                getCanvas().enqueueMarkIndexWithColorInTree(index, (Color) args[1], (String) args[2]);
        }
    }

    /**
     * Targets all showUnMark method signatures in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueUnMark method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showUnMark as an Object array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showUnMark(..))") // int, int String
    public void ShowUnmarkIndex(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int index = (int) joinPoint.getArgs()[0];
        String message = null;
        if(args.length == 2)
            message = (String) args[1];
        getCanvas().enqueueUnmarkIndexInTree(index, message);
    }

    /**
     * Targets all showSwap method signatures in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueSwap method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showSwap as an Object array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showSwap(..))")
    public void ShowSwapAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int firstIndex = (int) args[0];
        int secondIndex = (int) args[1];
        String message = null;
        // int, int | int, int, String
        if(args.length == 3)
            message = (String) args[2];
        getCanvas().enqueueSwap(firstIndex, secondIndex, message);
    }

    /**
     * Targets all showInsert method signatures in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueInsert method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showInsert as an Object
     *                  array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showInsert(..))") // int, int | int, int, String
    public void showInsertAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int element = (int) args[0];
        int index = (int) args[1];
        String message = null;
        if(args.length == 3)
            message = (String) args[2];
        getCanvas().enqueueInsert(element, index, message);
    }

    /**
     * Targets all showDelete method signatures in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueInsert method in the tree canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showInsert as an Object
     *                  array.
     */
    @Before("execution(public void com.mikey.aop.trees.algorithms.GenericTreeAlgorithm.showDelete(..))") // int | int, String
    public void ShowDeleteAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int index = (int) args[0];
        String message = null;
        if(args.length == 2)
            message = (String) args[1];
        getCanvas().enqueueDelete(index, message);
    }

    /**
     * Targets the showMessage method in GenericTreeAlgorithm.
     * Passes the data in the correct format to the enqueueMessage method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameter passed to showMessage from the Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showMessage(String))")
    public void EnqueueShowMessageAdvice(JoinPoint joinPoint){
//        System.out.println("EnqueueShowMessageAdvice called..");
        getCanvas().enqueueMessage((String) joinPoint.getArgs()[0]);
    }
}
