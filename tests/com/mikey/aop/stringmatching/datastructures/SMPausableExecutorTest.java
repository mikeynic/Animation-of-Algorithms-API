package com.mikey.aop.stringmatching.datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMPausableExecutorTest {

    SMPausableExecutor executor;

    public SMPausableExecutorTest() {
        executor = new SMPausableExecutor();
    }

    @Test
    void isPaused() {
        assertTrue(executor.isPaused());
        executor.resume();
        assertFalse(executor.isPaused());
    }
}