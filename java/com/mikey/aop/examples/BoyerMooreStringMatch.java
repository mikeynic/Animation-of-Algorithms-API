package com.mikey.aop.examples;

import com.mikey.aop.application.StringMatchingAnimationApplication;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;

public class BoyerMooreStringMatch extends GenericStringMatch {

    static int NO_OF_CHARS = 256;

    private int max (int a, int b) {
        return (a > b)? a: b;
    }

    private void badCharHeuristic(char[] str, int size, int[] badchar) {
        int i;

        for (i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = -1;

        for (i = 0; i < size; i++)
            badchar[(int) str[i]] = i;
    }

    @Override
    public void stringMatch() {
        char[] pattern = new char[]{'a','c','a','b','a','c','a','c'};
        char[] text = new char[]{'a','c','f','a','c','a','b','a','c','a','b','a','c','a','c'};
        int m = pattern.length;
        int n = text.length;

        showInitialPatternAndText(new String(pattern), new String(text));

        int badchar[] = new int[NO_OF_CHARS];
        badCharHeuristic(pattern, m, badchar);
        int oldS = 0;
        int s = 0;
        while(s <= (n - m)) {
            showMovePatternToIndex(oldS, s, "Moving pattern by 1"); // library method
            int j = m-1;
            while(j >= 0 && pattern[j] == text[s+j]){
                showComparison(j, s+j);
                showEqual(j, s+j);
                j--;
            }

            if (j < 0) {
                System.out.println("Patterns occur at shift = " + s);
                showMessage("Match found!");
                s += (s+m < n)? m-badchar[text[s+m]] : 1;
            }
            else{
                showComparison(j, s+j);
                showNotEqual(j, s+j);
                oldS = s;
                s += max(1, j - badchar[text[s+j]]);
            }
        }
    }

    public static void main(String[] args){
        StringMatchingAnimationApplication.runAnimation(new BoyerMooreStringMatch());
    }
}
