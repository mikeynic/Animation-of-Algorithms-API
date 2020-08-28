package com.mikey.aop.trees.datastructures.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertionTest {

    Insertion insertion;

    public InsertionTest() {
        insertion = new Insertion(3, 6);
    }

    @Test
    void getElement() {
        assertEquals(3, insertion.getElement());
    }

    @Test
    void getIndex() {
        assertEquals(6, insertion.getIndex());
    }
}