package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMQueueNodeTest {

    @Test
    void getMoving() {
        SMMoving node = new SMMoving(3, 5);
        assertEquals(3, node.getMovingStartIndex());
        assertEquals(5, node.getMovingEndIndex());
        assertNull(node.getMessage());
    }

    @Test
    void getComparing() {
        SMComparing node = new SMComparing(4, 9);
        assertEquals(4, node.getComparingPatternIndex());
        assertEquals(9, node.getComparingTextIndex());
        assertNull(node.getMessage());
    }

    @Test
    void getEqual() {
        SMEqual node = new SMEqual(3, 5);
        assertEquals(3, node.getShowEqualPatternIndex());
        assertEquals(5, node.getShowEqualTextIndex());
        assertNull(node.getMessage());
    }

    @Test
    void getNotEqual() {
        SMNotEqual node = new SMNotEqual(3, 5);
        assertEquals(3, node.getShowNotEqualPatternIndex());
        assertEquals(5, node.getShowNotEqualTextIndex());
        assertNull(node.getMessage());
    }

    @Test
    void getMessage() {
        SMNotEqual node = new SMNotEqual(3, 5);
        assertEquals(3, node.getShowNotEqualPatternIndex());
        assertEquals(5, node.getShowNotEqualTextIndex());
        node.setMessage("message");
        assertNotNull(node.getMessage());
    }
}