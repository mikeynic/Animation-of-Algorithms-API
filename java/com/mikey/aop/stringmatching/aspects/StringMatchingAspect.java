package com.mikey.aop.stringmatching.aspects;

import com.mikey.aop.stringmatching.components.SMCanvas;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This class contains all of the join points and corresponding advice needed to retrieve the data from the
 * user callable methods in GenericStringMatch using AOP.
 * @author Michael Nicholson
 */
@Aspect
public class StringMatchingAspect {

    /**
     * Helper method to retrieve the singleton instance of the string matching canvas
     * Used in other methods in this aspect class to reflect the relevant changes that are called.
     */
    private static SMCanvas getCanvas(){
        return SMCanvas.getInstance();
    }


    /**
     * Targets the showInitialStringAndText method in GenericStringMatch.
     * Passes the data in the correct format to the sorting canvas. This includes the pattern and the
     * text. This advice also specifies to draw the initial state of the pattern and text.
     * @param joinPoint This is the join point that retrieves the parameters passed to setPattern and setText as an
     *                  Object array.
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showInitialPatternAndText(String, String))")
    public void ShowInitialStringAndTextAdvice(JoinPoint joinPoint){
//        System.out.println("#ShowInitialStringAndTextAdvice called...");
        String pattern = (String) joinPoint.getArgs()[0];
        String text = (String) joinPoint.getArgs()[1];
        SMCanvas canvas = getCanvas();
        canvas.setTextAndPattern(text, pattern);
        canvas.initialiseDrawing();
    }

    /**
     * Targets all showMovePatternToIndex method signatures in GenericStringMatch.
     * Passes the data in the correct format to the sorting canvas. This includes the start and end
     * indices as well as an optional message.
     * @param joinPoint This is the join point that retrieves the parameters passed to showMovePatternToIndex in
     *                  the string matching canvas
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showMovePatternToIndex(..))")
        public void ShowMovePatternToIndexAdvice (JoinPoint joinPoint){
            int startIndex = (int) joinPoint.getArgs()[0];
            int endIndex = (int) joinPoint.getArgs()[1];
            String message = null;
            if(joinPoint.getArgs().length == 3)
                message = (String) joinPoint.getArgs()[2];
            getCanvas().showMovePatternToIndex(startIndex, endIndex, message);
    }

    /**
     * Targets all showComparison method signatures in GenericStringMatch.
     * Passes the data in the correct format to the sorting canvas. This includes the indices that are to be compared
     * in the text and pattern. There is an optional message parameter that may be included too.
     * @param joinPoint This is the join point that retrieves the parameters passed to showComparison in
     *                  the string matching canvas
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showComparison(..))")
    public void ShowComparisonAdvice(JoinPoint joinPoint){
        int patternIndex = (int) joinPoint.getArgs()[0];
        int textIndex = (int) joinPoint.getArgs()[1];
        String message = null;
        if(joinPoint.getArgs().length == 3)
            message = (String) joinPoint.getArgs()[2];
        getCanvas().showComparison(patternIndex, textIndex, message);
    }

    /**
     * Targets all showEqual method signatures in GenericStringMatch.
     * Passes the data in the correct format to the sorting canvas. This includes the indices that will be shown to
     * be equal in the text and pattern. There is an optional message parameter that may be included too.
     * @param joinPoint This is the join point that retrieves the parameters passed to showEqual in
     *                  the string matching canvas
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showEqual(..))")
    public void ShowEqualAdvice(JoinPoint joinPoint){
        int patternIndex = (int) joinPoint.getArgs()[0];
        int textIndex = (int) joinPoint.getArgs()[1];
        String message = null;
        if(joinPoint.getArgs().length == 3)
            message = (String) joinPoint.getArgs()[2];
        getCanvas().showEqual(patternIndex, textIndex, message);
    }

    /**
     * Targets all showNotEqual method signatures in GenericStringMatch.
     * Passes the data in the correct format to the sorting canvas. This includes the indices that will be shown to
     * not be equal in the text and pattern. There is an optional message parameter that may be included too.
     * @param joinPoint This is the join point that retrieves the parameters passed to showEqual in
     *                  the string matching canvas
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showNotEqual(..))")
    public void ShowNotEqualAdvice(JoinPoint joinPoint){
        int patternIndex = (int) joinPoint.getArgs()[0];
        int textIndex = (int) joinPoint.getArgs()[1];
        String message = null;
        if(joinPoint.getArgs().length == 3)
            message = (String) joinPoint.getArgs()[2];
        getCanvas().showNotEqual(patternIndex, textIndex, message);
    }

    /**
     * Targets the showMessage method in GenericStringMatch.
     * Passes the data in the correct format to the enqueueMessage method in the string matching canvas.
     * @param joinPoint This is the join point that retrieves the parameter passed to showMessage from the Object array.
     */
    @Before("execution(public void com.mikey.aop.stringmatching.algorithms.GenericStringMatch.showMessage(String))")
    public void ShowMessageAdvice(JoinPoint joinPoint){
        getCanvas().enqueueMessage((String) joinPoint.getArgs()[0]);
    }

}