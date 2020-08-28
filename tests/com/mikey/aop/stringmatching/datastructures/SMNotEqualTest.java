package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMNotEqualTest {

    SMNotEqual notEqual;

    public SMNotEqualTest() {
        this.notEqual = new SMNotEqual(4, 8);
    }

    @Test
    void getShowNotEqualTextIndex() {
        assertEquals(8, notEqual.getShowNotEqualTextIndex());
    }

    @Test
    void getShowNotEqualPatternIndex() {
        assertEquals(4, notEqual.getShowNotEqualPatternIndex());
    }
}