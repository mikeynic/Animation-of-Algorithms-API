package com.mikey.aop.sorting.threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Extension example from the ThreadPoolExecutor class documentation.
 * Found at: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
 * Last accessed: 28/04/2020
 */
public class PausableExecutor extends ThreadPoolExecutor {
    private boolean isPaused = true;
    private Lock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    public PausableExecutor() {
        super(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * Enables the pause functionality of the PausableExecutor
     */
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * Enables the play functionality of the PausableExecutor
     */
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signal();
        } finally {
            pauseLock.unlock();
        }
    }
}
