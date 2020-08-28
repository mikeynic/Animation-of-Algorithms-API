package com.mikey.aop.sorting.components;

import com.mikey.aop.application.SORTING_CONSTANT;
import com.mikey.aop.sorting.datastructures.Index;
import com.mikey.aop.sorting.enumerations.MarkingState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingCanvasHelperTest {

    SortingCanvasHelper canvasHelper;

    public SortingCanvasHelperTest() {
        this.canvasHelper = new SortingCanvasHelper(null);
    }

    @Test
    void selectColour() {
        Index index = new Index();
        index.setComparing(true);
        Color expected = SORTING_CONSTANT.COMPARISON_COLOUR;
        Color actual = canvasHelper.selectColour(index);
        assertEquals(expected, actual);

        index = new Index();
        index.setSwapping(true);
        expected = SORTING_CONSTANT.SWAPPING_COLOUR;
        actual = canvasHelper.selectColour(index);
        assertEquals(expected, actual);
    }

    @Test
    void selectMarkingColour() {
        Index index = new Index();
        index.setColor(Color.YELLOWGREEN);
        // 1st state
        index.setMarked(MarkingState.NOT_MARKED);
        assertEquals(Color.WHITE, canvasHelper.selectMarkingColour(index));
        // 2nd state
        index.setMarked(MarkingState.DEFAULT_MARKED);
        assertEquals(SORTING_CONSTANT.MARKED_COLOUR, canvasHelper.selectMarkingColour(index));
        // 3rd state
        index.setMarked(MarkingState.CUSTOM_MARKED);
        assertEquals(Color.YELLOWGREEN, canvasHelper.selectMarkingColour(index));
    }
}