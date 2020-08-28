package com.mikey.aop.examples;

import com.mikey.aop.application.TreeAnimationApplication;
import com.mikey.aop.trees.algorithms.GenericTreeAlgorithm;
import javafx.scene.paint.Color;

public class TreeSearch extends GenericTreeAlgorithm {

    @Override
    public void algorithm() {
        int[] tree = new int[]{0,6,3,8,1,5,7,9,0,2,4};
        showArray(tree);
        System.out.println(searchHelper(tree, 1, 4));
    }

    private int searchHelper(int[] arr, int index, int targetElement) {
        if (arr[index] == targetElement) {
            showMark(index, Color.YELLOW);
            return index;
        }
        showMark(index);
        if (arr[index] > targetElement && index * 2 < arr.length)
            return searchHelper(arr, index * 2, targetElement);
        if (arr[index] < targetElement && (index * 2) + 1 < arr.length)
            return searchHelper(arr, (index * 2) + 1, targetElement);
        return -1;
    }

    public static void main(String[] args){
        TreeAnimationApplication.runAnimation(new TreeSearch());
    }
}


