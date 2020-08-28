package com.mikey.aop.trees.datastructures.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeletionTest {

    Deletion deletion;

    public DeletionTest() {
        deletion = new Deletion(5);
    }

    @Test
    void getIndex() {
        assertEquals(5, deletion.getIndex());
    }
}