package com.mikey.aop.sorting.datastructures;

import com.mikey.aop.sorting.enumerations.MarkedAction;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingQueueNodeTest {

    @Test
    void getSetArray() {
        SetArray node = new SetArray(new int[]{1,2,3,4,5,6});
        assertArrayEquals(new int[]{1,2,3,4,5,6}, node.getArray());
        assertNull(node.getMessage());
    }

    @Test
    void getMark() {
        Mark node = new Mark(3, MarkedAction.BEING_MARKED, Color.GRAY);
        assertEquals(MarkedAction.BEING_MARKED, node.getMarkedAction());
        assertNull(node.getMessage());
    }

    @Test
    void getCompare() {
        Compare node = new Compare(4,5);
        assertEquals(node.getCompareIndex1(), 4);
        assertEquals(node.getCompareIndex2(), 5);
        assertNull(node.getMessage());
    }

    @Test
    void getSwap() {
        Swap node = new Swap(3,10);
        assertNull(node.getMessage());
    }

    @Test
    void getMessage() {
        SortingQueueNode node = new SortingQueueNode();
        node.setMessage("message");
        assertNotNull(node.getMessage());
    }
}