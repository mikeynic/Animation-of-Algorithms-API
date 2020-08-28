package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMMovingTest {

    SMMoving moving;

    public SMMovingTest() {
        this.moving = new SMMoving(1, 5);
    }

    @Test
    void getMovingStartIndex() {
        assertEquals(1, moving.getMovingStartIndex());
    }

    @Test
    void getMovingEndIndex() {
        assertEquals(5, moving.getMovingEndIndex());
    }
}