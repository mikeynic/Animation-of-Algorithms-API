package com.mikey.aop.application;

import com.mikey.aop.sorting.algorithms.GenericSort;

import java.util.Random;

public class QuickSort extends GenericSort {

    @Override
    public void sort() {
        int n = 10;
        int[] arr = new int[n];
        Random random = new Random();
        for(int i=0; i<n; i++){
            arr[i] = random.nextInt(20);
        }

        showInitial(arr);
        sorting(arr, 0, n-1);
    }

    private void sorting(int[] arr, int low, int high){
        if (low < high) {
            int pi = partition(arr, low, high);
            sorting(arr, low, pi-1);
            sorting(arr, pi+1, high);
        }
    }

    private int partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int i = (low-1);
        for (int j=low; j<high; j++) {
            if (arr[j] < pivot) {
                i++;
                showSwap(i, j);
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        showSwap(i+1, high);
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return i+1;
    }

    public static void main(String[] args){
        SortingAnimationApplication.runAnimation(new QuickSort());
    }
}
