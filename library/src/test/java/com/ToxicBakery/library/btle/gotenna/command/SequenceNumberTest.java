package com.ToxicBakery.library.btle.gotenna.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Ensure sequence numbers work as intended
 */
public class SequenceNumberTest {

    private static final int WORKER_THREAD_COUNT = 1024;

    @Test
    public void testNext() throws Exception {
        SequenceNumber sequenceNumber = new SequenceNumber();
        sequenceNumber.reset();
        assertEquals(1, sequenceNumber.next());
        assertEquals(2, new SequenceNumber().next());
        assertEquals(3, new SequenceNumber().next());
    }

    @Test
    public void testNext_multipleThreads() throws Exception {
        final SequenceNumber sequenceNumber = new SequenceNumber();
        sequenceNumber.reset();
        assertEquals(1, sequenceNumber.next());

        Thread[] threads = new Thread[WORKER_THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    sequenceNumber.next();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(WORKER_THREAD_COUNT + 2, sequenceNumber.next());
    }

    @Test
    public void testReset() throws Exception {
        SequenceNumber sequenceNumber = new SequenceNumber();
        sequenceNumber.next();
        sequenceNumber.reset();
        int next = sequenceNumber.next();
        assertEquals(1, next);
        assertEquals(2, new SequenceNumber().next());
        assertEquals(3, new SequenceNumber().next());
    }

}