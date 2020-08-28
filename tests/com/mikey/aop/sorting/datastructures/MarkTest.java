package com.mikey.aop.sorting.datastructures;

import com.mikey.aop.sorting.enumerations.MarkedAction;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkTest {

    Mark mark;

    public MarkTest() {
        mark = new Mark(4, MarkedAction.BEING_MARKED, Color.NAVY);
    }

    @Test
    void getMarkIndex() {
        assertEquals(4, mark.getMarkIndex());
    }

    @Test
    void getMarkedAction() {
        assertEquals(MarkedAction.BEING_MARKED, mark.getMarkedAction());
    }

    @Test
    void getMarkedColour() {
        assertEquals(Color.NAVY, mark.getMarkedColour());
    }
}