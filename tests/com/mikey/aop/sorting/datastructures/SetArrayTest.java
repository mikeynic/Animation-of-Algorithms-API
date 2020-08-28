package com.mikey.aop.sorting.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetArrayTest {

    int[] arr;
    int[] actual;

    public SetArrayTest() {
        arr = new int[]{2,3,4,5};
        actual = new int[arr.length];
        System.arraycopy(arr, 0, actual, 0, arr.length);
    }

    @Test
    void getArray() {
        SetArray setArray = new SetArray(arr);
        assertArrayEquals(actual, setArray.getArray());
    }
}