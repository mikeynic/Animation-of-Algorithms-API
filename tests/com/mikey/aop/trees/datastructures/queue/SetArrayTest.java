package com.mikey.aop.trees.datastructures.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetArrayTest {

    SetArray array;

    public SetArrayTest() {
        this.array = new SetArray(new int[]{2,3,4,5,6});
    }

    @Test
    void getArray() {
        assertArrayEquals(new int[]{2,3,4,5,6}, array.getArray());
    }
}