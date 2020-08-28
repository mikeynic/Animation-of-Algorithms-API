package com.mikey.aop.trees.datastructures.queue;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkTest {

    Mark mark;

    public MarkTest() {
        mark = new Mark(3, Color.ALICEBLUE);
    }

    @Test
    void getIndex() {
        assertEquals(3, mark.getIndex());
    }

    @Test
    void getColor() {
        assertEquals(Color.ALICEBLUE, mark.getColor());
    }

    @Test
    void isColoured() {
        assertTrue(mark.isColoured());
    }
}