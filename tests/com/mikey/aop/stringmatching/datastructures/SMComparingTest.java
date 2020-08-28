package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMComparingTest {

    SMComparing comparing;

    public SMComparingTest() {
        this.comparing = new SMComparing(3, 6);
    }

    @Test
    void getComparingTextIndex() {
        assertEquals(6, comparing.getComparingTextIndex());
    }

    @Test
    void getComparingPatternIndex() {
        assertEquals(3, comparing.getComparingPatternIndex());
    }
}