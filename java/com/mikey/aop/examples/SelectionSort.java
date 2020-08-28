package com.mikey.aop.examples;

import com.mikey.aop.application.SortingAnimationApplication;
import com.mikey.aop.sorting.algorithms.GenericSort;
import javafx.scene.paint.Color;

public class SelectionSort extends GenericSort {

    @Override
    public void sort() {
        int[] arr = new int[]{5,4,3,2,1};
        showInitial(arr);
        int n = arr.length;

        for (int i = 0; i < n; i++){
            int minIndex = i;
            showMark(minIndex, "Marking the minimum element so far in this iteration of the outer for loop ");
            for (int j = i+1; j < n; j++) {
                showComparison(j, minIndex, "Comparing the current minimum element with index " + j);
                if (arr[j] < arr[minIndex]){
                    showUnMark(minIndex);
                    minIndex = j;
                    showMark(minIndex);
                }
            }
            showUnMark(minIndex);
            showSwap(minIndex, i, "Swapping indices: " + minIndex + " and " + i);
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
            showMark(i, Color.GREEN, "Element " + i + " is in place");
        }
    }

    public static void main(String[] args){
        SortingAnimationApplication.runAnimation(new SelectionSort());
    }

}
