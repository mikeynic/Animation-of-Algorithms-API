package com.mikey.aop.sorting.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompareTest {

    Compare compare = new Compare(1, 2);

    @Test
    void getCompareIndex1() {
        assertEquals(1, compare.getCompareIndex1());
    }

    @Test
    void getCompareIndex2() {
        assertEquals(2, compare.getCompareIndex2());
    }
}