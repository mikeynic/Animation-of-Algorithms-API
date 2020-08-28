package com.mikey.aop.stringmatching.datastructures;

import com.mikey.aop.application.SM_CONSTANT;
import com.mikey.aop.application.SORTING_CONSTANT;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class overrides the Thread class and spawns a PausableExecutor thread. The general goal of this class is to
 * schedule frames to be written to the GUI in an orderly, predictable manor. Runnable objects enqueued with
 * enqueueRunnable should have nested Runnable objects that are scheduled to be enqueued to the PausableExecutor
 * thread. This will ensure that the correct order of events is achieved.
 * @author Michael Nicholson
 */
public class SMQueueThread extends Thread {

    private Queue<Runnable> queue;
    private SMPausableExecutor executor = new SMPausableExecutor();

    /**
     * The sole constructor for this class. This creates a new queue and pauses the executor so that the animation does
     * not immediately play when the GUI is loaded.
     */
    public SMQueueThread(){
        queue = new LinkedList<>();
        executor.pause();
    }

    /**
     * Overrides start. This pauses the executor so that the animation does not immediately play when the GUI is loaded.
     */
    @Override
    public synchronized void start() {
        super.start();
        executor.pause();
    }

    /**
     * Overrides the run method. This executes a loop that only breaks when the application exits. Every second, the
     * thread periodically checks to see if there are any Runnable objects to execute in the internal queue. If there
     * are, then they are executed. If there are not, then the thread waits another second.
     */
    @Override
    public void run() {
        super.run();
        while(!SM_CONSTANT.APPLICATION_EXIT){
//            System.out.println("queue size=" + queue.size());
            while(!queue.isEmpty() && !SM_CONSTANT.APPLICATION_EXIT){
                Runnable next = queue.remove();
                Platform.runLater(next);
            }
            sleepThread(1000);
        }
        executor.shutdown();
    }

    /**
     * Enqueues a runnable to the internal queue which is then scheduled by the run method.
     * @param r Is the Runnable that will be enqueued to the internal queue of Runnables.
     */
    public void enqueueRunnable(Runnable r){
        queue.add(r);
    }

    /**
     * Enqueues a runnable to the executor thread. This is executed as soon as all of the other Runnables in the
     * executor queue have been executed.
     * @param r The Runnable object being enqueued to the executor thread.
     */
    public void enqueueToExecutor(Runnable r){
        executor.submit(r);
    }

    /**
     * This method pauses the thread for the amount of time specified as the parameter.
     * @param sleepTime The amount of time the thread should sleep for.
     */
    private void sleepThread(int sleepTime){
        try {Thread.sleep(sleepTime);}catch (Exception e) {System.out.println(e);}
    }

    /**
     * This method resets the internal queue, shuts down the executor thread and spawns fresh one
     */
    public void resetQueue(){
        queue = new LinkedList<>();
        executor.shutdown();
        executor = new SMPausableExecutor();
        executor.pause();
    }

    /**
     * Interface that allows the executor thread to be played from the pause state.
     */
    public void play(){
        executor.resume();
    }

    /**
     * Interface that allows the executor thread to be paused from the play state.
     */
    public void pause(){
        executor.pause();
    }

    /**
     * Interface that allows the executor thread to execute one Runnable object from the executors internal queue.
     */
    public void step(){
        executor.resume();
        sleepThread(SORTING_CONSTANT.ANIMATION_LENGTH);
        executor.pause();
    }
}
