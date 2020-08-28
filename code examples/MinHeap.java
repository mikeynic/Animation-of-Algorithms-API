package com.mikey.aop;

import com.mikey.aop.trees.algorithms.GenericTreeAlgorithm;
import javafx.scene.paint.Color;

public class MinHeap extends GenericTreeAlgorithm {

    @Override
    public void algorithm() {
        int[] tree = new int[]{0,1,3,6,5,9,8,0,0,0};

        showArray(tree); // library method

        tree[7] = -2;

        showInsert(-2, 7); // library method

        upHeap(tree, 7);
        tree[8] = -3;

        showInsert(-3, 8); // library method

        upHeap(tree, 8);
    }
    private void upHeap(int[] tree, int nodeIndex){
        if(nodeIndex != 0){
            int parentIndex = getParentIndex(tree, nodeIndex);
            if(tree[parentIndex] > tree[nodeIndex]){
                int temp = tree[parentIndex];
                tree[parentIndex] = tree[nodeIndex];
                tree[nodeIndex] = temp;

                showSwap(parentIndex, nodeIndex); // library method

                upHeap(tree, parentIndex);
            }
        }
    }
    private int getParentIndex(int[] arr, int index){
        int left = index/2;
        if(arr[left*2] == arr[index])
            return left;
        int right = (index-1)/2;
        if(arr[(right*2)+1] == arr[index])
            return right;
        return -1;
    }
}


