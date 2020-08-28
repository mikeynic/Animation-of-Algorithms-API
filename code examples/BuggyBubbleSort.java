package com.mikey.aop.examples;

import com.mikey.aop.application.SortingAnimationApplication;
import com.mikey.aop.sorting.algorithms.GenericSort;

import java.util.Random;

public class BuggyBubbleSort extends GenericSort {

    @Override
    public void sort() {
        int n = 10;
        int[] arr = new int[n];
        Random random = new Random();
        for(int i=0; i<n; i++){
            arr[i] = random.nextInt(20);
        }

        showInitial(arr);
        for(int i=0; i<=n-1; i++){
            showMessage("Starting for loop, i=" + i);
            int j;
            for(j=0; j<n-i-2; j++){
                showComparison(j, j+1,"Comparison of indices: " + j + ", " + (j+1));
                if(arr[j] > arr[j+1]){
                    showSwap(j, j+1,"Swapping indices: "  + j + ", " + (j+1));
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            showMark(j); // library function
        }
        showMessage("End of bubble sort!");
    }

    public static void main(String[] args){
        SortingAnimationApplication.runAnimation(new BuggyBubbleSort());
    }
}
