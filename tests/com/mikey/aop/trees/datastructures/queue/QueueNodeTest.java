package com.mikey.aop.trees.datastructures.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueNodeTest {

    QueueNode node;

    public QueueNodeTest() {
        node = new QueueNode();
        node.setMessage("hello");
    }

    @Test
    void getMessage() {
        assertTrue("hello".equals(node.getMessage()));
    }
}