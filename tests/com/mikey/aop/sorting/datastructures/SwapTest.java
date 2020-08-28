package com.mikey.aop.sorting.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwapTest {

    Swap swap;

    public SwapTest() {
        swap = new Swap(6, 8);
    }

    @Test
    void getIndex1() {
        assertEquals(6, swap.getIndex1());
    }

    @Test
    void getIndex2() {
        assertEquals(8, swap.getIndex2());
    }
}