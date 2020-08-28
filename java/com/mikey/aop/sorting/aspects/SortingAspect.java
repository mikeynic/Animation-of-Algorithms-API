package com.mikey.aop.sorting.aspects;

import com.mikey.aop.sorting.components.SortingCanvas;
import javafx.scene.paint.Color;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This class contains all of the join points and corresponding advice needed to retrieve the data from the
 * user callable methods in GenericSort using AOP.
 * @author Michael Nicholson
 */
@Aspect
public class SortingAspect {

    /**
     * Helper method to retrieve the singleton instance of the sorting canvas.
     * Used in other methods in this aspect class to reflect the relevant changes that are called.
     * @return Returns the singleton instance of the SortingCanvas
     */
    private static SortingCanvas getCanvas(){
        return SortingCanvas.getInstance();
    }

    /**
     * Targets the showInitial method in GenericSort.
     * Passes the data in the correct format to the setArr method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showInitial as an Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showInitial(int[]))")
    public void ShowInitialAdvice(JoinPoint joinPoint) {
//        System.out.println("ShowInitialAdvice called..");
        int[] arr = (int[]) joinPoint.getArgs()[0];
        int[] copy = new int[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        getCanvas().setArr(copy);
    }

    /**
     * Targets all showMark method signatures in GenericSort.
     * Passes the data in the correct format to the enqueueMark method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showMark as an Object array.
     */

    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showMark(..))")
    public void ShowMarkAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        int markIndex = (int) args[0];
        if(args.length == 1)
            getCanvas().enqueueMark(markIndex, null);
        else if(args[1] instanceof String)
            getCanvas().enqueueMark(markIndex, (String) args[1]);
        else if(args[1] instanceof Color){
            String message = null;
            if(args.length == 3)
                message = (String) args[2];
            getCanvas().enqueueMark(markIndex, (Color) args[1], message);
        }
    }

    /**
     * Targets all showUnMark method signatures in GenericSort.
     * Passes the data in the correct format to the enqueueUnMark method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showUnMark as an Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showUnMark(..))")
    public void ShowUnmarkAdvice(JoinPoint joinPoint){
//        System.out.println("ShowUnmarkAdvice called..");
        int unmarkedIndex = (int) joinPoint.getArgs()[0];
        String message = null;
        if(joinPoint.getArgs().length == 2){
            message = (String) joinPoint.getArgs()[1];
        }
        getCanvas().enqueueUnmark(unmarkedIndex, message);
    }

    /**
     * Targets all showSwap method signatures in GenericSort.
     * Passes the data in the correct format to the enqueueSwap method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showSwap as an Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showSwap(..))")
    public void ShowSwapAdvice(JoinPoint joinPoint){
//        System.out.println("ShowSwapAdvice called..");
        int firstIndex = (int) joinPoint.getArgs()[0];
        int secondIndex = (int) joinPoint.getArgs()[1];
        String message = null;

        if(joinPoint.getArgs().length == 3){
            message = (String) joinPoint.getArgs()[2];
        }
        getCanvas().enqueueSwap(firstIndex, secondIndex, message);
    }

    /**
     * Targets all showComparison method signatures in GenericSort.
     * Passes the data in the correct format to the enqueueComparison method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showComparison as an Object
     *                  array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showComparison(..))")
    public void ShowComparisonAdvice(JoinPoint joinPoint){
//        System.out.println("ShowComparisonAdvice called..");
        int firstIndex = (int) joinPoint.getArgs()[0];
        int secondIndex = (int) joinPoint.getArgs()[1];
        String message = null;
        if(joinPoint.getArgs().length == 3)
            message = (String) joinPoint.getArgs()[2];
        getCanvas().enqueueComparison(firstIndex, secondIndex, message);
    }

    /**
     * Targets all showUnMarkAll method signatures in GenericSort.
     * Passes the data in the correct format to the enqueueUnmarkAll method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameters passed to showUnMarkAll as an Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showUnMarkAll(..))")
    public void ShowUnMarkAllAdvice(JoinPoint joinPoint) {
//        System.out.println("ShowUnMarkAllAdvice called..");
        String message = null;
        if (joinPoint.getArgs().length == 1) {
            message = (String) joinPoint.getArgs()[0];
        }
        getCanvas().enqueueUnmarkAll(message);
    }

    /**
     * Targets the showMessage method in GenericSort.
     * Passes the data in the correct format to the enqueueMessage method in the sorting canvas.
     * @param joinPoint This is the join point that retrieves the parameter passed to showMessage from the Object array.
     */
    @Before("execution(public void com.mikey.aop.sorting.algorithms.GenericSort.showMessage(String))")
    public void EnqueueShowMessageAdvice(JoinPoint joinPoint){
//        System.out.println("EnqueueShowMessageAdvice called..");
        getCanvas().enqueueMessage((String) joinPoint.getArgs()[0]);
    }
}
