package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMEqualTest {

    SMEqual smEqual;

    public SMEqualTest() {
        this.smEqual = new SMEqual(6, 9);
    }

    @Test
    void getShowEqualTextIndex() {
        assertEquals(9, smEqual.getShowEqualTextIndex());
    }

    @Test
    void getShowEqualPatternIndex() {
        assertEquals(6, smEqual.getShowEqualPatternIndex());
    }

    @Test
    void returnCopy() {
        SMEqual copy = smEqual.returnCopy();
        assertEquals(6, copy.getShowEqualPatternIndex());
        assertEquals(9, copy.getShowEqualTextIndex());
    }
}