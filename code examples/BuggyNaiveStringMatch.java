package com.mikey.aop.examples;

import com.mikey.aop.application.StringMatchingAnimationApplication;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;

public class BuggyNaiveStringMatch extends GenericStringMatch{

    @Override
    public void stringMatch(){
        String pattern = "acabacac",  text = "acfacabacabacac";

        showInitialPatternAndText(pattern, text); // library function
        for(int s = 0; s< text.length() - pattern.length(); s++){
            int j = 0;
            if(s!=0) {
                showMessage("For loop number: " + s); // library function
                showMovePatternToIndex(s - 1, s, "Moving pattern by 1"); // library method
            }
            showComparison(j, s+j, "Showing comparison: pattern=" + j + ", text=" + (s+j)); // library method
            while( j < pattern.length() && pattern.charAt(j) == text.charAt(s+j)){
                showComparison(j, s+j, "Showing comparison: pattern=" + j + ", text=" + (s+j)); // library method
                showEqual(j, s+j, "Showing equal: pattern=" + j + ", text=" + (s+j)); // library method
                j++;
            }
            if(j == pattern.length()){
                showMessage("Found a match!");
                return;
            }
            showComparison(j, s+j, "Showing comparison: pattern=" + j + ", text=" + (s+j)); // library method
            showNotEqual(j, s+j, "Showing not equal: pattern=" + j + ", text=" + (s+j)); // library method
        }
        showMessage("No match found!");
    }

    public static void main(String[] args){
        StringMatchingAnimationApplication.runAnimation(new BuggyNaiveStringMatch());
    }
}
