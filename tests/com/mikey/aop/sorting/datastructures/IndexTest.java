package com.mikey.aop.sorting.datastructures;

import com.mikey.aop.sorting.enumerations.MarkingState;
import com.sun.java.swing.plaf.windows.WindowsDesktopIconUI;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexTest {

    @Test
    void getColor() {
        Index index = new Index();
        index.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, index.getColor());
    }

    @Test
    void isSwapping() {
        Index index = new Index();
        index.setSwapping(true);
        assertEquals(true, index.isSwapping());
        index.setSwapping(false);
        assertEquals(false, index.isSwapping());
    }

    @Test
    void getMarked() {
        Index index = new Index();
        index.setMarked(MarkingState.NOT_MARKED);
        assertEquals(MarkingState.NOT_MARKED, index.getMarked());
        index.setMarked(MarkingState.DEFAULT_MARKED);
        assertEquals(MarkingState.DEFAULT_MARKED, index.getMarked());
        index.setMarked(MarkingState.CUSTOM_MARKED);
        assertEquals(MarkingState.CUSTOM_MARKED, index.getMarked());
    }

    @Test
    void isComparing() {
        Index index = new Index();
        index.setComparing(false);
        assertEquals(false, index.isComparing());
        index.setComparing(true);
        assertEquals(true, index.isComparing());
    }

    @Test
    void getValue() {
        Index index = new Index();
        for(int i=0; i<100; i++){
            index.setValue(i);
            assertEquals(i, index.getValue());
        }
    }

    @Test
    void getIndex1() {
        for(int i=1; i<=100; i++){
            Index index = new Index();
            index.setIndex(i);
            assertEquals(i, index.getIndex());
        }
    }

    @Test
    void assureIndexIsOnlySetOnce() {
        Index index = new Index();
        index.setIndex(101);
        for(int i=1; i<=100; i++){
            index.setIndex(i);
            assertNotEquals(i, index.getIndex());
        }
    }

    @Test
    void getCopy(){
        Index actual = new Index();
        actual.setColor(Color.YELLOW);
        actual.setSwapping(false);
        actual.setMarked(MarkingState.CUSTOM_MARKED);
        actual.setComparing(true);
        actual.setValue(99);
        actual.setIndex(100);

        Index expected = new Index();
        expected.setColor(Color.YELLOW);
        expected.setSwapping(false);
        expected.setMarked(MarkingState.CUSTOM_MARKED);
        expected.setComparing(true);
        expected.setValue(99);
        expected.setIndex(100);

        Index actualCopy = actual.getCopy();
        assertEquals(expected.getColor(), actualCopy.getColor());
        assertEquals(expected.isSwapping(), actualCopy.isSwapping());
        assertEquals(expected.getMarked(), actualCopy.getMarked());
        assertEquals(expected.isComparing(), actualCopy.isComparing());
        assertEquals(expected.getValue(), actualCopy.getValue());
        assertEquals(expected.getIndex(), actualCopy.getIndex());
    }
}